package com.ip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.ProductDTO;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.service.CategoryService;
import com.ip.service.ProductService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	CategoryService cService;
	
	@Autowired
	ProductService pService;
	
	@PostMapping("/categories/create")
	public ResponseEntity<Category> addCategory(@Validated @RequestBody Category category, @RequestParam("token") String token) throws CredentialException {
		return new ResponseEntity<Category>(cService.addCategory(category, token), HttpStatus.CREATED);
	}
	
	@PostMapping("/products/create")
	public ResponseEntity<Product> createProductHandler(@RequestBody ProductDTO pdto, @RequestParam("token") String token) throws CredentialException, CategoryException {
		return new ResponseEntity<Product>(pService.createProduct(pdto, token), HttpStatus.CREATED);
	}
	
	@GetMapping("/products/get")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywise(String token) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(token), HttpStatus.FOUND);
	}

	
	
	
	
	
	
}
