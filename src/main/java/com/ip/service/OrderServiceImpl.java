package com.ip.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.dto.OrderDTO;
import com.ip.enums.UserType;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;
import com.ip.model.Cart;
import com.ip.model.Customer;
import com.ip.model.OrderDetail;
import com.ip.model.Orders;
import com.ip.model.Payment;
import com.ip.model.Product;
import com.ip.model.UserSession;
import com.ip.repository.CartProductQuantityRepo;
import com.ip.repository.CustomerRepo;
import com.ip.repository.OrdersRepo;
import com.ip.repository.SessionRepo;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private SessionRepo sRepo;
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private CartService cService;
	
	@Autowired
	private OrdersRepo oRepo;
	
	@Autowired
	private CartProductQuantityRepo cpqRepo;
	
	@Override
	public OrderDTO makePurchase(String token, Payment payment) throws CredentialException, ProductException, CustomerException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.ADMIN) {
			throw new CredentialException("Please login as a customer");
		}
		
		Customer customer = cRepo.findById(userSession.getUserid()).get();
		
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
		o.setTotal_order_amount(cService.showCart(customer.getUserid(), token).getSubtotal());
		
		Orders o1 = oRepo.save(o);
		
		
		OrderDTO od = new OrderDTO();
		
		od.setCustomerID(customer.getUserid());
		od.setOrderID(o1.getOrderID());
		od.setOrderDateTime(o1.getOrderDateTime());
		od.setDetails(o1.getDetails());
		od.setTotal_order_amount(o1.getTotal_order_amount());

		return od;
	}

	
	

}
