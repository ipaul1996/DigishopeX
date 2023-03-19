package com.ip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ip.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	LoginService lService;
	
	@Operation(summary = "Admin or Customer login", description = "An admin or customer can login by providing valid credentials."
			+ " Server will send a JWT to the client in the response header which can be used for subsequent api calls")
	@GetMapping("/login")
	public ResponseEntity<String> loginIntoAccountHandler() {
		return new ResponseEntity<String>(lService.loginIntoAccount(), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Admin or Customer logout", description = "An admin or customer can logout by providing a valid JWT"
			+ " Once the admin or customer is loggedout the JWT will be blacklisted and can not be used for any api call")
	@DeleteMapping("/logout")
	public ResponseEntity<String> logoutFromAccountHandler(HttpServletRequest request) {
		return new ResponseEntity<String>(lService.logoutFromAccount(request), HttpStatus.OK);
	}

}
