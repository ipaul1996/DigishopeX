package com.ip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.LoginDTO;
import com.ip.exception.AdminException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.service.LoginService;



@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	LoginService lService;
	
	@PostMapping("/login")
	public ResponseEntity<String> loginIntoAccount(@RequestBody LoginDTO dto) throws CredentialException, AdminException, CustomerException {
		return new ResponseEntity<String>(lService.loginIntoAccount(dto), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity<String> logoutFromAccount(String token) throws CredentialException {
		return new ResponseEntity<String>(lService.logoutFromAccount(token), HttpStatus.OK);
	}

}
