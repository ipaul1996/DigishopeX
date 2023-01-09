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

import com.ip.dto.CartDTO;
import com.ip.dto.CartDTOV2;
import com.ip.dto.CartDTOV3;
import com.ip.dto.CategoryPriceDTO;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Customer;
import com.ip.model.Product;
import com.ip.service.CartService;
import com.ip.service.CategoryService;
import com.ip.service.CustomerService;
import com.ip.service.ProductService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private CategoryService cService;
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private CartService cartService;
	
	
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
	
	
	@DeleteMapping("/delete/{cid}")
	public ResponseEntity<Customer>  deleteCustomerHandler(@PathVariable("cid") Integer customerId, 
																			@RequestParam("token") String token) throws CustomerException, CredentialException {
		return new ResponseEntity<Customer>(custService.deleteCustomer(customerId, token), HttpStatus.OK);
	}
	
	
	
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
	
	@GetMapping("/products/getbycategoryid/{cid}")
	public ResponseEntity<List<Product>> getProductsByCategoryIdHandler( @PathVariable("cid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
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
	
	@PostMapping("/products/rate/{pid}/{ratings}")
	public ResponseEntity<Product> rateAProductHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings, @RequestParam("token") String token) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.rateAProduct(productId, ratings, token), HttpStatus.OK);
	}
	
	@PutMapping("/products/editratings/{pid}/{ratings}")
	public ResponseEntity<Product> editRatingsOfAProductHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings, @RequestParam("token") String token) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.editRatingsOfAProduct(productId, ratings, token), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Cart **************************************************************   */
	
	
   @PostMapping("/cart/addproduct")
	public ResponseEntity<String> addToCartHandler(@RequestBody CartDTO dto, @RequestParam("token") String token) throws CredentialException, CustomerException, ProductException {
		return new ResponseEntity<String>(cartService.addToCart(dto, token), HttpStatus.CREATED);
	}
   
   @PutMapping("/cart/increasequantity/{pid}/{cid}")
   public ResponseEntity<String> increaseProductQuantityHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("cid") @NotNull Integer customerId, @RequestParam("token") String token) throws CredentialException, CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.increaseProductQuantity(productId, customerId, token), HttpStatus.ACCEPTED);
   }
   
   @PutMapping("/cart/decreasequantity/{pid}/{cid}")
   public ResponseEntity<String> decreaseProductQuantityHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("cid") @NotNull Integer customerId, @RequestParam("token") String token) throws CredentialException, CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.decreaseProductQuantity(productId, customerId, token), HttpStatus.ACCEPTED);
   }
	
   @DeleteMapping("/cart/deleteproduct/{pid}/{cid}")
   public ResponseEntity<CartDTOV2> deleteFromCartHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("cid") @NotNull Integer customerId, @RequestParam("token") String token) throws CredentialException, CustomerException, ProductException {
	   return new ResponseEntity<CartDTOV2>(cartService.deleteFromCart(productId, customerId, token), HttpStatus.OK);
   }
	
   @GetMapping("/cart/getallproducts/{custid}")
   public ResponseEntity<CartDTOV3> showCartHandler(@PathVariable("custid") @NotNull Integer customerId, @RequestParam("token") String token) throws CredentialException, CustomerException, ProductException {
	   return new ResponseEntity<CartDTOV3>(cartService.showCart(customerId, token), HttpStatus.OK);
   }

}
