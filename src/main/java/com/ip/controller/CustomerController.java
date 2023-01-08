package com.ip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.model.Category;
import com.ip.model.Customer;
import com.ip.service.CategoryService;
import com.ip.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private CategoryService cService;
	
	
	/*   ********************************************************Customer **************************************************************   */
	
	@PostMapping("/create")
	public ResponseEntity<Customer>  createCustomerHandler(@Validated @RequestBody Customer customer) throws CustomerException {
		return new ResponseEntity<Customer>(custService.createCustomer(customer), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Customer>  updateCustomerHandler(@RequestBody Customer customer, 
																			@RequestParam("token") String token) throws CustomerException, CredentialException {
		return new ResponseEntity<Customer>(custService.updateCustomer(customer, token), HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Customer>  deleteCustomerHandler(@PathVariable("id") Integer customerId, 
																			@RequestParam("token") String token) throws CustomerException, CredentialException {
		return new ResponseEntity<Customer>(custService.deleteCustomer(customerId, token), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Category **************************************************************   */
	
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}

}
