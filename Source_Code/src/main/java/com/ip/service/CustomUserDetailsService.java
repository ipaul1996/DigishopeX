package com.ip.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ip.model.Admin;
import com.ip.model.Customer;
import com.ip.model.User;
import com.ip.repository.AdminRepo;
import com.ip.repository.CustomerRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private AdminRepo aRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> op1 =  cRepo.findByEmail(username);
		Optional<Admin> op2 =  aRepo.findByEmail(username);
		
		if(op1.isEmpty() && op2.isEmpty()) {
			throw new UsernameNotFoundException("Invalid credentials");
			
		} 
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		User user;
		
		if(op1.isEmpty()) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(op2.get().getRole().toString());
			authorities.add(sga);
			user = op2.get();
			
		} else {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(op1.get().getRole().toString());
			authorities.add(sga);
			user = op1.get();
			
		}

		
		return  new CustomUserDetails(user.getEmail(), user.getPassword(),  authorities);
		
	}

	
	

}
