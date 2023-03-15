package com.ip.service;

import java.util.List;

import com.ip.dto.CancelledDTO;
import com.ip.dto.OrderDTO;
import com.ip.dto.ReturnRequestDTO;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
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


	




}
