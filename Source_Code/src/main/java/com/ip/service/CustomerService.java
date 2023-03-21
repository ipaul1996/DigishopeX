package com.ip.service;

import com.ip.dto.CustomerDTO;
import com.ip.dto.CustomerDTOV2;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;
import com.ip.model.Customer;

public interface CustomerService {
	
	public Customer createCustomer(CustomerDTO dto) throws CustomerException;
	
	public Customer updateCustomer(CustomerDTOV2 dto) throws CustomerException;
	
	public Customer deleteCustomer(String email) throws CustomerException, CredentialException, ProductException ;

}
