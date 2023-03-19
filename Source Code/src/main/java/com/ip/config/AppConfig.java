package com.ip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.ip.repository.AdminRepo;
import com.ip.repository.BlackListedTokenRepo;
import com.ip.repository.CustomerRepo;

@Configuration
public class AppConfig {
	
	@Autowired
	private AdminRepo aRepo;
	
	@Autowired
	private CustomerRepo cRepo;
	
	@Autowired
	private BlackListedTokenRepo bRepo;
	
	@Bean
	SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		    .authorizeHttpRequests()
		    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
		    .requestMatchers(HttpMethod.POST, "/admin/create").permitAll()
		    .requestMatchers(HttpMethod.PUT, "/admin/update").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.DELETE, "/admin/delete").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.POST, "/admin/categories/create").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.PUT, "/admin/categories/update").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.DELETE, "/admin/categories/delete/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/categories/getall").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.POST, "/admin/products/create").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.POST, "/admin/products/add").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.PUT, "/admin/products/update").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.DELETE, "/admin/products/delete/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/get/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/getbycategoryid/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/getbycategoryname/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/getall").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyname/asc/**").hasRole("ADMIN")	    
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyname/desc/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyprice/asc/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyprice/desc/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyratings/asc/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/sortbyratings/desc/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/filterbyprice/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/products/filterbyratings/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.POST, "/admin/suppliers/register").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.PATCH, "/admin/suppliers/changestatus/**").hasRole("ADMIN")    
		    .requestMatchers(HttpMethod.POST, "/admin/shippers/register").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.PATCH, "/admin/shippers/changestatus/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/orders/track/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/orders/getall").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/orders/get-by-orderid/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/orders/get-by-customerid/**").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/orders/get-by-email").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/today").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/date").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/lastweek").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/lastmonth").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/year").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/bestselling-products-by-ratings").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.GET, "/admin/dashboard/analysis/bestselling-products-by-category").hasRole("ADMIN")
		    .requestMatchers(HttpMethod.POST, "/customer/create").permitAll()
		    .requestMatchers(HttpMethod.PUT, "/customer/update").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.DELETE, "/customer/delete").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/categories/getall").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/get/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/getbycategoryid/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/getbycategoryname/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/getall").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyname/asc/**").hasRole("CUSTOMER")	    
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyname/desc/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyprice/asc/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyprice/desc/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyratings/asc/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/sortbyratings/desc/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/filterbyprice/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/products/filterbyratings/**").hasRole("CUSTOMER")  
		    .requestMatchers(HttpMethod.POST, "/customer/products/rate/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.PUT, "/customer/products/editratings/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.POST, "/customer/cart/addproduct").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.PUT, "/customer/cart/increasequantity/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.PUT, "/customer/cart/decreasequantity/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.DELETE, "/customer/cart/deleteproduct/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/cart/getallproducts/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.POST, "/customer/orders/purchase").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.DELETE, "/customer/orders/cancel/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.DELETE, "/customer/orders/return/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/orders/track/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/orders/getall").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/customer/orders/get-by-orderid/**").hasRole("CUSTOMER")
		    .requestMatchers(HttpMethod.GET, "/anonymous/categories/getall").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/get/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/getbycategoryid/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/getbycategoryname/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/getall").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyname/asc/**").hasRole("ANONYMOUS")	    
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyname/desc/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyprice/asc/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyprice/desc/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyratings/asc/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/sortbyratings/desc/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/filterbyprice/**").hasRole("ANONYMOUS")
		    .requestMatchers(HttpMethod.GET, "/anonymous/products/filterbyratings/**").hasRole("ANONYMOUS")
		    .anyRequest().authenticated()
		    .and()
			.addFilterAfter(new JwtTokenGeneratorFilter(aRepo, cRepo), BasicAuthenticationFilter.class)
		    .addFilterBefore(new JwtTokenValidatorFilter(bRepo), BasicAuthenticationFilter.class)
			.formLogin()
			.and()
			.httpBasic();

		
		
		return http.build();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
