package com.ip.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.dto.LoginDTO;
import com.ip.enums.UserType;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.User;
import com.ip.model.UserSession;
import com.ip.repository.AdminRepo;
import com.ip.repository.CustomerRepo;
import com.ip.repository.SessionRepo;


@Service
public class LoginServiceImpl implements LoginService {
	
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private SessionRepo sRepo;
	
		
	@Override
	public String loginIntoAccount(LoginDTO dto) throws CredentialException, AdminException, CustomerException {
		
		User existingUser = null;
		
		if(dto.getUserType().equals(UserType.ADMIN)) {		
		      existingUser = aRepo.findByAdminEmail(dto.getEmail());		 
		      if(existingUser == null) {
					 throw new AdminException("Invalid Email Id");
				}	
		      
		} else if(dto.getUserType().equals(UserType.CUSTOMER)) {
			 existingUser = cRepo.findByCustomerEmail(dto.getEmail());	
			 if(existingUser == null) {
				 throw new CustomerException("Invalid Email Id");
			 }	
		}
					
		 Optional<UserSession> userSession = sRepo.findById(existingUser.getUserid());
		 		 
		 if(userSession.isPresent()) {
			 throw new CredentialException("Customer is already logged in");
		 }
		 		 
		 if(! existingUser.getPassword().equals(dto.getPassword())) {
			 throw new CredentialException("Invalid password");			 
		 }
		 
		 String token = LoginServiceImpl.getToken(16);
		 
		 UserSession session = new UserSession(existingUser.getUserid(), token, LocalDateTime.now(), existingUser.getUserType());
	
		 sRepo.save(session);
		 
		 return "Logged In Successfully.. " + session.toString();
		 
	}

	
	@Override
	public String logoutFromAccount(String token) throws CredentialException {
		
		UserSession session = sRepo.findByToken(token);
		
		if(session == null) {
			throw new CredentialException("User is not logged in with this account");
		}
		
		sRepo.delete(session);
		
		return "Logged Out Successfully... " + LocalDateTime.now();
	}
	
	
	public static String getToken(int n) {
	 
		  String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz@#$%&!?";
		 
		  StringBuilder sb = new StringBuilder(n);
		 
		  for (int i = 1; i <= n; i++) {	 
			   int index = (int)(AlphaNumericString.length() * Math.random());	 
			   sb.append(AlphaNumericString.charAt(index));
		  }
		  
		  return sb.toString();
	 }

	  
	
}
