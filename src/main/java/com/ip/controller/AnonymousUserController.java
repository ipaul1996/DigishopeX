package com.ip.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.service.CategoryService;
import com.ip.service.ProductService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/anonymous")
public class AnonymousUserController {
	
	@Autowired
	private CategoryService cService;
	
	@Autowired
	private ProductService pService;
	
	
/*   ********************************************************Category **************************************************************   */
	
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}
	
	
/*   ********************************************************Product **************************************************************   */
	
	
	@GetMapping("/products/get/{pid}")
	public ResponseEntity<Product> getProductByProductIdHandler( @PathVariable("pid") @NotNull Integer productId) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.getProductByProductId(productId), HttpStatus.OK);
	}
	
	@GetMapping("/products/getbycategoryid/{catid}")
	public ResponseEntity<List<Product>> getProductsByCategoryIdHandler( @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/getbycategoryname/{name}")
	public ResponseEntity<List<Product>> getProductsByCategoryNameHandler( @PathVariable("name") String categoryName) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryName(categoryName), HttpStatus.OK);
	}
	
	@GetMapping("/products/getall")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywiseHandler() throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyname/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameAscendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyname/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameDescendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyprice/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceAscendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyprice/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceDescendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyratings/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsAscendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/sortbyratings/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsDescendingForACategoryHandler(@PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/products/filterbyprice/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByPriceForACategoryHandler(@PathVariable("cid") @NotNull Integer categoryId, @PathVariable("min") @NotNull Double minPrice, @PathVariable("max") @NotNull Double maxPrice) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByPriceForACategory(categoryId, minPrice, maxPrice), HttpStatus.OK);
	}
	
	@GetMapping("/products/filterbyratings/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByRatingsForACategoryHandler(@PathVariable("cid") @NotNull Integer categoryId, @PathVariable("min") @NotNull Integer minRatings, @PathVariable("max") @NotNull Integer maxRatings) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByRatingsForACategory(categoryId, minRatings, maxRatings), HttpStatus.OK);
	}
	

}
