package com.ip.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.enums.UserType;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.Customer;
import com.ip.model.UserSession;
import com.ip.repository.CustomerRepo;
import com.ip.repository.SessionRepo;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private SessionRepo sRepo;

	
	@Override
	public Customer createCustomer(Customer customer) throws CustomerException {
		
		if(customer.getUserid() != null) {
			throw new CustomerException("Id is auto generated, no need to provide it explicitly");
		}
		return cRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer, String token) throws CustomerException, CredentialException {
		
		if(customer.getUserid() == null) {
			throw new CustomerException("Please provide admin id");
		}
		
		Optional<Customer> op = cRepo.findById(customer.getUserid());
		
		if(op.isEmpty()) {
			throw new CustomerException("Invalid id...");
		}
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.ADMIN) {
			throw new CredentialException("Please login as a customer");
		}
		
		if(userSession.getUserid() != customer.getUserid()) {
			throw new CustomerException("You are not authorized to perform this action...");
		}
		
		if(customer.getUserType() == UserType.ADMIN ) {
			throw new CustomerException("Invalid user type");
		}
		
		if(customer.getCustomerEmail() != null) {
			op.get().setCustomerEmail(customer.getCustomerEmail());
		}
		
		if(customer.getCustomerMobile() != null) {
			op.get().setCustomerMobile(customer.getCustomerMobile());
		}
		
		if(customer.getCustomerName() != null) {
			op.get().setCustomerName(customer.getCustomerName());
		}
		
		if(customer.getPassword() != null) {
			op.get().setPassword(customer.getPassword());
		}
		
		if(customer.getAddress() != null) {
			op.get().setAddress(customer.getAddress());
		}
		
		
		return cRepo.save(op.get());
	}

	@Override
	public Customer deleteCustomer(Integer customerId, String token) throws CustomerException, CredentialException {
		
		Optional<Customer> op = cRepo.findById(customerId);
		
		if(op.isEmpty()) {
			throw new CustomerException("Invalid id...");
		}
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.ADMIN) {
			throw new CredentialException("Please login as a customer");
		}
		
		if(userSession.getUserid() != customerId) {
			throw new CustomerException("You are not authorized to perform this action...");
		}
		
		sRepo.delete(userSession);
		cRepo.delete(op.get());
		
		return op.get();
	}

}
