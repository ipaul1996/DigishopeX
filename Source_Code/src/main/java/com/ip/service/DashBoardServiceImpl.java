package com.ip.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ip.dto.ProductDTOV4;
import com.ip.dto.ProductDTOV5;
import com.ip.dto.SalesAnalysisDTO;
import com.ip.exception.OrderException;
import com.ip.exception.SalesAnalysisNotFoundException;
import com.ip.model.DaywiseSalesData;
import com.ip.model.OrderDetail;
import com.ip.model.Orders;
import com.ip.model.Product;
import com.ip.model.ProductDTOV3;
import com.ip.model.RevenueDTO;
import com.ip.repository.CustomerRepo;
import com.ip.repository.OrdersRepo;
import com.ip.repository.ProductRepo;
import com.ip.repository.SalesDataRepo;

@Service
public class DashBoardServiceImpl implements DashBoardService{
	
	
	@Autowired
	private CustomerRepo cRepo;
	
	
	@Autowired
	private OrdersRepo oRepo;
	

	@Autowired
	private ProductRepo pRepo;
	


	@Autowired
	private SalesDataRepo sdRepo;
	
	@Override
	@Scheduled(fixedRate = 3600000)
	public void doTodaySalesAnalysis(){
		
		List<Orders> allOrders = oRepo.findAll();
		
		if(allOrders == null || allOrders.isEmpty()) {
			return;
		}
		
		allOrders =  allOrders.stream()
		         			  .filter(e -> (e.getOrderDateTime().toLocalDate()).equals(LocalDate.now()))
		         			  .collect(Collectors.toList());
		
		DecimalFormat df = new DecimalFormat("#.##");	
		
		Double totalSalesPriceForAllOrders = 0.0;
		Double totalCostPriceForAllOrders = 0.0;
		Integer noOfOrders = 0;
		
	    Map<Integer, ProductDTOV3> allProducts = new LinkedHashMap<>();
	    
	    Map<String, RevenueDTO> revenueBreakdownMap = new LinkedHashMap<>();
	    
	    Set<Integer> countCustomer = new HashSet<>();
					 
		for(Orders order : allOrders) {
					 
			 List<OrderDetail> details = order.getDetails();
			 			 
			 totalSalesPriceForAllOrders += details.stream()
					  							  .mapToDouble(e2 -> e2.getQuantity() * e2.getProduct().getPrice())
					  							  .sum();
			 
			 totalCostPriceForAllOrders += details.stream()
												  .mapToDouble(e2 -> e2.getQuantity() * e2.getProduct().getCostPrice())
												  .sum();
			 
			 noOfOrders++;
			 
			 
			 details.stream()
			 		.forEach(e -> {
			 			
			 			ProductDTOV3 pdv3;
			 			
			 			if(allProducts.containsKey(e.getProduct().getProductId())) {
			 				pdv3 = allProducts.get(e.getProduct().getProductId());
			 				pdv3.setNoOfProductsSold(pdv3.getNoOfProductsSold() + e.getQuantity());
			 				
			 			} else {
			 				pdv3 = new ProductDTOV3();
			 				pdv3.setCategoryName(e.getProduct().getCategory().getCategoryName());
			 				pdv3.setProductID(e.getProduct().getProductId());
			 				pdv3.setNoOfProductsSold(e.getQuantity());
			 				pdv3.setTotalSales(e.getQuantity() * e.getProduct().getPrice());
			 				
			 			}
			 			
			 			allProducts.put(e.getProduct().getProductId(), pdv3);
			 			
			 			RevenueDTO rdt;
			 			
			 			if(revenueBreakdownMap.containsKey(e.getProduct().getCategory().getCategoryName())) {
			 				rdt = revenueBreakdownMap.get(e.getProduct().getCategory().getCategoryName());
			 				rdt.setCategorywiseTotalRevenue(rdt.getCategorywiseTotalRevenue() + (e.getQuantity() * 
			 						(e.getProduct().getPrice() - e.getProduct().getCostPrice())));
			 				
			 			} else {
			 				rdt = new RevenueDTO();
			 				rdt.setCategoryName(e.getProduct().getCategory().getCategoryName());
			 				rdt.setCategorywiseTotalRevenue(e.getQuantity() * 
			 						(e.getProduct().getPrice() - e.getProduct().getCostPrice()));
			 				
			 			}
			 			
			 			revenueBreakdownMap.put(rdt.getCategoryName(), rdt);
			 			
			 		});
			 
			 countCustomer.add(order.getCustomer().getUserid());
			 
			 
		}
		
		
		Double avgOrderValue = Double.valueOf(df.format(totalSalesPriceForAllOrders / noOfOrders));
		
		
		Double totalRevenueGenerated = totalSalesPriceForAllOrders - totalCostPriceForAllOrders;
		
		List<ProductDTOV3> topTenProducts = new ArrayList<>();
		
		List<Map.Entry<Integer, ProductDTOV3>> list = new ArrayList<>(allProducts.entrySet());
		
		list.sort((c1, c2) -> c2.getValue().getNoOfProductsSold() - c1.getValue().getNoOfProductsSold());
		
		int count = 0;
		
		for(Map.Entry<Integer, ProductDTOV3> pd : list) {
			topTenProducts.add(pd.getValue());
			count++;
			if(count > 10) break;
		}

		
		List<RevenueDTO> revenueBreakdownList = new ArrayList<>();
		
		for(Map.Entry<String, RevenueDTO> re : revenueBreakdownMap.entrySet()) {
			
			RevenueDTO rdto =  re.getValue();
			
			Double revenuePercentage = ((rdto.getCategorywiseTotalRevenue() / totalRevenueGenerated) * 100);
					
			String formatedPercentage = df.format(revenuePercentage);			
			rdto.setRevenuePercentage(formatedPercentage + "%");
			
			revenueBreakdownList.add(rdto);
		}
		
		
		String conversionRate = df.format((countCustomer.size() / cRepo.findAll().size()) * 100) + "%";

		
		Optional<DaywiseSalesData> op = sdRepo.findByDate(LocalDate.now());
		
		DaywiseSalesData dsd;
		
		if(op.isEmpty()) {
			dsd = new DaywiseSalesData();
			
		} else {
			dsd = op.get();
		}
		
		if(noOfOrders.equals(0)) return;
		
		dsd.setDate(LocalDate.now());
		dsd.setNoOfOrders(noOfOrders);
		dsd.setTotalSalesAmount(totalSalesPriceForAllOrders);
		dsd.setTotalRevenue(totalRevenueGenerated);
		dsd.setAvgOrderValue(avgOrderValue);
		dsd.setRevenueBreakdown(revenueBreakdownList);
		dsd.setTopSellingProducts(topTenProducts);
		dsd.setConversionRate(conversionRate);
		
		sdRepo.save(dsd);
		
	}


