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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/anonymous")
public class AnonymousUserController {
	
	@Autowired
	private CategoryService cService;
	
	@Autowired
	private ProductService pService;
	
	
/*   ********************************************************Category **************************************************************   */
	
	@Operation(summary = "Get all existing categories", description = "Guest user can get all the existing categories ")
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}
	
	
/*   ********************************************************Product **************************************************************   */
	
	@Operation(summary = "Get a product by productId", 
			description = "Guest user can retrive a product by providing valid productId")
	@GetMapping("/products/get/{pid}")
	public ResponseEntity<Product> getProductByProductIdHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.getProductByProductId(productId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products by categoryId", 
			description = "Guest user can retrive all products of a category by providing valid categoryId")
	@GetMapping("/products/getbycategoryid/{catid}")
	public ResponseEntity<List<Product>> getProductsByCategoryIdHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products by category name", 
			description = "Guest user can retrive all products of a category by providing valid category name")
	@GetMapping("/products/getbycategoryname/{name}")
	public ResponseEntity<List<Product>> getProductsByCategoryNameHandler(@Parameter(description = "name represents category name") @PathVariable("name") String categoryName) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryName(categoryName), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products for all categories", 
			description = "Guest user can retrive all products for all categories")
	@GetMapping("/products/getall")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywiseHandler() throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(), HttpStatus.OK);
	}
	
	@Operation(summary = "Sort products in ascending order of product name for a category", 
			description = "Guest user can sort all products in ascending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product name for a category", 
			description = "Guest user can sort all products in descending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product price for a category", 
			description = "Guest user can sort all products in ascending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product price for a category", 
			description = "Guest user can sort all products in descending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product ratings for a category", 
			description = "Guest user can sort all products in ascending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product ratings for a category", 
			description = "Guest user can sort all products in descending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Filter products by a price range for a category", 
			description = "Guest user can filter products by a price range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyprice/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByPriceForACategoryHandler(@Parameter(description = "cid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of price range") @PathVariable("min") @NotNull Double minPrice, @Parameter(description = "max represents upper bound of price range") @PathVariable("max") @NotNull Double maxPrice) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByPriceForACategory(categoryId, minPrice, maxPrice), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Filter products by a rating range for a category", 
			description = "Guest user can filter products by a rating range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyratings/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByRatingsForACategoryHandler(@Parameter(description = "cid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of ratings range") @PathVariable("min") @NotNull Integer minRatings, @Parameter(description = "max represents upper bound of ratings range") @PathVariable("max") @NotNull Integer maxRatings) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByRatingsForACategory(categoryId, minRatings, maxRatings), HttpStatus.OK);
	}
	

}
