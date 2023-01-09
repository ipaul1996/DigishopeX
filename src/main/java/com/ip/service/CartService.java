package com.ip.service;


import com.ip.dto.CartDTO;
import com.ip.dto.CartDTOV2;
import com.ip.dto.CartDTOV3;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;


public interface CartService {
	
	public String addToCart(CartDTO dto, String token) throws CredentialException, CustomerException, ProductException;
	
	public String increaseProductQuantity(Integer productId, Integer customerId, String token) throws CredentialException, CustomerException, ProductException;
	
	public String decreaseProductQuantity(Integer productId, Integer customerId, String token) throws CredentialException, CustomerException, ProductException;
	
	public CartDTOV2 deleteFromCart(Integer productId, Integer customerId, String token) throws CredentialException, CustomerException, ProductException;
	
	public CartDTOV3 showCart(Integer customerId, String token) throws CredentialException, CustomerException, ProductException;

}
