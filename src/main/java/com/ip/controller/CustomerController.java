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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.CartDTO;
import com.ip.dto.CartDTOV2;
import com.ip.dto.CartDTOV3;
import com.ip.dto.CustomerDTO;
import com.ip.dto.CustomerDTOV2;
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

import jakarta.validation.constraints.NotNull;

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
	public ResponseEntity<Customer>  createCustomerHandler(@Validated @RequestBody CustomerDTO dto) throws CustomerException {
		return new ResponseEntity<Customer>(custService.createCustomer(dto), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Customer>  updateCustomerHandler(@Validated @RequestBody CustomerDTOV2 dto) throws CustomerException {
		return new ResponseEntity<Customer>(custService.updateCustomer(dto), HttpStatus.ACCEPTED);
	}
	
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<Customer>  deleteCustomerHandler(@PathVariable("email") String email) throws CustomerException, CredentialException {
		return new ResponseEntity<Customer>(custService.deleteCustomer(email), HttpStatus.OK);
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
	public ResponseEntity<Product> rateAProductHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings) throws ProductException {
		return new ResponseEntity<Product>(pService.rateAProduct(productId, ratings), HttpStatus.OK);
	}
	
	@PutMapping("/products/editratings/{pid}/{ratings}")
	public ResponseEntity<Product> editRatingsOfAProductHandler(@PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings) throws ProductException {
		return new ResponseEntity<Product>(pService.editRatingsOfAProduct(productId, ratings), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Cart **************************************************************   */
	
	
   @PostMapping("/cart/addproduct")
	public ResponseEntity<String> addToCartHandler(@RequestBody CartDTO dto) throws CustomerException, ProductException {
		return new ResponseEntity<String>(cartService.addToCart(dto), HttpStatus.CREATED);
	}
   
   @PutMapping("/cart/increasequantity/{pid}/{cid}")
   public ResponseEntity<String> increaseProductQuantityHandler(@PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.increaseProductQuantity(productId), HttpStatus.ACCEPTED);
   }
   
   @PutMapping("/cart/decreasequantity/{pid}/{cid}")
   public ResponseEntity<String> decreaseProductQuantityHandler(@PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.decreaseProductQuantity(productId), HttpStatus.ACCEPTED);
   }
	
   @DeleteMapping("/cart/deleteproduct/{pid}/{cid}")
   public ResponseEntity<CartDTOV2> deleteFromCartHandler(@PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<CartDTOV2>(cartService.deleteFromCart(productId), HttpStatus.OK);
   }
	
   @GetMapping("/cart/getallproducts/{email}")
   public ResponseEntity<CartDTOV3> showCartHandler(@PathVariable("email") @NotNull String email) throws CustomerException, ProductException, CredentialException {
	   return new ResponseEntity<CartDTOV3>(cartService.showCart(email), HttpStatus.OK);
   }

}
