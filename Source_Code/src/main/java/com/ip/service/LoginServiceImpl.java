package com.ip.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ip.config.SecurityConstants;
import com.ip.model.BlackListedToken;
import com.ip.model.User;
import com.ip.repository.AdminRepo;
import com.ip.repository.BlackListedTokenRepo;
import com.ip.repository.CustomerRepo;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class LoginServiceImpl implements LoginService {
	
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private BlackListedTokenRepo bRepo;
		
	@Override
	public String loginIntoAccount() {
		
		User existingUser = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		
	     String role = new ArrayList<>(auth.getAuthorities()).get(0).toString();
	     
	     if(role.equals("ROLE_ADMIN"))  {
	    	 existingUser = aRepo.findByEmail(auth.getName()).get();
	    	 
	     } else if(role.equals("ROLE_CUSTOMER")) {
	    	 existingUser = cRepo.findByEmail(auth.getName()).get();
	    	 
	     }
		

		 return "User with userid " +  existingUser.getUserid() + " has logged In Successfully.. ";
		 
	}

	
	@Override
	public String logoutFromAccount(HttpServletRequest request) {
		
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		
		BlackListedToken tokenDetails = new BlackListedToken();
		
		tokenDetails.setToken(jwt);
		
		bRepo.save(tokenDetails);
		
		return "Logged Out Successfully... " + LocalDateTime.now();
	}
	
	

	  
	
}
