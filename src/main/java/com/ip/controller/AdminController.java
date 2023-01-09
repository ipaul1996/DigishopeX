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

import com.ip.dto.CategoryPriceDTO;
import com.ip.dto.CategoryRatingsDTO;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
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
	
	
	@DeleteMapping("/delete/{adid}")
	public ResponseEntity<Admin>  deleteAdminHandler(@PathVariable("adid") Integer adminId, 
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
	
	@DeleteMapping("/categories/delete/{catid}")
	public ResponseEntity<Category> deleteCategoryHandler(@PathVariable("catid") @NotNull Integer categoryId,  @RequestParam("token") String token) throws CategoryException, CredentialException {
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
	
	@PostMapping("/products/add")
	public ResponseEntity<Product> addExistingProductHandler(ProductDTOV2 pdto, @RequestParam("token") String token) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.addExistingProduct(pdto, token), HttpStatus.CREATED);
	}
	
	@PutMapping("/products/update")
	public ResponseEntity<Product> updateProductHandler(ProductDTO pdto, @RequestParam("token") String token) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Product>(pService.updateProduct(pdto, token), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/products/delete/{pid}")
	public ResponseEntity<Product> deleteProductHandler( @PathVariable("pid") @NotNull Integer productid, @RequestParam("token") String token) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.deleteProduct(productid, token), HttpStatus.OK);
	}
	
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
