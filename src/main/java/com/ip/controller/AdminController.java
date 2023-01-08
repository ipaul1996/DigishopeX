package com.ip.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

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

import com.ip.dto.ProductDTO;
import com.ip.exception.AdminException;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.ProductException;
import com.ip.model.Admin;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.service.AdminService;
import com.ip.service.CategoryService;
import com.ip.service.ProductService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService aService;
	
	@Autowired
	CategoryService cService;
	
	@Autowired
	ProductService pService;
	
	
	
	/*   ********************************************************Admin **************************************************************   */
	
	
	@PostMapping("/create")
	public ResponseEntity<Admin>  createAdminHandler(@Validated @RequestBody Admin admin) throws AdminException {
		return new ResponseEntity<Admin>(aService.createAdmin(admin), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Admin>  updateAdminHandler(@RequestBody Admin admin, 
																			@RequestParam("token") String token) throws AdminException, CredentialException {
		return new ResponseEntity<Admin>(aService.updateAdmin(admin, token), HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Admin>  deleteAdminHandler(@PathVariable("id") Integer adminId, 
																			@RequestParam("token") String token) throws AdminException, CredentialException {
		return new ResponseEntity<Admin>(aService.deleteAdmin(adminId, token), HttpStatus.OK);
	}
		
	
	
	/*   ********************************************************Category **************************************************************   */
	
	@PostMapping("/categories/create")
	public ResponseEntity<Category> addCategoryHandler(@Validated @RequestBody Category category, @RequestParam("token") String token) throws CredentialException, CategoryException {
		return new ResponseEntity<Category>(cService.addCategory(category, token), HttpStatus.CREATED);
	}
	
	@PutMapping("/categories/update")
	public ResponseEntity<Category> updateCategoryHandler( @RequestBody Category category,  @RequestParam("token") String token) throws CategoryException, CredentialException {
		return new ResponseEntity<Category>(cService.updateCategory(category, token), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/categories/delete/{id}")
	public ResponseEntity<Category> deleteCategoryHandler(@PathVariable("id") @NotNull Integer categoryId,  @RequestParam("token") String token) throws CategoryException, CredentialException {
		return new ResponseEntity<Category>(cService.deleteCategory(categoryId, token), HttpStatus.OK);
	}
	
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Product **************************************************************   */
	
	
	@PostMapping("/products/create")
	public ResponseEntity<Product> createProductHandler(@RequestBody ProductDTO pdto, @RequestParam("token") String token) throws CredentialException, CategoryException, ProductException {
		return new ResponseEntity<Product>(pService.createProduct(pdto, token), HttpStatus.CREATED);
	}
	
	@GetMapping("/products/getall")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywiseHandler() throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(), HttpStatus.FOUND);
	}

	
	
	
	
	
	
}
