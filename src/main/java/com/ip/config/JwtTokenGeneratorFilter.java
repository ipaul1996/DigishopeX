package com.ip.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ip.model.Admin;
import com.ip.model.Customer;
import com.ip.repository.AdminRepo;
import com.ip.repository.CustomerRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtTokenGeneratorFilter extends OncePerRequestFilter{
	

	private AdminRepo aRepo;
	

	private CustomerRepo cRepo;
	
	public JwtTokenGeneratorFilter(AdminRepo aRepo, CustomerRepo cRepo) {
		this.aRepo = aRepo;
		this.cRepo = cRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("Hello");
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		
		String role = populateAuthorities(authentication.getAuthorities());
		
		
		
		String name = "NA";
		String address = "NA";
		
		if(role.equals("ROLE_ADMIN")) {	
			Admin admin = aRepo.findByEmail(authentication.getName()).get();
			name = admin.getAdminName();
			
		} else if(role.equals("ROLE_CUSTOMER")) {
			Customer customer = cRepo.findByEmail(authentication.getName()).get();
			name = customer.getCustomerName();
			address = customer.getAddress().toString();
			
		}
		
		if(authentication != null) {
			
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
			
			String jwt = Jwts.builder()
								.setHeaderParam("typ", "JWT")
								.setHeaderParam("alg", "HS512")
								.setIssuer("IP")
								.setSubject(authentication.getName())
								.claim("name", name)
								.claim("authorities", populateAuthorities(authentication.getAuthorities()))
								.claim("address", address)
								.setIssuedAt(new Date())
								.setExpiration(new Date(new Date().getTime() + 30000000))  // expiration time of 8 hours //30000000 is in ms
								.signWith(key).compact();
			
			
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
			
		}
		
		filterChain.doFilter(request, response);
		
		
		
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		
		Set<String> authoritiesSet = new HashSet<>();
		
		for(GrantedAuthority authority : collection) {
			authoritiesSet.add(authority.getAuthority());
		}
		
		return String.join(",", authoritiesSet);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().equals("/authentication/login");	
	}

	
}
