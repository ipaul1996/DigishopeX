package com.ip.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ip.dto.OrderDTO;
import com.ip.enums.OrderStatus;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderDetailException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
import com.ip.exception.ShipperException;
import com.ip.exception.SupplierException;
import com.ip.model.Cart;
import com.ip.model.Customer;
import com.ip.model.OrderDetail;
import com.ip.model.Orders;
import com.ip.model.Payment;
import com.ip.model.Product;
import com.ip.model.Shipper;
import com.ip.model.Supplier;
import com.ip.repository.AdminRepo;
import com.ip.repository.CartProductQuantityRepo;
import com.ip.repository.CustomerRepo;
import com.ip.repository.OrderDetailRepo;
import com.ip.repository.OrdersRepo;
import com.ip.repository.ProductRepo;
import com.ip.repository.ShipperRepo;
import com.ip.repository.SupplierRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private CartService cService;
	
	@Autowired
	private OrdersRepo oRepo;
	
	@Autowired
	private CartProductQuantityRepo cpqRepo;
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private OrderDetailRepo odRepo;
	
	@Autowired
	private SupplierRepo suppRepo;
	
	
	@Autowired
	private ShipperRepo shipRepo;
	
	@Autowired
	private ProductRepo pRepo;
	
	
	@Override
	public OrderDTO makePurchase(Payment payment) throws ProductException, CustomerException, CredentialException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Customer customer = cRepo.findByEmail(auth.getName()).get();
		
		Cart cart = customer.getCart();
		
		if(cart == null || cart.getProducts().size() == 0) {
			throw new ProductException("Cart has no product i.e., empty");
		}
		
		final Orders o = new Orders();
		
		List<Product> products = cart.getProducts();
		
		products.stream().forEach(p -> {
			
			OrderDetail od = new OrderDetail();
			
			od.setProduct(p);
			
			od.setQuantity(cpqRepo.findByCartIdAndProductId(cart.getCartId(), customer.getUserid()).getProductQuantity());
			cpqRepo.delete(cpqRepo.findByCartIdAndProductId(cart.getCartId(), customer.getUserid()));
			
			od.setOrder(o);
			o.getDetails().add(od);
			
		});
		
		products.clear();
		cart.setProducts(products);
		
		o.setPayment(payment);
		payment.setOrder(o);
		o.setTotal_order_amount(cService.showCart(customer.getEmail()).getSubtotal());
		
		o.setOrderStatus(OrderStatus.PENDING);
		
	
		Orders o1 = oRepo.save(o);
		
		
		OrderDTO od = new OrderDTO();
		
		od.setCustomerID(customer.getUserid());
		od.setOrderID(o1.getOrderID());
		od.setOrderDate(o1.getOrderDate());
		od.setDetails(o1.getDetails());
		od.setTotal_order_amount(o1.getTotal_order_amount());

		return od;
	}

	
	@Override
	public String cancelOrder(Integer orderID) throws OrderException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Customer customer = cRepo.findByEmail(auth.getName()).get();
		
		Orders od = customer.getOrders().stream()
										.filter(o -> o.getOrderID() == orderID)
										.collect(Collectors.toList())
										.get(0);
		
		if(od == null) {
			throw new OrderException("Invalid orderId");
		}
		
		if(!od.getDeliveryDate().isAfter(LocalDate.now())) {
			throw new OrderException("Proucts are already being delivered so you can't cancel the order now, if not satisfied please return");
		}
		
		if(!LocalDate.now().isBefore(od.getShipDate())) {
			throw new OrderException("Products are already being shipped so you can't cancel the order now, if not satisfied please return after receiving it");
		}
		
		od.getDetails().stream()
					   .forEach(d -> {
						  Product pr = pRepo.findById(d.getProduct().getProductId()).get();					  
						  pr.setStockQuantity(d.getQuantity());
						  pRepo.save(pr);
					   });
		
		
		
		od.setOrderStatus(OrderStatus.CANCELLED);
		
		
		return "Order with " + orderID + " has been cancelled successfully";
		
	}
	
	
	@Override
	public OrderDetail assignSupplier(Integer supplierID, Integer orderID, Integer productID)
			throws SupplierException, OrderDetailException, AdminException {
		
		
		Optional<OrderDetail> op = odRepo.findByOrderAndProduct(orderID, productID);
		
		if(op.isEmpty()) {
			throw new OrderDetailException("No OrderDetail found");
		}
		
		Optional<Supplier> op1 = suppRepo.findById(supplierID);
		
		if(op1.isEmpty()) {
			throw new SupplierException("Invalid supplierID");
		}
		
		if(op.get().getSupplier() != null) {
			throw new SupplierException("Supplier is already been assigned for the order with orderID: " + orderID + ", and product with productID: " + productID);
		}
		
		OrderDetail od1 = op.get();
		
		od1.setSupplier(op1.get());
		op1.get().getDetails().add(od1);
		
		return odRepo.save(od1);
	}

	
	@Override
	public Orders assignShipper(Integer shipperID, Integer orderID)
			throws OrderException, ShipperException {
		
		
		Optional<Orders> op = oRepo.findById(orderID);
		
		if(op.isEmpty()) {
			throw new OrderException("Invalid orderID");
		}
		
		if(op.get().getDeliveryDate() != null || op.get().getShipDate() != null || op.get().getShipper() != null) {
			throw new ShipperException("Shipper is already been assigned for the product with orderID: " + orderID);
		}
		
		
		Optional<Shipper> op1 =  shipRepo.findById(shipperID);
		
		if(op1.isEmpty()) {
			throw new ShipperException("Invalid shipperID");
		}
		
		Orders o = op.get();
		
		Shipper s = op1.get();
		
		o.setShipper(s);
		s.getOrders().add(o);
		
		return oRepo.save(o);
	
	}

	
	@Override
	public Orders assignShipDate(Integer orderID, LocalDate shipDate) throws OrderException, ShipperException {
		
		
		Optional<Orders> op = oRepo.findById(orderID);
		
		
		if(op.isEmpty()) {
			throw new OrderException("Invalid orderID");
		}
		
		if(op.get().getShipper() == null) {
			throw new ShipperException("Shipper is not been assigned for the products with orderID: " + orderID);
		}
		
		if(op.get().getDeliveryDate() != null || op.get().getShipDate() != null ) {
			throw new ShipperException("Shipping date is already been assigned for the products with orderID: " + orderID);
		}
		
		
		if(!(shipDate.isAfter(LocalDate.now()) || shipDate.isEqual(LocalDate.now()))) {			
			throw new ShipperException("Shipping date should be in present or future");
		}
		
		
		op.get().setShipDate(shipDate);
		
		return oRepo.save(op.get());
		
	}

	
	@Override
	public Orders assignDeliveryDate(Integer orderID, LocalDate deliveryDate)
			throws OrderException, ShipperException {
		
		
		Optional<Orders> op = oRepo.findById(orderID);
		
		
		if(op.isEmpty()) {
			throw new OrderException("Invalid orderID");
		}
		
		if(op.get().getShipper() == null) {
			throw new ShipperException("Shipper is not been assigned for the products with orderID: " + orderID);
		}
		
		if(op.get().getShipDate() == null) {
			throw new ShipperException("Shipping date is not assigned yet");
		}
		
		if(op.get().getDeliveryDate() != null) {
			throw new ShipperException("Delivery date is already been assigned for the products with orderID: " + orderID);
		}
		
		
		if(!(deliveryDate.isAfter(LocalDate.now()) || deliveryDate.isEqual(LocalDate.now()))) {			
			throw new ShipperException("Shipping date should be in present or future");
		}
		
		if(! (deliveryDate.isAfter(op.get().getShipDate()))) {
			throw new ShipperException("Delivery date should be post of ship date");
		}
		
		
		op.get().setDeliveryDate(deliveryDate);
		
		return oRepo.save(op.get());
		
	}

	
	@Override
	public String updateOrderStatus(Integer orderID) throws OrderException {
		
		
		Optional<Orders> op = oRepo.findById(orderID);
		
		
		if(op.isEmpty()) {
			throw new OrderException("Invalid orderID");
		}
		
		Orders od = op.get();
		
		
		LocalDate shipDate = od.getShipDate();				
		LocalDate deliveryDate = od.getDeliveryDate();
		
		if(shipDate == null && deliveryDate == null) {
			od.setOrderStatus(OrderStatus.PENDING);
			
		} else if(deliveryDate == null) {
			
			if(shipDate.isAfter(LocalDate.now())) {
				od.setOrderStatus(OrderStatus.PENDING);
				
			} else {
				od.setOrderStatus(OrderStatus.SHIPPED);
				
			}
			
		} else if(!deliveryDate.isAfter(LocalDate.now())) {
			od.setOrderStatus(OrderStatus.DELIVERED);
			
		} else if(deliveryDate.isAfter(LocalDate.now()) && !shipDate.isAfter(LocalDate.now())) {
			od.setOrderStatus(OrderStatus.SHIPPED);
			
		} else if(shipDate.isAfter(LocalDate.now())) {
			od.setOrderStatus(OrderStatus.PENDING);
			
		}
		
		return oRepo.save(od).getOrderStatus().toString();
		

	}

	@Override
	public String checkOrderStatus(Integer orderID) throws OrderException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String role = new ArrayList<>(auth.getAuthorities()).get(0).toString();
		
		Orders od;
		
		if(role.equals("ROLE_CUSTOMER")) {
			
			Customer customer = cRepo.findByEmail(auth.getName()).get();
			
			od = customer.getOrders().stream()
									 .filter(o -> o.getOrderID() == orderID)
									 .collect(Collectors.toList())
									 .get(0);
			
			if(od == null) {
				throw new OrderException("Invalid orderID");
			}

		} else {
			
			
			Optional<Orders> op = oRepo.findById(orderID);
			
			if(op.isEmpty()) {
				throw new OrderException("Invalid orderID");
			}
			
			od = op.get();
		}
		
		return od.getOrderStatus().toString();
		
	}


	@Override
	public String makeRefundRequest(String token, Integer orderID) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String refund(String token, Integer orderID) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	public List<Orders> getAllOrdersForCustomer(String token) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Orders> getAllOrdersForAdmin(String token) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Orders getOrderByOrderID(String token, Integer orderID) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Orders> getAllOrdersByCustomerID(String token, Integer customerID)
			throws CredentialException, OrderException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Orders> getAllOrdersByCustomerEmail(String token, String email)
			throws CredentialException, OrderException, CustomerException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String returnProducts(String token, Integer orderID) throws CredentialException, OrderException {
		// TODO Auto-generated method stub
		return null;
	}



	
}