	@Override
	public DaywiseSalesData getTodaySalesAnalysis() throws SalesAnalysisNotFoundException{
		
		doTodaySalesAnalysis();
		
		Optional<DaywiseSalesData> op = sdRepo.findByDate(LocalDate.now());
		
		if(op.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No analysis found...");
		}
			
		return op.get();
		
	}


	@Override
	public DaywiseSalesData getSalesAnalysisOfDate(LocalDate targetDate) throws SalesAnalysisNotFoundException {
		
		if(targetDate.isAfter(LocalDate.now())) {
			throw new SalesAnalysisNotFoundException("Invalid date");
		}
		
		Optional<DaywiseSalesData> op = sdRepo.findByDate(targetDate);
		
		if(op.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No analysis found...");
		}
		
		return op.get();
	}


	@Override
	public SalesAnalysisDTO getSalesAnalysisOfLastWeek() throws SalesAnalysisNotFoundException {
		
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.minusDays(7);
		
		List<DaywiseSalesData> list = sdRepo.findAll();
		
		list = list.stream()
				   .filter(e -> (!e.getDate().isAfter(today) && !e.getDate().isBefore(startDate)) ? true : false)
				   .collect(Collectors.toList());
		
		if(list.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No sales data found...");
		}
		
		
		Double TotalSalesAmountInLastWeek = 0.0;	
		Integer noOfOrdersInLastWeek = 0;
	    Double avgOrderValueInLastWeek = 0.0;
		Double totalRevenueInLastWeek = 0.0;
		List<ProductDTOV3> topSellingProductsInLastWeek = new ArrayList<>();
		List<RevenueDTO> revenueBreakdownInLastWeek = new ArrayList<>();
		
		
		
		for(DaywiseSalesData dsd : list) {
			TotalSalesAmountInLastWeek += dsd.getTotalSalesAmount();
			noOfOrdersInLastWeek += dsd.getNoOfOrders();
			avgOrderValueInLastWeek += dsd.getAvgOrderValue();
			totalRevenueInLastWeek += dsd.getTotalRevenue();
			topSellingProductsInLastWeek.addAll(dsd.getTopSellingProducts());
			revenueBreakdownInLastWeek.addAll(dsd.getRevenueBreakdown());
		}
		

		List<ProductDTOV3> finalizedTopSellingProductsInLastWeek 
		      = topSellingProductsInLastWeek.stream()
										    .collect(Collectors.groupingBy(p -> p.getProductID()))
										    .entrySet().stream()
										    .map(entry -> {
										    	
										        Integer productId = entry.getKey();
										        List<ProductDTOV3> productDTOV3s = entry.getValue();
										        int noOfProductsSold = 0;
										        double totalSales = 0.0;
										        String categoryName = productDTOV3s.get(0).getCategoryName();
										        
										        for (ProductDTOV3 productDTOV3 : productDTOV3s) {
										            noOfProductsSold += productDTOV3.getNoOfProductsSold();
										            totalSales += productDTOV3.getTotalSales();
										        }
										        return new ProductDTOV3(productId, noOfProductsSold, totalSales, categoryName);
										    })
										    .sorted((c1, c2) -> c2.getNoOfProductsSold() - c1.getNoOfProductsSold())
										    .limit(10)
										    .collect(Collectors.toList());

		
		
		List<RevenueDTO> finalizedRevenueBreakdownInLastWeek 
				= revenueBreakdownInLastWeek.stream()
											.collect(Collectors.groupingBy(e -> e.getCategoryName()))
											.entrySet().stream()
											.map(entry -> {
												
												Double categorywiseTotalRevenue = 0.0;
												
												List<RevenueDTO> revenueDTOs =  entry.getValue();
												String categoryName = revenueDTOs.get(0).getCategoryName();
												
												for(RevenueDTO rdt : revenueDTOs) {
													categorywiseTotalRevenue += rdt.getCategorywiseTotalRevenue();
												}
												
												return new RevenueDTO(categoryName, categorywiseTotalRevenue, null);
											})
											.collect(Collectors.toList());
			
		DecimalFormat df = new DecimalFormat("#.##");	
		
		Double totalRevenue = totalRevenueInLastWeek;
		
		finalizedRevenueBreakdownInLastWeek = 
				finalizedRevenueBreakdownInLastWeek.stream()	
												   .map(e -> {
													   e.setRevenuePercentage(df.format(e.getCategorywiseTotalRevenue() / totalRevenue) + "%") ;
													   return e;
												   })
												   .collect(Collectors.toList());
		
		
		List<Orders> orders = oRepo.findAll();
		
		orders = orders.stream()
					   .filter(e -> (!e.getOrderDateTime().toLocalDate().isAfter(today) && !e.getOrderDateTime().toLocalDate().isBefore(startDate)) ? true : false)
					   .collect(Collectors.toList());
		
		Set<Integer> customerCount = new HashSet<>();
		
		orders.stream()
		      .forEach(o -> customerCount.add(o.getCustomer().getUserid()));
		
		
		String conversionRateInLastWeek = df.format((customerCount.size() / cRepo.findAll().size()) * 100) + "%";
		
		
		SalesAnalysisDTO sadt = new SalesAnalysisDTO();
		sadt.setAvgOrderValue(avgOrderValueInLastWeek);
		sadt.setConversionRate(conversionRateInLastWeek);
		sadt.setNoOfOrders(noOfOrdersInLastWeek);
		sadt.setRevenueBreakdown(finalizedRevenueBreakdownInLastWeek);
		sadt.setTopSellingProducts(finalizedTopSellingProductsInLastWeek);
		sadt.setTotalSalesAmount(TotalSalesAmountInLastWeek);
		sadt.setTotalRevenue(totalRevenueInLastWeek);
		
		
		return sadt;
	}
	
	
	@Override
	public SalesAnalysisDTO getSalesAnalysisOfLastMonth() throws SalesAnalysisNotFoundException {
		
		LocalDate firstDate = (LocalDate.now().withMonth(LocalDate.now().getMonth().minus(1).getValue()).withDayOfMonth(1));
		
		LocalDate lastDate = firstDate.withDayOfMonth(firstDate.getMonth().length(firstDate.isLeapYear()));
	
		if(lastDate.getMonthValue() == 1) {
			firstDate = firstDate.withYear(firstDate.getYear() - 1);
			lastDate = lastDate.withYear(lastDate.getYear() - 1);
		}
		
		LocalDate modifiedFirstDate = firstDate;
		LocalDate modifiedLastDate = lastDate;
		
		List<DaywiseSalesData> list = sdRepo.findAll();
		
		list = list.stream()
				   .filter(e -> (!e.getDate().isAfter(modifiedLastDate) && !e.getDate().isBefore(modifiedFirstDate)) ? true : false)
				   .collect(Collectors.toList());
		
		if(list.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No sales data found...");
		}
		
		Double TotalSalesAmountInLastMonth = 0.0;	
		Integer noOfOrdersInLastMonth = 0;
	    Double avgOrderValueInLastMonth = 0.0;
		Double totalRevenueInLastMonth = 0.0;
		List<ProductDTOV3> topSellingProductsInLastMonth = new ArrayList<>();
		List<RevenueDTO> revenueBreakdownInLastMonth = new ArrayList<>();
		
		
		
		for(DaywiseSalesData dsd : list) {
			TotalSalesAmountInLastMonth += dsd.getTotalSalesAmount();
			noOfOrdersInLastMonth += dsd.getNoOfOrders();
			avgOrderValueInLastMonth += dsd.getAvgOrderValue();
			totalRevenueInLastMonth += dsd.getTotalRevenue();
			topSellingProductsInLastMonth.addAll(dsd.getTopSellingProducts());
			revenueBreakdownInLastMonth.addAll(dsd.getRevenueBreakdown());
		}
		

		List<ProductDTOV3> finalizedTopSellingProductsInLastMonth
		      = topSellingProductsInLastMonth.stream()
										    .collect(Collectors.groupingBy(p -> p.getProductID()))
										    .entrySet().stream()
										    .map(entry -> {
										    	
										        Integer productId = entry.getKey();
										        List<ProductDTOV3> productDTOV3s = entry.getValue();
										        int noOfProductsSold = 0;
										        double totalSales = 0.0;
										        String categoryName = productDTOV3s.get(0).getCategoryName();
										        
										        for (ProductDTOV3 productDTOV3 : productDTOV3s) {
										            noOfProductsSold += productDTOV3.getNoOfProductsSold();
										            totalSales += productDTOV3.getTotalSales();
										        }
										        return new ProductDTOV3(productId, noOfProductsSold, totalSales, categoryName);
										    })
										    .sorted((c1, c2) -> c2.getNoOfProductsSold() - c1.getNoOfProductsSold())
										    .limit(10)
										    .collect(Collectors.toList());

		
		
		List<RevenueDTO> finalizedRevenueBreakdownInLastMonth 
				= revenueBreakdownInLastMonth.stream()
											.collect(Collectors.groupingBy(e -> e.getCategoryName()))
											.entrySet().stream()
											.map(entry -> {
												
												Double categorywiseTotalRevenue = 0.0;
												
												List<RevenueDTO> revenueDTOs =  entry.getValue();
												String categoryName = revenueDTOs.get(0).getCategoryName();
												
												for(RevenueDTO rdt : revenueDTOs) {
													categorywiseTotalRevenue += rdt.getCategorywiseTotalRevenue();
												}
												
												return new RevenueDTO(categoryName, categorywiseTotalRevenue, null);
											})
											.collect(Collectors.toList());
											
		
		DecimalFormat df = new DecimalFormat("#.##");	
		
		Double totalRevenue = totalRevenueInLastMonth;
		
		finalizedRevenueBreakdownInLastMonth = 
				finalizedRevenueBreakdownInLastMonth.stream()	
												   .map(e -> {
													   e.setRevenuePercentage(df.format(e.getCategorywiseTotalRevenue() / totalRevenue) + "%") ;
													   return e;
												   })
												   .collect(Collectors.toList());
		
		
		List<Orders> orders = oRepo.findAll();
		
		orders = orders.stream()
					   .filter(e -> (!e.getOrderDateTime().toLocalDate().isAfter(modifiedLastDate) && !e.getOrderDateTime().toLocalDate().isBefore(modifiedFirstDate)) ? true : false)
					   .collect(Collectors.toList());
		
		Set<Integer> customerCount = new HashSet<>();
		
		orders.stream()
		      .forEach(o -> customerCount.add(o.getCustomer().getUserid()));

		
		String conversionRateInLastMonth = df.format((customerCount.size() / cRepo.findAll().size()) * 100) + "%";
		
		
		SalesAnalysisDTO sadt = new SalesAnalysisDTO();
		sadt.setAvgOrderValue(avgOrderValueInLastMonth);
		sadt.setConversionRate(conversionRateInLastMonth);
		sadt.setNoOfOrders(noOfOrdersInLastMonth);
		sadt.setRevenueBreakdown(finalizedRevenueBreakdownInLastMonth);
		sadt.setTopSellingProducts(finalizedTopSellingProductsInLastMonth);
		sadt.setTotalSalesAmount(TotalSalesAmountInLastMonth);
		sadt.setTotalRevenue(totalRevenueInLastMonth);
		
		
		return sadt;
	}
	

