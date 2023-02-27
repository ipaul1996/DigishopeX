package com.ip.service;

import com.ip.dto.OrderDTO;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;
import com.ip.model.Payment;

public interface OrderService {
	
	public OrderDTO makePurchase(String token, Payment payment) throws CredentialException, ProductException, CustomerException;

}
