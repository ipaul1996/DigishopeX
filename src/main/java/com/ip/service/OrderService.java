package com.ip.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ip.dto.CancelledDTO;
import com.ip.dto.DaywiseSalesData;
import com.ip.dto.OrderDTO;
import com.ip.dto.ProductDTOV4;
import com.ip.dto.ProductDTOV5;
import com.ip.dto.ReturnRequestDTO;
import com.ip.dto.SalesAnalysisDTO;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
import com.ip.exception.SalesAnalysisNotFoundException;
import com.ip.exception.ShipperException;
import com.ip.exception.SupplierException;
import com.ip.model.Orders;
import com.ip.model.Payment;
import com.ip.model.Shipper;
import com.ip.model.Supplier;

public interface OrderService {
	
	public Supplier registerSupplier(Supplier supplier) throws SupplierException;
	
	
	public String changeActiveStatusOfSupplier(Integer supplierID) throws SupplierException;
	
	
	public Shipper registerShipper(Shipper shipper) throws ShipperException;
	
	
	public String changeActiveStatusOfShipper(Integer shipperID) throws ShipperException;
	
	
	public OrderDTO makePurchase(Payment payment) throws SupplierException, ProductException, CustomerException, CredentialException;

	
	public CancelledDTO cancelOrder(Integer orderID) throws OrderException;
	
	
	public ReturnRequestDTO submitReturnRequest(Integer orderID) throws OrderException;
	
	
	public void updateStock();


	public void updateOrderStatus();
	
	
	public String checkOrderStatus(Integer orderID) throws OrderException;


	public List<Orders> getAllOrdersForCustomer() throws CustomerException, OrderException;
	
	
	public List<Orders> getAllOrdersForAdmin() throws OrderException;
	

	public Orders getOrderByOrderID(Integer orderID) throws OrderException;


	public List<Orders> getAllOrdersByCustomerID(Integer customerID) throws OrderException, CustomerException;


	public List<Orders> getAllOrdersByCustomerEmail(String email) throws CredentialException, OrderException, CustomerException;


//	public void doTodaySalesAnalysis();

	
//	public DaywiseSalesData getTodaySalesAnalysis() throws SalesAnalysisNotFoundException;
//	
//	
//	public DaywiseSalesData getSalesAnalysisOfDate(LocalDate targetDate) throws SalesAnalysisNotFoundException;
//	
//	
//	public SalesAnalysisDTO getSalesAnalysisOfLastWeek() throws SalesAnalysisNotFoundException;
//	
//	
//	public SalesAnalysisDTO getSalesAnalysisOfLastMonth() throws SalesAnalysisNotFoundException;
//
//
//	public SalesAnalysisDTO getSalesAnalysisOfYear(Integer targetYear) throws SalesAnalysisNotFoundException;
//
//
//	public List<ProductDTOV4> getBestsellingProductByRatingInDuration(LocalDate startDate, LocalDate endDate) throws SalesAnalysisNotFoundException;
//
//
//	public Map<String, ProductDTOV5> getBestsellingProductForEachCategoryInDuration(LocalDate startDate, LocalDate endDate) throws OrderException;


}