	@Override
	public SalesAnalysisDTO getSalesAnalysisOfYear(Integer targetYear) throws SalesAnalysisNotFoundException {
		
		if(targetYear > LocalDate.now().getYear()) {
			throw new SalesAnalysisNotFoundException("Invalid year");
		}
		
		
		LocalDate firstDate = LocalDate.of(targetYear, 1, 1);		
		LocalDate lastDate = LocalDate.of(targetYear, 12, 31);
	
		if(lastDate.getMonthValue() == 1) {
			firstDate = firstDate.withYear(firstDate.getYear() - 1);
			lastDate = lastDate.withYear(lastDate.getYear() - 1);
		}
		
		LocalDate modifiedFirstDate = firstDate;
		LocalDate modifiedLastDate = lastDate;
		
		List<DaywiseSalesData> list = sdRepo.findAll();
		
		list = list.stream()
				   .filter(e -> (!e.getDate().isAfter(modifiedLastDate) && !e.getDate().isBefore(modifiedFirstDate)) ? true : false)
				   .collect(Collectors.toList());
		
		if(list.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No sales data found...");
		}
		
		Double TotalSalesAmountInTheYear = 0.0;	
		Integer noOfOrdersInTheYear = 0;
	    Double avgOrderValueInTheYear = 0.0;
		Double totalRevenueInTheYear = 0.0;
		List<ProductDTOV3> topSellingProductsInTheYear = new ArrayList<>();
		List<RevenueDTO> revenueBreakdownInTheYear = new ArrayList<>();
		
		
		
		for(DaywiseSalesData dsd : list) {
			TotalSalesAmountInTheYear += dsd.getTotalSalesAmount();
			noOfOrdersInTheYear += dsd.getNoOfOrders();
			avgOrderValueInTheYear += dsd.getAvgOrderValue();
			totalRevenueInTheYear += dsd.getTotalRevenue();
			topSellingProductsInTheYear.addAll(dsd.getTopSellingProducts());
			revenueBreakdownInTheYear.addAll(dsd.getRevenueBreakdown());
		}
		

		List<ProductDTOV3> finalizedTopSellingProductsInTheYear 
		      = topSellingProductsInTheYear.stream()
										    .collect(Collectors.groupingBy(p -> p.getProductID()))
										    .entrySet().stream()
										    .map(entry -> {
										    	
										        Integer productId = entry.getKey();
										        List<ProductDTOV3> productDTOV3s = entry.getValue();
										        int noOfProductsSold = 0;
										        double totalSales = 0.0;
										        String categoryName = productDTOV3s.get(0).getCategoryName();
										        
										        for (ProductDTOV3 productDTOV3 : productDTOV3s) {
										            noOfProductsSold += productDTOV3.getNoOfProductsSold();
										            totalSales += productDTOV3.getTotalSales();
										        }
										        return new ProductDTOV3(productId, noOfProductsSold, totalSales, categoryName);
										    })
										    .sorted((c1, c2) -> c2.getNoOfProductsSold() - c1.getNoOfProductsSold())
										    .limit(10)
										    .collect(Collectors.toList());

		
		
		List<RevenueDTO> finalizedRevenueBreakdownInTheYear 
				= revenueBreakdownInTheYear.stream()
											.collect(Collectors.groupingBy(e -> e.getCategoryName()))
											.entrySet().stream()
											.map(entry -> {
												
												Double categorywiseTotalRevenue = 0.0;
												
												List<RevenueDTO> revenueDTOs =  entry.getValue();
												String categoryName = revenueDTOs.get(0).getCategoryName();
												
												for(RevenueDTO rdt : revenueDTOs) {
													categorywiseTotalRevenue += rdt.getCategorywiseTotalRevenue();
												}
												
												return new RevenueDTO(categoryName, categorywiseTotalRevenue, null);
											})
											.collect(Collectors.toList());
		
		
		DecimalFormat df = new DecimalFormat("#.##");	
		
		Double totalRevenue = totalRevenueInTheYear;
		
		finalizedRevenueBreakdownInTheYear = 
				finalizedRevenueBreakdownInTheYear.stream()	
												   .map(e -> {
													   e.setRevenuePercentage(df.format(e.getCategorywiseTotalRevenue() / totalRevenue) + "%") ;
													   return e;
												   })
												   .collect(Collectors.toList());
											
		
		
		List<Orders> orders = oRepo.findAll();
		
		orders = orders.stream()
					   .filter(e -> (!e.getOrderDateTime().toLocalDate().isAfter(modifiedLastDate) && !e.getOrderDateTime().toLocalDate().isBefore(modifiedFirstDate)) ? true : false)
					   .collect(Collectors.toList());
		
		Set<Integer> customerCount = new HashSet<>();
		
		orders.stream()
		      .forEach(o -> customerCount.add(o.getCustomer().getUserid()));
		
		String conversionRateInTheYear = df.format((customerCount.size() / cRepo.findAll().size()) * 100) + "%";
		
		
		SalesAnalysisDTO sadt = new SalesAnalysisDTO();
		sadt.setAvgOrderValue(avgOrderValueInTheYear);
		sadt.setConversionRate(conversionRateInTheYear);
		sadt.setNoOfOrders(noOfOrdersInTheYear);
		sadt.setRevenueBreakdown(finalizedRevenueBreakdownInTheYear);
		sadt.setTopSellingProducts(finalizedTopSellingProductsInTheYear);
		sadt.setTotalSalesAmount(TotalSalesAmountInTheYear);
		sadt.setTotalRevenue(totalRevenueInTheYear);
		
		
		return sadt;
	}


