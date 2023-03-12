package com.ip.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ip.model.BlackListedToken;
import com.ip.repository.BlackListedTokenRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtTokenValidatorFilter extends OncePerRequestFilter{
	

	private BlackListedTokenRepo bRepo;
	
	
	public JwtTokenValidatorFilter(BlackListedTokenRepo bRepo) {
		this.bRepo = bRepo;
	}
	
	public boolean isBlackListed(String token) {
		
		Optional<BlackListedToken> op = bRepo.findByToken(token);
		if(op.isEmpty()) return false;
		return true;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
		
		
		if(isBlackListed(jwt)) {
			throw new BadCredentialsException("Invalid Token received..");
		}
		
		
		if(jwt != null) {
			
			try {
				
				jwt = jwt.substring(7);
				
				SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String username = claims.getSubject();
				
				String authorities = String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> auth =  AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
			
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, auth);
			
				SecurityContextHolder.getContext().setAuthentication(authentication);
			
			} catch (Exception e) {
				
				throw new BadCredentialsException("Invalid Token received..");
			}
			
		} 
		
		filterChain.doFilter(request, response);
		
	}
	

	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/authentication/login") ||
		           request.getServletPath().startsWith("/swagger-ui/") ||
		           request.getServletPath().startsWith("/v3/api-docs") ||
		           request.getServletPath().startsWith("/customer/create") || 
		           request.getServletPath().startsWith("/admin/create") ||
		           request.getServletPath().startsWith("/anonymous");
	}

	
}
