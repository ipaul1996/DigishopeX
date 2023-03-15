package com.ip.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.AdminDTO;
import com.ip.dto.AdminDTOV2;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.exception.AdminException;
import com.ip.exception.CategoryException;
import com.ip.exception.ProductException;
import com.ip.model.Admin;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.service.AdminService;
import com.ip.service.CategoryService;
import com.ip.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;

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
	
	@Operation(summary = "Register as an admin", description = "An user can register himself as an admin")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All the fields are mandatory. "
			+ "The field value for role should be admin, admin name should be minimum of size 3, mobile number should start with 6, 7, 8 or 9"
			+ " and remaining 9 numbers should be between 0 - 9")
	@PostMapping("/create")
	public ResponseEntity<Admin> createAdminHandler(@Validated @RequestBody AdminDTO dto) throws AdminException {
		return new ResponseEntity<>(aService.createAdmin(dto), HttpStatus.CREATED);
	}
	
	
	@Operation(summary = "Update admin details", description = "Admin can update his profile details "
			+ "- name, mobile number, password")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All the fields are optional. "
			+ "Only provided fields will be updated")
	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminHandler(@RequestBody AdminDTOV2 dto) throws AdminException {
		return new ResponseEntity<>(aService.updateAdmin(dto), HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Delete admin details", description = "Admin can delete his all details. "
			+ "This admin has no longer has any authority to perform any action in the application")
	@DeleteMapping("/delete")
	public ResponseEntity<Admin> deleteAdminHandler() throws AdminException {
		return new ResponseEntity<>(aService.deleteAdmin(), HttpStatus.OK);
	}
		
	
	
	/*   ********************************************************Category **************************************************************   */
	
	@Operation(summary = "Create a product category", description = "Admin can create a non-existing product category")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin should not provide any categoryid as it is auto-generated. "
			+ " All other fields are mandatory")
	@PostMapping("/categories/create")
	public ResponseEntity<Category> addCategoryHandler(@Validated @RequestBody Category category) throws CategoryException {
		return new ResponseEntity<Category>(cService.addCategory(category), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Update an existing category details", description = "Admin can upadate an existing categorydetails")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin should provide a valid categoryid to update category details. "
			+ "Here all the fields are optional ")
	@PutMapping("/categories/update")
	public ResponseEntity<Category> updateCategoryHandler(@Validated @RequestBody Category category) throws CategoryException {
		return new ResponseEntity<Category>(cService.updateCategory(category), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "delete an existing category", description = "Admin can delete an existing category by providing the valid categoryid. "
			+ "Point to be noted that all the associated products will not be deleted")
	@DeleteMapping("/categories/delete/{catid}")
	public ResponseEntity<Category> deleteCategoryHandler(@Parameter(description = "Here catid represents categoryid ") @PathVariable("catid") @NotNull Integer categoryId) throws CategoryException {
		return new ResponseEntity<Category>(cService.deleteCategory(categoryId), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all existing categories", description = "Admin can get all the existing categories ")
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Product **************************************************************   */
	
	@Operation(summary = "Create a product", description = "Admin can create a product under a particular category")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin should not provide productId, as it is auto generated. "
			+ "All other fields are mandatory. Also, admin should provide a valid category name for the product")
	@PostMapping("/products/create")
	public ResponseEntity<Product> createProductHandler(@RequestBody ProductDTO pdto) throws CategoryException, ProductException {
		return new ResponseEntity<Product>(pService.createProduct(pdto), HttpStatus.CREATED);
	}
	
	
	@Operation(summary = "Increase product quantity", description = "Admin can increase product quantity in stock by providing valid productid"
			+ " and quantity")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin should provide valid productid and quantity")
	@PostMapping("/products/add")
	public ResponseEntity<Product> addExistingProductHandler(@Validated @RequestBody ProductDTOV2 pdto) throws ProductException {
		return new ResponseEntity<Product>(pService.addExistingProduct(pdto), HttpStatus.CREATED);
	}
	
	
	@Operation(summary = "Update a product", description = "Admin can a update product details by providing valid productid")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Admin should provide valid productid. All other fields are optional."
			+ " If he provides category name, it should be a valid one i.e., it should exist in database")
	@PutMapping("/products/update")
	public ResponseEntity<Product> updateProductHandler(@Validated @RequestBody ProductDTO pdto) throws ProductException, CategoryException {
		return new ResponseEntity<Product>(pService.updateProduct(pdto), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Delete a product", description = "Admin can delete a product by providing valid productid")
	@DeleteMapping("/products/delete/{pid}")
	public ResponseEntity<Product> deleteProductHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productid) throws ProductException {
		return new ResponseEntity<Product>(pService.deleteProduct(productid), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get a product by productId", 
			description = "Admin can retrive a product by providing valid productId")
	@GetMapping("/products/get/{pid}")
	public ResponseEntity<Product> getProductByProductIdHandler(@Parameter(description = "pid represents productId") @PathVariable("pid") @NotNull Integer productId) throws ProductException {
		return new ResponseEntity<Product>(pService.getProductByProductId(productId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products by categoryId", 
			description = "Admin can retrive all products of a category by providing valid categoryId")
	@GetMapping("/products/getbycategoryid/{catid}")
	public ResponseEntity<List<Product>> getProductsByCategoryIdHandler(@Parameter(description = "catid represents categoryId") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products by category name", 
			description = "Admin can retrive all products of a category by providing valid category name")
	@GetMapping("/products/getbycategoryname/{name}")
	public ResponseEntity<List<Product>> getProductsByCategoryNameHandler(@Parameter(description = "name represents categoryName") @PathVariable("name") String categoryName) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryName(categoryName), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products for all categories", 
			description = "Admin can retrive all products for all categories")
	@GetMapping("/products/getall")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywiseHandler() throws ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product name for a category", 
			description = "Admin can sort all products in ascending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product name for a category", 
			description = "Admin can sort all products in descending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product price for a category", 
			description = "Admin can sort all products in ascending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product price for a category", 
			description = "Admin can sort all products in descending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product ratings for a category", 
			description = "Admin can sort all products in ascending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product ratings for a category", 
			description = "Admin can sort all products in descending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Filter products by a price range for a category", 
			description = "Admin can filter products by a price range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyprice/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByPriceForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of price range, It should be >= 1") @PathVariable("min") @NotNull Double minPrice, @Parameter(description = "max represents upper bound of price range") @PathVariable("max") @NotNull Double maxPrice) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByPriceForACategory(categoryId, minPrice, maxPrice), HttpStatus.OK);
	}
	
	@Operation(summary = "Filter products by a rating range for a category", 
			description = "Admin can filter products by a rating range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyratings/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByRatingsForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of ratings range, it should be >= 0") @PathVariable("min") @NotNull Integer minRatings, @Parameter(description = "max represents upper bound of ratings range, it should be <= 5") @PathVariable("max") @NotNull Integer maxRatings) throws ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByRatingsForACategory(categoryId, minRatings, maxRatings), HttpStatus.OK);
	}
	
}