	@Override
	public List<ProductDTOV4> getBestsellingProductByRatingInDuration(LocalDate startDate, LocalDate endDate)
			throws SalesAnalysisNotFoundException {
		
		if(startDate.isAfter(endDate)) {
			throw new SalesAnalysisNotFoundException("Invalid time period");
		}
		
		
		List<DaywiseSalesData> list = sdRepo.findAll();
		
		list = list.stream()
				   .filter(e -> (!e.getDate().isAfter(endDate) && !e.getDate().isBefore(startDate)) ? true : false)
				   .collect(Collectors.toList());
		
		if(list.isEmpty()) {
			throw new SalesAnalysisNotFoundException("No sales data found...");
		}
		
		
		List<ProductDTOV3> topSellingProductsInTheDuration = new ArrayList<>();
		
			
		for(DaywiseSalesData dsd : list) {
			topSellingProductsInTheDuration.addAll(dsd.getTopSellingProducts());
		}
		

		topSellingProductsInTheDuration 
		       = topSellingProductsInTheDuration.stream()
										    .collect(Collectors.groupingBy(p -> p.getProductID()))
										    .entrySet().stream()
										    .map(entry -> {
										    	
										        Integer productId = entry.getKey();
										        List<ProductDTOV3> productDTOV3s = entry.getValue();
										        int noOfProductsSold = 0;
										        double totalSales = 0.0;
										        String categoryName = productDTOV3s.get(0).getCategoryName();
										        
										        for (ProductDTOV3 productDTOV3 : productDTOV3s) {
										            noOfProductsSold += productDTOV3.getNoOfProductsSold();
										            totalSales += productDTOV3.getTotalSales();
										        }
										        return new ProductDTOV3(productId, noOfProductsSold, totalSales, categoryName);
										    })
										    .sorted((c1, c2) -> c2.getNoOfProductsSold() - c1.getNoOfProductsSold())
										    .limit(10)
										    .collect(Collectors.toList());

		
		List<ProductDTOV4> finalizedtopSellingProductsInTheDuration
								= topSellingProductsInTheDuration
								  .stream()
								  .map(p -> {
									  
									    ProductDTOV4 productV4 = new ProductDTOV4();
									    
								        productV4.setProductID(p.getProductID());
								        productV4.setNoOfProductsSold(p.getNoOfProductsSold());
								        productV4.setTotalSales(p.getTotalSales());
								        productV4.setCategoryName(p.getCategoryName());
								        Double avgRating = pRepo.findById(p.getProductID()).get().getAvgRatings();
								        productV4.setAvgRatings(avgRating != null ? avgRating : 0.0);
								        return productV4;
								  })
								  .sorted((p1, p2) -> Double.compare(p2.getAvgRatings(), p1.getAvgRatings()))
								  .collect(Collectors.toList());
		
		return finalizedtopSellingProductsInTheDuration;
	}
	

