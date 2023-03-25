package com.ip.service;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.exception.CategoryException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.model.ProductRatings;
import com.ip.repository.CategoryRepo;
import com.ip.repository.CustomerRepo;
import com.ip.repository.ProductRatingsRepo;
import com.ip.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo pRepo;
	
	@Autowired
	private CategoryRepo cRepo;
	
	
	@Autowired
	private ProductRatingsRepo prRepo;
	
	@Autowired
	private CustomerRepo custRepo;

	
	public String formatString(String string) {
		
		if(string.isBlank() || string.isBlank() || string == null) {
			return string;
		}
		
		return  Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();		
	}
	
	
	@Override
	public Product createProduct(ProductDTO pdto) throws CategoryException, ProductException {
		
       
		if(pdto.getProductId() != null) {
			throw new ProductException("Product id is auto generated, you need not provide it explicitly");
		}
		
		if(pdto.getCategoryName() == null) {
			throw new CategoryException("You must provide category name while adding a product");
		}
		
		if(pdto.getPrice() < pdto.getCostPrice()) {
			throw new ProductException("Selling price can not be less than the cost price");
		}
		
		Category category = cRepo.findByCategoryName(formatString(pdto.getCategoryName()));
		
		if(category == null) {
			throw new CategoryException("Please give a valid category name");
		}
		
		List<Product> productList = category.getProducts();
		
		Product p;
		
		if(!productList.isEmpty() && productList.stream()
					.anyMatch(p1 -> p1.getProductName().equals(pdto.getProductName()))) {
			
			 p = productList.stream()
						    .filter(p1 -> p1.getProductName().equals(pdto.getProductName()))
						    .collect(Collectors.toList())
						    .get(0);
			
			p.setStockQuantity(p.getStockQuantity() + pdto.getQuanity());
			
		} else {
			
			p = new Product();
			p.setProductName(pdto.getProductName());
			p.setProductImage(pdto.getProductImage());
			p.setDescription(pdto.getDescription());
			p.setCostPrice(pdto.getCostPrice());
			p.setPrice(pdto.getPrice());
			p.setStockQuantity(pdto.getQuanity());
			

			p.setCategory(category);	
			category.getProducts().add(p);
			
		}
		
		return pRepo.save(p);
	}
	
	
	@Override
	public Product addExistingProduct(ProductDTOV2 pdto) throws ProductException {
		
		
		Optional<Product> op = pRepo.findById(pdto.getProductId());
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		op.get().setStockQuantity(op.get().getStockQuantity() + pdto.getQuantity());         
		
		return pRepo.save(op.get());
		
	}

	
	@Override
	public Product updateProduct(ProductDTO pdto) throws ProductException, CategoryException {
		
		if(pdto.getProductId() == null) {
			throw new ProductException("To update a product you must provide a correct product id");
		}
		
		Optional<Product> op =  pRepo.findById(pdto.getProductId());
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		Product p = op.get();
		
		if(pdto.getProductName() != null) {
			p.setProductName(pdto.getProductName());
		}
		
		if(pdto.getProductImage() != null) {
			p.setProductImage(pdto.getProductImage());
		}
		
		if(pdto.getDescription() != null) {
			p.setDescription(pdto.getDescription());
		}
		
		if(pdto.getCostPrice() != null) {
			p.setCostPrice(pdto.getCostPrice());
		}
		
		if(pdto.getPrice() != null) {
			p.setPrice(pdto.getPrice());
		}
		
		if(p.getPrice() < p.getCostPrice()) {
			throw new ProductException("Selling price can not be less than the cost price");
		}
		
		if(pdto.getCategoryName() != null) {
					
			Category category = cRepo.findByCategoryName(formatString(pdto.getCategoryName()));
			
			if(category == null) {
				throw new CategoryException("Please give a valid category name");
			}
			
			p.setCategory(category);			
			category.getProducts().add(p);
			
		}
		
		return pRepo.save(p);
	}

	
	@Override
	public Product deleteProduct(Integer productId) throws ProductException {
		
		
		Optional<Product> op =  pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		pRepo.delete(op.get());
		
		return op.get();
		
	}

	
	@Override
	public Product getProductByProductId(Integer productId) throws ProductException {
		
		Optional<Product> op = pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		return op.get();
		
	}

	
	@Override
	public List<Product> getProductsByCategoryId(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
			
		return products;
	}

	
	@Override
	public List<Product> getProductsByCategoryName(String categoryName)
			throws ProductException, CategoryException {
		
	    Category category = cRepo.findByCategoryName(formatString(categoryName));
		
		if(category == null) {
			throw new CategoryException("Invalid category name");
		}
		
		List<Product> products =  category.getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + categoryName);
		}
			
		return products;
	}

	
	@Override
	public Map<Category, List<Product>> getAllProductsCategorywise()
			throws ProductException, CategoryException {
		
		List<Category> allCategory = cRepo.findAll();
		
		if(allCategory.isEmpty()) {
			throw new CategoryException("No category found");
		}
		
		Map<Category, List<Product>> map = new HashMap<>();
		
		allCategory.forEach(c -> {
			 map.put(c, c.getProducts());
		});
		
		return map;
	}

	
	@Override
	public List<Product> sortProductsByNameAscendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		products = products.stream()
						.sorted((p1, p2) -> formatString(p1.getProductName()).compareTo(formatString(p2.getProductName())))
						.collect(Collectors.toList());
				
		
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByNameDescendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		products = products.stream()
						.sorted((p1, p2) -> formatString(p2.getProductName()).compareTo(formatString(p1.getProductName())))
						.collect(Collectors.toList());

		return products;
	}

	
	@Override
	public List<Product> sortProductsByPriceAscendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		products = products.stream()
						.sorted((p1, p2) -> p1.getPrice() > p2.getPrice() ? +1 : p1.getPrice() < p2.getPrice() ? -1 : 0)
					    .collect(Collectors.toList());
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByPriceDescendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		products = products.stream()
				.sorted((p1, p2) -> p2.getPrice() > p1.getPrice() ? +1 : p2.getPrice() < p1.getPrice() ? -1 : 0)
				.collect(Collectors.toList());

		return products;
	}

	
	@Override
	public List<Product> sortProductsByRatingsAscendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getAvgRatings() > p2.getAvgRatings() ? +1 : p1.getAvgRatings() < p2.getAvgRatings() ? -1 : 0;
		});
		
		products = products.stream()
						.sorted((p1, p2) -> p1.getAvgRatings() > p2.getAvgRatings() ? +1 : p1.getAvgRatings() < p2.getAvgRatings() ? -1 : 0)
						.collect(Collectors.toList());
		
		return products;
		
	}

	
	@Override
	public List<Product> sortProductsByRatingsDescendingForACategory(Integer categoryId)
			throws ProductException, CategoryException {
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		products = products.stream()
				.sorted((p1, p2) -> p2.getAvgRatings() > p1.getAvgRatings() ? +1 : p2.getAvgRatings() < p1.getAvgRatings() ? -1 : 0)
				.collect(Collectors.toList());

		return products;
	}

	
	@Override
	public List<Product> filterProductsByRatingsForACategory(Integer categoryId, Integer minRatings, Integer maxRatings)
			throws ProductException, CategoryException {
		
		Optional<Category> op =  cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("No category found with the is " + categoryId);
		}
		
		if(maxRatings == null || minRatings == null) {
			throw new CategoryException("Invalid ratings range");
		}
		
		Double maxRatings1 = maxRatings.doubleValue();
		Double minRatings1 = minRatings.doubleValue();
		
		if(maxRatings1 <= minRatings1 || minRatings1 < 0 || maxRatings1 > 5) {
			throw new ProductException("Invalid ratings range");
		}
		
		List<Product> products =  op.get().getProducts().stream()
													.filter(p -> p.getAvgRatings() >= minRatings1 && p.getAvgRatings() <= maxRatings1)
													.collect(Collectors.toList());
		
		return products;
	}

	
	@Override
	public List<Product> filterProductsByPriceForACategory(Integer categoryId, Double minPrice, Double maxPrice)
			throws ProductException, CategoryException {
		
		if(categoryId == null) {
			throw new CategoryException("Category is can not be null");
		}
		
		Optional<Category> op =  cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("No category found with the is " + categoryId);
		}
		
		Double maxPrice1 = maxPrice;
		Double minPrice1 = minPrice;
		
		if(maxPrice1 <= minPrice1 || minPrice1 < 1 || maxPrice1 == null || minPrice1 == null) {
			throw new ProductException("Invalid price range");
		}
		
		List<Product> products =  op.get().getProducts().stream()
													.filter(p -> p.getPrice() >= minPrice1 && p.getPrice() <= maxPrice1)
													.collect(Collectors.toList());
		
		return products;
		
	}

	
	@Override
	public Product rateAProduct(Integer productId, Integer ratings)
			throws ProductException {

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Integer customerId = custRepo.findByEmail(auth.getName()).get().getUserid();

		
		Optional<Product> op =  pRepo.findById(productId);

		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(ratings < 0 || ratings > 5) {
			 throw new ProductException("Ratings should be an integeral value between 0 to 5");
		}
		
		ProductRatings productRatings =  prRepo.findByCustomerIdAndProductId(customerId, productId);

		if(productRatings != null) {
			throw new ProductException("You have already rated for the selected product. If you wish you can edit.");
		}

		ProductRatings pr = new ProductRatings();
		pr.setCustomerId(customerId);
		pr.setProductId(productId);
		pr.setRatings(ratings);

		Integer noOfPeople = op.get().getNoOfPeopleRated();
		Double avgRatings =  op.get().getAvgRatings();
		
		DecimalFormat df = new DecimalFormat("#.#");
		Double newAvgRatings = Double.valueOf(df.format(((avgRatings * noOfPeople) + ratings)/(noOfPeople + 1)));
		
		op.get().setAvgRatings(newAvgRatings);
		op.get().setNoOfPeopleRated(noOfPeople + 1);
		op.get().getProductRatings().add(pr);

		
		return pRepo.save(op.get());
		
	}


	@Override
	public Product editRatingsOfAProduct(Integer productId, Integer ratings)
			throws ProductException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Integer customerId = custRepo.findByEmail(auth.getName()).get().getUserid();
		
		Optional<Product> op =  pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(ratings < 0 || ratings > 5) {
			 throw new ProductException("Ratings should be an integeral value between 0 to 5");
		}
		
		ProductRatings productRatings =  prRepo.findByCustomerIdAndProductId(customerId, productId);
		
		if(productRatings == null) {
			throw new ProductException("You haven't rated the product yet");
		}
		
		op.get().getProductRatings().remove(productRatings);
		
		Integer oldRatings = productRatings.getRatings();		
		productRatings.setRatings(ratings);
		
		Integer noOfPeople = op.get().getNoOfPeopleRated();
		Double avgRatings =  op.get().getAvgRatings();
		
		DecimalFormat df = new DecimalFormat("#.#");
		Double newAvgRatings = Double.valueOf(df.format(((avgRatings * noOfPeople) - oldRatings + ratings)/noOfPeople));
		
		op.get().setAvgRatings(newAvgRatings);
		op.get().setNoOfPeopleRated(noOfPeople);
		op.get().getProductRatings().add(productRatings);
		
		return pRepo.save(op.get());
	}

}
