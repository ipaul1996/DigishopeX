package com.ip.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ip.dto.CartDTO;
import com.ip.dto.CartDTOV2;
import com.ip.dto.CartDTOV3;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;
import com.ip.model.Cart;
import com.ip.model.CartProductQuantity;
import com.ip.model.Customer;
import com.ip.model.Product;
import com.ip.repository.CartProductQuantityRepo;
import com.ip.repository.CartRepo;
import com.ip.repository.CustomerRepo;
import com.ip.repository.ProductRepo;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepo cRepo;
		
	@Autowired
	private ProductRepo pRepo;
		
	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private CartProductQuantityRepo cpqRepo;

	
	@Override
	public String addToCart(CartDTO dto) throws CustomerException, ProductException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
		if(dto.getProductId() == null || dto.getQuantity() == null || dto.getQuantity() == 0) {
			throw new CustomerException("No field of CartDTO should be null or 0");
		}
	
		
		Optional<Product> op =  pRepo.findById(dto.getProductId());
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(op.get().getStockQuantity() == 0) {
			throw new ProductException("Product is out of stock");
		}
		
		if(dto.getQuantity() > op.get().getStockQuantity()) {
			throw new ProductException("There is not enough product in the stock");
		}
		
		Product p = op.get();		
		Customer c =  custRepo.findByEmail(auth.getName()).get();
		
		if(c.getCart() == null) {
			
			Cart cart = new Cart();
			
			cart.setCustomer(c);
			c.setCart(cart);
			
			p.setStockQuantity(p.getStockQuantity() - dto.getQuantity());
			
			cart.getProducts().add(p);
			p.getCartList().add(cart);
						
			cRepo.save(cart);
			
		} else {
			
			if(c.getCart().getProducts().stream().anyMatch(p1 -> p1.getProductId() == dto.getProductId())) {
//			
				throw new ProductException("Product with ID: " + dto.getProductId() + " has already been"
						+ " added to cart, if required you can increase the product quantity");
				
			} else {
				
				p.setStockQuantity(p.getStockQuantity() - dto.getQuantity());
				
				c.getCart().getProducts().add(p);
				p.getCartList().add(c.getCart());
				
			}
			
			cRepo.save(c.getCart());
		}
		
		CartProductQuantity cpq;
		
		if(cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), dto.getProductId()) != null) {
			cpq = cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), dto.getProductId());
			
		} else {
			cpq = new CartProductQuantity();
		
		}
		
		cpq.setCartId(c.getCart().getCartId());
		cpq.setProductId(dto.getProductId());
		cpq.setProductQuantity(dto.getQuantity());		
	
		cpqRepo.save(cpq);
		
		return "Product with id = " + p.getProductId() + " has been added into the cart";
		
	}

	
	@Override
	public String increaseProductQuantity(Integer productId) throws ProductException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Product> op = pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(op.get().getStockQuantity() == 0) {
			throw new ProductException("Product is out of stock");
		}
		
		Customer c =  custRepo.findByEmail(auth.getName()).get();
		
		if(c.getCart() == null) {
			throw new ProductException("No product found in the cart");
		}
		
		if(c.getCart().getProducts().stream().anyMatch(p -> p.getProductId() == productId)) {
				
			CartProductQuantity cpq = cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), productId);		
			cpq.setProductQuantity(cpq.getProductQuantity() + 1);
			op.get().setStockQuantity(op.get().getStockQuantity() - 1);
			cpqRepo.save(cpq);
			pRepo.save(op.get());
			
		} else {
			throw new ProductException("You can't increase a product quantity which is not added into the cart");
		}
		
		return "Product quantity for the product with id " + productId + "  has been been increased by 1";
	}
	
	@Transactional
	@Override
	public String decreaseProductQuantity(Integer productId) throws ProductException {
		
		 try {
			 
		 
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				
				Optional<Product> op = pRepo.findById(productId);
				
				String result = null;
				
				if(op.isEmpty()) {
					throw new ProductException("Invalid product id");
				}
				
				Customer c =  custRepo.findByEmail(auth.getName()).get();
				
				if(c.getCart() == null) {
					throw new ProductException("No product found in the cart");
				}
				
				if(c.getCart().getProducts().stream().anyMatch(p -> p.getProductId().equals(productId))) {
						
					CartProductQuantity cpq = cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), productId);		
					cpq.setProductQuantity(cpq.getProductQuantity() - 1);
					op.get().setStockQuantity(op.get().getStockQuantity() + 1);
					
					result = "Product quantity for the product with id " + productId + "  has been been decreased by 1";
					if(!cpq.getProductQuantity().equals(0)) {				
						cpqRepo.save(cpq);
						pRepo.save(op.get());
						
					} else {
										
						Product deleteProduct =  c.getCart().getProducts().stream()
														    .filter(p -> p.getProductId().equals(productId))
														    .collect(Collectors.toList())
														    .get(0);
						
						c.getCart().getProducts().remove(deleteProduct);
						cRepo.save(c.getCart());
						cpqRepo.delete(cpq);
						
						result = "Product with id " + productId + " has been removed from the cart";
						
					}
								
				} else {
					throw new ProductException("You can't increase a product quantity which is not added into the cart");
				}
				
				return result;
		
		 } catch(Exception e) {
		        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		        throw e;
		 }
	 
	 }

	
	@Override
	public CartDTOV2 deleteFromCart(Integer productId) throws ProductException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Optional<Product> op = pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		Customer c = custRepo.findByEmail(auth.getName()).get();
		
		CartProductQuantity cpq = cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), productId);	

		if(c.getCart().getProducts().stream().anyMatch(p1 -> p1.getProductId().equals(productId))) {
			
			Product deleteProduct =  c.getCart().getProducts().stream()
															  .filter(p -> p.getProductId().equals(productId))
															  .collect(Collectors.toList())
															  .get(0);
			
			c.getCart().getProducts().remove(deleteProduct);
			
		
			op.get().setStockQuantity(op.get().getStockQuantity() + cpq.getProductQuantity());
			
			
			cRepo.save(c.getCart());							
			cpqRepo.delete(cpq);
			
		} else {
			throw new ProductException("Invalid product id");
		}
		
		CartDTOV2 cv2 = new CartDTOV2();
		cv2.setCartId(c.getCart().getCartId());
		cv2.setCategoryName(op.get().getCategory().getCategoryName());
		cv2.setImageUrl(op.get().getProductImage());
		cv2.setPrice(op.get().getPrice());
		cv2.setProductId(productId);
		cv2.setProductName(op.get().getProductName());
		cv2.setQuantity(cpq.getProductQuantity());
		
		return cv2;
	}

	
	@Override
	public CartDTOV3 showCart(String email) throws ProductException, CredentialException {
		
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		
		String role = getRole(auth.getAuthorities());
		
		if(role.equals("ROLE_CUSTOMER") && !auth.getName().equals(email)) {	
			throw new CredentialException("You don't have authority to perform this action");
			
		}
		
		Customer c = custRepo.findByEmail(email).get();
		
		System.out.println("Hello");
		
		if(c.getCart() == null) {
			throw new ProductException("No product found in the cart");
		}
		
		List<Product> products = c.getCart().getProducts();
		
		System.out.println("Hello1" + products.isEmpty());
		
		if(products.isEmpty()) {
			throw new ProductException("No product found in the cart");
		}
		
		List<CartDTOV2> list = new ArrayList<>();
		List<Integer> quantList = new ArrayList<>();
		List<Double> priceList = new ArrayList<>();
		
		products.forEach(c1 -> {
			CartDTOV2 cv2 = new CartDTOV2();
			cv2.setCartId(c.getCart().getCartId());
			cv2.setCategoryName(c1.getCategory().getCategoryName());
			cv2.setImageUrl(c1.getProductImage());
			cv2.setPrice(c1.getPrice());
			
			cv2.setProductId(c1.getProductId());
			cv2.setProductName(c1.getProductImage());
			Integer quantity = cpqRepo.findByCartIdAndProductId(c.getCart().getCartId(), c1.getProductId()).getProductQuantity();
			cv2.setQuantity(quantity);
			quantList.add(quantity);
			priceList.add(c1.getPrice() * quantity);
			list.add(cv2);
		});
		
		CartDTOV3 cv3 = new CartDTOV3();
		cv3.setList(list);
		cv3.setQuantity(quantList.stream().reduce(0, (s, e) -> s + e));
		cv3.setSubtotal(priceList.stream().reduce(0.0, (s, e) -> s + e));
		
		return cv3;
	}
	
	
	private String getRole(Collection<? extends GrantedAuthority> authorities) {
		
		return new ArrayList<>(authorities).get(0).toString();

	}

}