	@Override
	public Map<String, ProductDTOV5> getBestsellingProductForEachCategoryInDuration(LocalDate startDate, LocalDate endDate)
			throws OrderException {
		
		if(startDate.isAfter(endDate)) {
			throw new OrderException("Invalid date");
		}
		
		List<Orders> orders =  oRepo.findAll();
		
		if(orders.isEmpty()) {
			throw new OrderException("No order found...");
		}
		
		
		orders = orders.stream()
					   .filter(e -> !e.getOrderDateTime().toLocalDate().isAfter(endDate) 
							   		&& !e.getOrderDateTime().toLocalDate().isBefore(startDate))
					   .collect(Collectors.toList());
		
		if(orders.isEmpty()) {
			throw new OrderException("No order found...");
		}
		
		
		Map<String, List<ProductDTOV5>> categorywiseProductList = new LinkedHashMap<>();
		
		Map<String, ProductDTOV5> finalCategorywiseBestSellingProduct = new LinkedHashMap<>();
		
		for(Orders order : orders) {			
			List<OrderDetail> details = order.getDetails();
			
			 details.stream()
					.map(e1 -> {
						
						Product product = e1.getProduct();
						
						Integer categoryID = product.getCategory().getCategoryId();
						String categoryName = product.getCategory().getCategoryName();
						Integer productID = product.getProductId();
						String productName = product.getProductName();
						Double costPrice = product.getCostPrice();
						Double salePrice = product.getPrice();
						Integer noOfProducts = e1.getQuantity();
						Double revenueGenerated = noOfProducts * (salePrice - costPrice);
						
						ProductDTOV5 pdv5 = new ProductDTOV5();
					
						pdv5.setCategoryID(categoryID);
						pdv5.setCategoryName(categoryName);
						pdv5.setCostPrice(costPrice);
						pdv5.setNoOfProducts(noOfProducts);
						pdv5.setProductID(productID);
						pdv5.setProductName(productName);
						pdv5.setRevenueGenerated(revenueGenerated);
						pdv5.setSalePrice(salePrice);
						
						return pdv5;
						
					})
					.collect(Collectors.groupingBy(e2 -> e2.getCategoryName()))
					.forEach((cat, pro) -> {
						
						if(categorywiseProductList.containsKey(cat)) {
							List<ProductDTOV5> productList = categorywiseProductList.get(cat);
							productList.addAll(pro);
							
						} else {
							categorywiseProductList.put(cat, pro);
						}
						
					});

		}
		
		
		categorywiseProductList.entrySet()
							   .stream()
							   .forEach(e -> {
									
									String categoryName = e.getKey();
									List<ProductDTOV5> productList = e.getValue();
									
									ProductDTOV5 bestSellingProduct = 
											productList.stream()
												   .collect(Collectors.groupingBy(e1 -> e1.getProductID()))
												   .entrySet()
												   .stream()
												   .map(e2 -> {
													   
													   List<ProductDTOV5> pdv5 = e2.getValue();
													   Integer categoryID1 = pdv5.get(0).getCategoryID();
													   String categoryName1 = pdv5.get(0).getCategoryName();
													   Integer productID1 = pdv5.get(0).getProductID();
													   String productName1 = pdv5.get(0).getProductName();
													   Double salePrice1 = pdv5.get(0).getSalePrice();
													   Double costPrice1 = pdv5.get(0).getCostPrice();
													   Integer noOfProducts1 = 0;
													   Double revenueGenerated = 0.0;
													   
													   for(ProductDTOV5 p : pdv5) {
														   noOfProducts1 += p.getNoOfProducts();
														   revenueGenerated += p.getRevenueGenerated();
													   }
													   
													   ProductDTOV5 pdv = new ProductDTOV5();
													   pdv.setCategoryID(categoryID1);
													   pdv.setCategoryName(categoryName1);
													   pdv.setCostPrice(costPrice1);
													   pdv.setNoOfProducts(noOfProducts1);
													   pdv.setProductID(productID1);
													   pdv.setProductName(productName1);
													   pdv.setRevenueGenerated(revenueGenerated);
													   pdv.setSalePrice(salePrice1);
													   
													   return pdv;
												   })
												   .sorted((c1, c2) -> c2.getNoOfProducts() - c1.getNoOfProducts())
												   .limit(1)
												   .collect(Collectors.toList())
												   .get(0);
									
									finalCategorywiseBestSellingProduct.put(categoryName, bestSellingProduct);
									
								});
								
		
		
		return finalCategorywiseBestSellingProduct;
	}
	

}
