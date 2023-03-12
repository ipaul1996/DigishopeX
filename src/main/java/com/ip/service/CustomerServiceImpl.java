package com.ip.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ip.dto.CustomerDTO;
import com.ip.dto.CustomerDTOV2;
import com.ip.enums.UserRole;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.Customer;
import com.ip.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepo cRepo;
	
	
	@Autowired
	private PasswordEncoder pEncoder;

	
	@Override
	public Customer createCustomer(CustomerDTO dto) throws CustomerException {
		
		Customer customer = new Customer();
		
		
		customer.setCustomerName(dto.getCustomerName());
		customer.setEmail(dto.getCustomerEmail());
		customer.setCustomerMobile(dto.getCustomerMobile());
		customer.setPassword(pEncoder.encode(dto.getPassword()));
		customer.setAddress(dto.getAddress());
		
		
		if(!dto.getRole().toUpperCase().equals("CUSTOMER")) {
			throw new CustomerException("Role should be customer");
		}
		
		customer.setRole(UserRole.ROLE_CUSTOMER);
		
		
		return cRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(CustomerDTOV2 dto) throws CustomerException {

		
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Customer> op = cRepo.findByEmail(customerEmail);
		
		if(op.isEmpty()) {
			throw new CustomerException("Customer details not found");
		}
		
		
		if(dto.getCustomerMobile() != null) {
			op.get().setCustomerMobile(dto.getCustomerMobile());
		}
		
		if(dto.getCustomerName() != null) {
			op.get().setCustomerName(dto.getCustomerName());
		}
		
		if(dto.getPassword() != null) {
			op.get().setPassword(dto.getPassword());
		}
		
		if(dto.getAddress() != null) {
			op.get().setAddress(dto.getAddress());
		}
		
		
		return cRepo.save(op.get());
	}

	@Override
	public Customer deleteCustomer(String email) throws CustomerException, CredentialException {
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		
		String role = getRole(authentication.getAuthorities());
		
		if(role.equals("ROLE_CUSTOMER") && !authentication.getName().equals(email)) {	
			throw new CredentialException("You don't have authority to perform this action");
			
		}
		
		
		Optional<Customer> op = cRepo.findByEmail(email);
		
		if(op.isEmpty()) {
			throw new CustomerException("No details found...");
		}
		
		cRepo.delete(op.get());
		
		return op.get();
	}

	
	
	private String getRole(Collection<? extends GrantedAuthority> authorities) {
		
		return new ArrayList<>(authorities).get(0).toString();

	}

}
