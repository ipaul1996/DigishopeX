package com.ip.service;

import java.time.LocalDate;
import java.util.List;

import com.ip.dto.OrderDTO;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderDetailException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
import com.ip.exception.ShipperException;
import com.ip.exception.SupplierException;
import com.ip.model.OrderDetail;
import com.ip.model.Orders;
import com.ip.model.Payment;

public interface OrderService {
	
	public OrderDTO makePurchase(Payment payment) throws ProductException, CustomerException, CredentialException;

	
	public String cancelOrder(Integer orderID) throws OrderException;
	
	
	public String returnProducts(String token, Integer orderID) throws CredentialException, OrderException;
	
	
	public String makeRefundRequest(String token, Integer orderID) throws CredentialException, OrderException;
	
	
	public String refund(String token, Integer orderID) throws CredentialException, OrderException;
	
	
	public OrderDetail assignSupplier(Integer supplierID, Integer orderID, Integer productID) throws SupplierException, OrderDetailException, AdminException;
	
	
	public Orders assignShipper(Integer shipperID, Integer orderID) throws OrderException, ShipperException;

	
	public Orders assignShipDate(Integer orderID, LocalDate shipDate) throws OrderException, ShipperException;


	public Orders assignDeliveryDate(Integer orderID, LocalDate deliveryDate) throws OrderException, ShipperException;


	public String updateOrderStatus(Integer orderID) throws OrderException;
	
	
	public String checkOrderStatus(Integer orderID) throws OrderException;


	public List<Orders> getAllOrdersForCustomer(String token) throws CredentialException, OrderException;
	
	
	public List<Orders> getAllOrdersForAdmin(String token) throws CredentialException, OrderException;
	

	public Orders getOrderByOrderID(String token, Integer orderID) throws CredentialException, OrderException;


	public List<Orders> getAllOrdersByCustomerID(String token, Integer customerID) throws CredentialException, OrderException, CustomerException;


	public List<Orders> getAllOrdersByCustomerEmail(String token, String email) throws CredentialException, OrderException, CustomerException;


	




}
