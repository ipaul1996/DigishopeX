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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
	
	@Operation(summary = "Register as a customer", description = "An user can register himself as a customer")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All the fields are mandatory. "
			+ "The field value for role should be customer, customer name should be minimum of size 3, mobile number should start with 6, 7, 8 or 9"
			+ " and remaining 9 numbers should be between 0 - 9")
	@PostMapping("/create")
	public ResponseEntity<Customer>  createCustomerHandler(@Validated @RequestBody CustomerDTO dto) throws CustomerException {
		return new ResponseEntity<Customer>(custService.createCustomer(dto), HttpStatus.CREATED);
	}
	
	
	
	@Operation(summary = "Update customer details", description = "customer can update his profile details "
			+ "- name, mobile number, password")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "All the fields are optional. "
			+ "Only provided fields will be updated")
	@PutMapping("/update")
	public ResponseEntity<Customer>  updateCustomerHandler(@Validated @RequestBody CustomerDTOV2 dto) throws CustomerException {
		return new ResponseEntity<Customer>(custService.updateCustomer(dto), HttpStatus.ACCEPTED);
	}
	
	
	@Operation(summary = "Delete customer details", description = "Customer can delete his all details. "
			+ "This customer has no longer has any authority to perform any action in the application")
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<Customer>  deleteCustomerHandler(@PathVariable("email") String email) throws CustomerException, CredentialException, ProductException {
		return new ResponseEntity<Customer>(custService.deleteCustomer(email), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Category **************************************************************   */
	
	@Operation(summary = "Get all existing categories", description = "Customer can get all the existing categories ")
	@GetMapping("/categories/getall")
	public ResponseEntity<List<Category>>  getAllCategoryHandler() throws CategoryException {
		return new ResponseEntity<List<Category>> (cService.getAllCategory(), HttpStatus.OK);
	}
	
	
/*   ********************************************************Product **************************************************************   */
	
	@Operation(summary = "Get a product by productId", 
			description = "Customer can retrive a product by providing valid productId")
	@GetMapping("/products/get/{pid}")
	public ResponseEntity<Product> getProductByProductIdHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId) throws CredentialException, ProductException {
		return new ResponseEntity<Product>(pService.getProductByProductId(productId), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all products by categoryId", 
			description = "Customer can retrive all products of a category by providing valid categoryId")
	@GetMapping("/products/getbycategoryid/{cid}")
	public ResponseEntity<List<Product>> getProductsByCategoryIdHandler(@Parameter(description = "cid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Get all products by category name", 
			description = "Customer can retrive all products of a category by providing valid category name")
	@GetMapping("/products/getbycategoryname/{name}")
	public ResponseEntity<List<Product>> getProductsByCategoryNameHandler(@Parameter(description = "name represents category name") @PathVariable("name") String categoryName) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.getProductsByCategoryName(categoryName), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all products for all categories", 
			description = "Customer can retrive all products for all categories")
	@GetMapping("/products/getall")
	public ResponseEntity<Map<Category, List<Product>>> getAllProductsCategorywiseHandler() throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<Map<Category,List<Product>>>(pService.getAllProductsCategorywise(), HttpStatus.OK);
	}
	
	@Operation(summary = "Sort products in ascending order of product name for a category", 
			description = "Customer can sort all products in ascending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product name for a category", 
			description = "Customer can sort all products in descending order of product name for a category by providing valid categoryid")
	@GetMapping("/products/sortbyname/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByNameDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByNameDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in ascending order of product price for a category", 
			description = "Customer can sort all products in ascending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product price for a category", 
			description = "Customer can sort all products in descending order of product price for a category by providing valid categoryid")
	@GetMapping("/products/sortbyprice/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByPriceDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByPriceDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	@Operation(summary = "Sort products in ascending order of product ratings for a category", 
			description = "Customer can sort all products in ascending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/asc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsAscendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsAscendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Sort products in descending order of product ratings for a category", 
			description = "Customer can sort all products in descending order of product ratings for a category by providing valid categoryid")
	@GetMapping("/products/sortbyratings/desc/{catid}")
	public ResponseEntity<List<Product>> sortProductsByRatingsDescendingForACategoryHandler(@Parameter(description = "catid represents categoryid") @PathVariable("catid") @NotNull Integer categoryId) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.sortProductsByRatingsDescendingForACategory(categoryId), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Filter products by a price range for a category", 
			description = "Customer can filter products by a price range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyprice/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByPriceForACategoryHandler(@Parameter(description = "cid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of price range") @PathVariable("min") @NotNull Double minPrice, @Parameter(description = "max represents upper bound of price range") @PathVariable("max") @NotNull Double maxPrice) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByPriceForACategory(categoryId, minPrice, maxPrice), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Filter products by a rating range for a category", 
			description = "Customer can filter products by a rating range for a category by providing valid categoryid, "
					+ " lower bound of price range and upper bound of price range")
	@GetMapping("/products/filterbyratings/{cid}/{min}/{max}")
	public ResponseEntity<List<Product>> filterProductsByRatingsForACategoryHandler(@Parameter(description = "cid represents categoryid") @PathVariable("cid") @NotNull Integer categoryId, @Parameter(description = "min represents lower bound of ratings range") @PathVariable("min") @NotNull Integer minRatings, @Parameter(description = "max represents upper bound of ratings range") @PathVariable("max") @NotNull Integer maxRatings) throws CredentialException, ProductException, CategoryException {
		return new ResponseEntity<List<Product>>(pService.filterProductsByRatingsForACategory(categoryId, minRatings, maxRatings), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Rate a product", description = "Customer can rate a product by providing valid productid. "
			+ " Ratings should be in the range of 0 - 5, both inclusive")
	@PostMapping("/products/rate/{pid}/{ratings}")
	public ResponseEntity<Product> rateAProductHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings) throws ProductException {
		return new ResponseEntity<Product>(pService.rateAProduct(productId, ratings), HttpStatus.OK);
	}
	
	@Operation(summary = "Edit ratings for a product", description = "Customer can edit ratings of a product by provding a valid productid. "
			+ " Customer can edit a product if and only if he has rated the product earlier. "
			+ "Ratings should be in the range of 0 - 5, both inclusive")
	@PutMapping("/products/editratings/{pid}/{ratings}")
	public ResponseEntity<Product> editRatingsOfAProductHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId, @PathVariable("ratings") @NotNull Integer ratings) throws ProductException {
		return new ResponseEntity<Product>(pService.editRatingsOfAProduct(productId, ratings), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************Cart **************************************************************   */
	
	@Operation(summary = "Add product in the cart", description = "Customer can add a product to his cart by providing a valid productid. "
			+ " As the product will be added in cart, quantity of the product in the stock will decrease")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer need to provide valid productid and the quantity")
	@PostMapping("/cart/addproduct")
	public ResponseEntity<String> addToCartHandler(@Validated @RequestBody CartDTO dto) throws CustomerException, ProductException {
		return new ResponseEntity<String>(cartService.addToCart(dto), HttpStatus.CREATED);
	}
   
   @Operation(summary = "Increase quantity of a product in the cart", description = "Customer can increase quantity of a product by 1 which already exists in the customer cart "
   		+ " by providing a valid productid. As the product quantity will increase in the cart, quantity of the product in the stock will decrease")
   @PutMapping("/cart/increasequantity/{pid}")
   public ResponseEntity<String> increaseProductQuantityHandler(@Parameter(description = "pid represents productid")@PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.increaseProductQuantity(productId), HttpStatus.ACCEPTED);
   }
   
   @Operation(summary = "Decrease quantity of a product in the cart", description = "Customer can decrease quantity of a product by 1 which already exists in the customer cart "
	   		+ " by providing a valid productid. As the product quantity will decrease in the cart, quantity of the product in the stock will increase")
   @PutMapping("/cart/decreasequantity/{pid}")
   public ResponseEntity<String> decreaseProductQuantityHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<String>(cartService.decreaseProductQuantity(productId), HttpStatus.ACCEPTED);
   }
	
   
   @Operation(summary = "Delete a product from the cart", description = "Customer can delete a product from the cart by providing a valid productid. "
   		+ " As the product is deleted form the cart, the product quantity will increase in the stock")
   @DeleteMapping("/cart/deleteproduct/{pid}")
   public ResponseEntity<CartDTOV2> deleteFromCartHandler(@Parameter(description = "pid represents productid") @PathVariable("pid") @NotNull Integer productId) throws CustomerException, ProductException {
	   return new ResponseEntity<CartDTOV2>(cartService.deleteFromCart(productId), HttpStatus.OK);
   }
	
   @Operation(summary = "Get all products from the cart", description = "Customer can get all the products from the cart by providing his email id")
   @GetMapping("/cart/getallproducts/{email}")
   public ResponseEntity<CartDTOV3> showCartHandler(@PathVariable("email") @NotNull String email) throws CustomerException, ProductException, CredentialException {
	   return new ResponseEntity<CartDTOV3>(cartService.showCart(email), HttpStatus.OK);
   }

}
