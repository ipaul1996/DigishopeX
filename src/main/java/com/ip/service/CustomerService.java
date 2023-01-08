package com.ip.service;

import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer) throws CustomerException;
	
	public Customer updateCustomer(Customer customer, String token) throws CustomerException, CredentialException;
	
	public Customer deleteCustomer(Integer customerId, String token) throws CustomerException, CredentialException;

}
