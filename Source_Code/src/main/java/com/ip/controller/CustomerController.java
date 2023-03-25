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

import com.ip.dto.CancelledDTO;
import com.ip.dto.CartDTO;
import com.ip.dto.CartDTOV2;
import com.ip.dto.CartDTOV3;
import com.ip.dto.CustomerDTO;
import com.ip.dto.CustomerDTOV2;
import com.ip.dto.OrderDTO;
import com.ip.dto.ReturnRequestDTO;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
import com.ip.exception.SupplierException;
import com.ip.model.Category;
import com.ip.model.Customer;
import com.ip.model.Orders;
import com.ip.model.Payment;
import com.ip.model.Product;
import com.ip.service.CartService;
import com.ip.service.CategoryService;
import com.ip.service.CustomerService;
import com.ip.service.OrderService;
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
	
	@Autowired
	private OrderService oService;
	
	
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
   
   
   /*   ********************************************************Orders **************************************************************   */

    @Operation(summary = "Place an order", 
    		description = "Customer can place an order by making a payment.\r\n"
    				+ "It returns a ResponseEntity containing an OrderDTO object, "
    				+ "which represents the details of the order. It "
    				+ "contains the following fields:\r\n"
    				+ "orderID, "
    				+ "customerID, "
    				+ "details: a list of OrderDetail objects that represent the details of the products ordered.\r\n"
    				+ "total_order_amount: a double that represents the total cost of the order.\r\n"
    				+ "orderDateTime: a LocalDateTime object that represents the date and time when the order is placed.\r\n"
    				+ "shipDateTime: a LocalDateTime object that represents the date and time when the order will be shipped. "
    				+ "Order will be shipped after one day, time is auto-generated between 10.00 a.m. - 9.00 p.m. measured in 24 hours clock.\r\n"
    				+ "deliveryDateTime: a LocalDateTime object that represents the date and time when the order will be delivered. "
    				+ "Order will be delivered after 5 days, time is auto-generated between 10.00 a.m. - 9.00 p.m. measured in 24 hours clock.\r\n"
    				+ "shipper: Shipper will be assigned dynamically based on the active status of the shipper.\r\n"
    				+ "suppliers: Supplier for each product of different categories will be assigned dynamically "
    				+ "based on the active status and the category of products they do supply.\r\n"
    				+ "The OrderDetail object is a child object of OrderDTO and contains the following fields:\r\n"
    				+ "orderDetailID, quantity: an integer that represents the quantity of the product ordered and some other details. "
    				+ " Also the order status will be updated to 'PENDING'"
    		)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Payment object has two attributes: paymentID which is auto-generated, and PaymentType. "
    		+ " PaymentType represents the type of payment method used by the customer, which can be one of the following options: "
    		+ "DEBITCARD, POD, PAYPAL, WALLET, NETBANKING, or UPI.")
    @PostMapping("/orders/purchase")
    public ResponseEntity<OrderDTO> plcaeOrderHandler(@RequestBody Payment payment) throws SupplierException, ProductException, CustomerException, CredentialException {
    	return new ResponseEntity<>(oService.makePurchase(payment), HttpStatus.ACCEPTED);
    }
   
  
    @Operation(summary = "Cancel an order", description = "Customer can cancel an order by providng a valid orderid. "
    		+ "Cancelling an order is only possible if the product is not shipped yet. After cancelling the product customer "
    		+ "customer will get a response in which a refund datetime will be present. Refund date will be 7 days"
    		+ " later of the cancelled date and refund time is a randomly generated time between 10.00 a.m. - 9.00 p.m. mesaured "
    		+ "in 24 hours clock. Also the orderStatus will be updated to 'CANCELLED_AND_REFUND_IN_PROGESS'. After the "
    		+ " order is being cancelled the products get updated to the stock immediately")
    @DeleteMapping("/orders/cancel/{oid}")
    public ResponseEntity<CancelledDTO> cancelOrderHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
    	return new ResponseEntity<>(oService.cancelOrder(orderID), HttpStatus.OK);
    }
	
    @Operation(summary = "Submit a return request", 
    		description = "With this API endpoint a customer can submit a return request "
    				+ "for a particular order. The method first checks if the customer has made "
    				+ "any orders or not and checks if the order status allows for a return request "
    				+ "to be made. If the order has been delivered within the last 7 days, "
    				+ "the method creates a ReturnRequestedOrders object and sets the "
    				+ "fulfillment(After 3 days of return request submitted) and "
    				+ "refund dates(After 10 days of return request submitted, "
    				+ "time is dynamically generated between between 10.00 a.m - 9.00 p.m. "
    				+ "in a 24 hours clock) accordingly. It also saves the returned order details "
    				+ "and the ReturnRequestedOrders object in the database. Finally, it updates "
    				+ "the order status to \"RETURN_IN_PROGRESS\". When the refund will be completed "
    				+ "then the order status will be \"RETURNED_AND_REFUND_IN_PROGESS\". "
    				+ "The ReturnRequestDTO is returned with the details of the return request, "
    				+ "including the order ID, requested date, fulfillment date, refund date, and "
    				+ "status. On the fulfilment date all the products will be picked from the "
    				+ "customer and stock will be updated with the products(Stock gets updated "
    				+ "every 5 minutes).\r\n"
    				+ "ReturnRequestDTO contains the following fields:\r\n"
    				+ "orderID: the ID of the order for which the return request is being made\r\n"
    				+ "requestedON: the date and time when the return request was made\r\n"
    				+ "fulfillON: the date and time by which the return request should be fulfilled\r\n"
    				+ "refundON: the date and time by which the refund should be processed\r\n"
    				+ "status: the status of the return request\r\n"
    				+ ""
    		)
    @DeleteMapping("/orders/return/{oid}")
	public ResponseEntity<ReturnRequestDTO> submitReturnRequestHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
		return new ResponseEntity<>(oService.submitReturnRequest(orderID), HttpStatus.OK);
	}
	
	
    @Operation(summary = "Track order", description = "Customer can check order status by providing correct orderid")
	@GetMapping("/orders/track/{oid}")
	public ResponseEntity<String> checkOrderStatusHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
		return new ResponseEntity<>(oService.checkOrderStatus(orderID), HttpStatus.OK);
	}
   
    @Operation(summary = "Get all orders", description = "Customer can get all the available orders in the database")
    @GetMapping("/orders/getall")
	public ResponseEntity<List<Orders>> getAllOrdersForCustomerHandler() throws CustomerException, OrderException {
		return new ResponseEntity<>(oService.getAllOrdersForCustomer(), HttpStatus.OK);
	}
	
	
    @Operation(summary = "Get order by orderid", description = "Customer can get an order by providing a correct orderid")
    @GetMapping("/orders/get-by-orderid/{oid}")
	public ResponseEntity<Orders> getOrderByOrderIDHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
		return new ResponseEntity<>(oService.getOrderByOrderID(orderID), HttpStatus.OK);
	}
   
   
}
