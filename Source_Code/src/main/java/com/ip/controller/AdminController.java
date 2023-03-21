package com.ip.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.AdminDTO;
import com.ip.dto.AdminDTOV2;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.dto.ProductDTOV4;
import com.ip.dto.ProductDTOV5;
import com.ip.dto.SalesAnalysisDTO;
import com.ip.exception.AdminException;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.CustomerException;
import com.ip.exception.OrderException;
import com.ip.exception.ProductException;
import com.ip.exception.SalesAnalysisNotFoundException;
import com.ip.exception.ShipperException;
import com.ip.exception.SupplierException;
import com.ip.model.Admin;
import com.ip.model.Category;
import com.ip.model.DaywiseSalesData;
import com.ip.model.Orders;
import com.ip.model.Product;
import com.ip.model.Shipper;
import com.ip.model.Supplier;
import com.ip.service.AdminService;
import com.ip.service.CategoryService;
import com.ip.service.DashBoardService;
import com.ip.service.OrderService;
import com.ip.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService aService;
	
	@Autowired
	private CategoryService cService;
	
	@Autowired
	private ProductService pService;
	
	@Autowired
	private OrderService oService;
	
	@Autowired
	private DashBoardService dService;
	
	
	
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
			+ "This admin has no longer any authority to perform any action in the application")
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
	
	
	
	/*   ********************************************************Orders **************************************************************   */
	
	@Operation(summary = "Register a supplier", 
			description = "Admin can register a supplier by providing all necessary supplier details. "
					+ "Each supplier supplies the products of a particular category")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Mandatory fields: Company name,"
			+ " Moile number, Category name. Optional Fields: City, State, Pincode, Active(By default it is true). "
			+ " Mobile number should start with 6, 7, 8 or 9 and remaining 9 numbers should be between 0 - 9. No need to provide "
			+ "shipperID as it is auto generated")
	@PostMapping("/suppliers/register")
	public ResponseEntity<Supplier> registerSupplierHandler(@Validated @RequestBody Supplier supplier) throws SupplierException {
		return new ResponseEntity<>(oService.registerSupplier(supplier), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Change active status of a supplier", description = "Admin can change the active status "
			+ "of a supplier")
	@PatchMapping("/suppliers/changestatus/{sid}")
	public ResponseEntity<String> changeActiveStatusOfSupplierHandler(@Parameter(description = "sid represents supplierid")@PathVariable("sid") @NotNull Integer supplierID) throws SupplierException {
		return new ResponseEntity<>(oService.changeActiveStatusOfSupplier(supplierID), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Register a shipper", 
			description = "Admin can register a shipper by providing all necessary shipper details")
	@PostMapping("/shippers/register")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Here all the fields are mandatory"
			+ " except shipperID and active status. You need not provide the shipperId as it is auto-generated. Active status "
			+ " is optional field and by default it is true")
	public ResponseEntity<Shipper> registerShipperHandler(@Validated @RequestBody Shipper shipper) throws ShipperException {
		return new ResponseEntity<>(oService.registerShipper(shipper), HttpStatus.ACCEPTED);
	}
	
	@Operation(summary = "Change active status of a shipper", description = "Admin can change the active status "
			+ "of a shipper")
	@PatchMapping("/shippers/changestatus/{sid}")
	public ResponseEntity<String> changeActiveStatusOfShipperHandler(@Parameter(description = "sid represents shipperid") @PathVariable("sid") @NotNull Integer shipperID) throws ShipperException {
		return new ResponseEntity<>(oService.changeActiveStatusOfShipper(shipperID), HttpStatus.OK);
	}
	
	@Operation(summary = "Track order", description = "Admin can check order status by providing correct orderid")
	@GetMapping("/orders/track/{oid}")
	public ResponseEntity<String> checkOrderStatusHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
		return new ResponseEntity<>(oService.checkOrderStatus(orderID), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all orders", description = "Admin can get all the available orders in the database")
	@GetMapping("/orders/getall")
	public ResponseEntity<List<Orders>> getAllOrdersForAdminHandler() throws OrderException {
		return new ResponseEntity<>(oService.getAllOrdersForAdmin(), HttpStatus.OK);
	}
	@Operation(summary = "Get order by orderid", description = "Admin can get an order by providing a correct orderid")
	@GetMapping("/orders/get-by-orderid/{oid}")
	public ResponseEntity<Orders> getOrderByOrderIDHandler(@Parameter(description = "oid represents orderid") @PathVariable("oid") @NotNull Integer orderID) throws OrderException {
		return new ResponseEntity<>(oService.getOrderByOrderID(orderID), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all orders of a customer by customerid", description = "Admin can get all the orders of a "
			+ "customer by provding a correct cutsomerid")
	@GetMapping("/orders/get-by-customerid/{cid}")
	public ResponseEntity<List<Orders>> getAllOrdersByCustomerIDHandler(@Parameter(description = "cid represents customerid") @PathVariable("cid") @NotNull Integer customerID) throws OrderException, CustomerException {
		return new ResponseEntity<>(oService.getAllOrdersByCustomerID(customerID), HttpStatus.OK);
	}
	
	@Operation(summary = "Get all orders of a customer by customer email", description = "Admin can get all the orders of a "
			+ "customer by provding a correct cutsomer email")
	@GetMapping("/orders/get-by-email")
	public ResponseEntity<List<Orders>> getAllOrdersByCustomerEmailHandler(@RequestParam("email") @NotNull String email) throws CredentialException, OrderException, CustomerException {
		return new ResponseEntity<>(oService.getAllOrdersByCustomerEmail(email), HttpStatus.OK);
	}
	
	
	
	/*   ********************************************************DashBoard **************************************************************   */
	
	@Operation(summary = "Get todays' sales analysis", 
			description = "With this API operation admin can get the sales analysis for the current day which "
					+ "returns a DaywiseSalesData object. The DaywiseSalesData class represents a daily sales analysis, which provides important information about the sales of a particular day. This information includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the sales analysis entry.\r\n"
					+ "totalSalesAmount: The total sales amount for the day.\r\n"
					+ "noOfOrders: The number of orders placed on that day.\r\n"
					+ "avgOrderValue: The average order value for the day.\r\n"
					+ "totalRevenue: The total revenue generated for the day.\r\n"
					+ "conversionRate: The conversion rate for the day.\r\n"
					+ "date: The date for which the sales analysis is being performed.\r\n"
					+ "topSellingProducts: A list of the top-selling products on that day, represented by the ProductDTOV3 class.\r\n"
					+ "revenueBreakdown: A list of the revenue breakdown by category on that day, represented by the RevenueDTO class.\r\n"
					+ "The ProductDTOV3 class represents the details of a product that has been sold on a particular day. It includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the product entry.\r\n"
					+ "productID: The unique identifier of the product.\r\n"
					+ "noOfProductsSold: The number of units sold for the product.\r\n"
					+ "totalSales: The total sales generated for the product.\r\n"
					+ "categoryName: The category name of the product.\r\n"
					+ "The RevenueDTO class represents the revenue breakdown by category on a particular day. It includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the revenue entry.\r\n"
					+ "categoryName: The category name for which the revenue is being calculated.\r\n"
					+ "categorywiseTotalRevenue: The total revenue generated for the category.\r\n"
					+ "revenuePercentage: The percentage of revenue generated by the category out of the total revenue for the day.\r\n"
					+ ""
			)
	@GetMapping("/dashboard/analysis/today")
	public ResponseEntity<DaywiseSalesData> getTodaySalesAnalysisHandler() throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getTodaySalesAnalysis(), HttpStatus.OK);
	}
	
	@Operation(summary = "Get sales analysis of a particular date", 
			description = "With this API operation admin can get the sales analysis for the provided date which returns a DaywiseSalesData object.\r\n"
					+ "The DaywiseSalesData class represents a daily sales analysis, which provides important information about the sales of a particular day. This information includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the sales analysis entry.\r\n"
					+ "totalSalesAmount: The total sales amount for the day.\r\n"
					+ "noOfOrders: The number of orders placed on that day.\r\n"
					+ "avgOrderValue: The average order value for the day.\r\n"
					+ "totalRevenue: The total revenue generated for the day.\r\n"
					+ "conversionRate: The conversion rate for the day.\r\n"
					+ "date: The date for which the sales analysis is being performed.\r\n"
					+ "topSellingProducts: A list of the top-selling products on that day, represented by the ProductDTOV3 class.\r\n"
					+ "revenueBreakdown: A list of the revenue breakdown by category on that day, represented by the RevenueDTO class.\r\n"
					+ "The ProductDTOV3 class represents the details of a product that has been sold on a particular day. It includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the product entry.\r\n"
					+ "productID: The unique identifier of the product.\r\n"
					+ "noOfProductsSold: The number of units sold for the product.\r\n"
					+ "totalSales: The total sales generated for the product.\r\n"
					+ "categoryName: The category name of the product.\r\n"
					+ "The RevenueDTO class represents the revenue breakdown by category on a particular day. It includes:\r\n"
					+ "serialNo: An auto-generated unique identifier for the revenue entry.\r\n"
					+ "categoryName: The category name for which the revenue is being calculated.\r\n"
					+ "categorywiseTotalRevenue: The total revenue generated for the category.\r\n"
					+ "revenuePercentage: The percentage of revenue generated by the category out of the total revenue for the day.\r\n"
					+ ""
			)
	@GetMapping("/dashboard/analysis/date")
	public ResponseEntity<DaywiseSalesData> getSalesAnalysisOfDateHandler(@Parameter(description = "dt represents the Target Date") @RequestParam("dt") @NotNull LocalDate targetDate) throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getSalesAnalysisOfDate(targetDate), HttpStatus.OK);
	}
	
	@Operation(summary = "Get sales analysis of the last week", 
			description = "With this API operation admin can get SalesAnalysisDTO object that contains information about the sales analysis of the last week. The SalesAnalysisDTO object has several properties:\r\n"
					+ "\"TotalSalesAmount\" - the total sales amount of all orders in the last week\r\n"
					+ "\"noOfOrders\" - the total number of orders placed in the last week\r\n"
					+ "\"avgOrderValue\" - the average value of orders in the last week\r\n"
					+ "\"totalRevenue\" - the total revenue generated from sales in the last week\r\n"
					+ "\"topSellingProducts\" - a list of ProductDTOV3 objects that represent the top selling products in the last week\r\n"
					+ "\"revenueBreakdown\" - a list of RevenueDTO objects that represent the revenue breakdown by category in the last week\r\n"
					+ "\"conversionRate\" - the conversion rate of the website in the last week\r\n"
					+ "The ProductDTOV3 object has several properties:\r\n"
					+ "\"productID\" - the ID of the product\r\n"
					+ "\"noOfProductsSold\" - the number of products sold in the last week\r\n"
					+ "\"totalSales\" - the total sales of the product in the last week\r\n"
					+ "\"categoryName\" - the category name of the product\r\n"
					+ "The RevenueDTO object has several properties:\r\n"
					+ "\"categoryName\" - the category name of the revenue breakdown\r\n"
					+ "\"categorywiseTotalRevenue\" - the total revenue generated from sales in the last week for that category\r\n"
					+ "\"revenuePercentage\" - the percentage of revenue generated from sales in the last week for that category compared to the total revenue generated from sales in the last week.\r\n"
					+ ""
			)
	@GetMapping("/dashboard/analysis/lastweek")
	public ResponseEntity<SalesAnalysisDTO> getSalesAnalysisOfLastWeekHandler() throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getSalesAnalysisOfLastWeek(), HttpStatus.OK);
	}
	
	@Operation(summary = "Get sales analysis of the last month", 
			description = "With this API operation admin can get SalesAnalysisDTO object that contains "
					+ "information about the sales analysis of the last month. "
					+ "The SalesAnalysisDTO object has several properties: "
					+ "\"TotalSalesAmount\" - the total sales amount of all orders in the last month "
					+ "\"noOfOrders\" - the total number of orders placed in the last month "
					+ "\"avgOrderValue\" - the average value of orders in the last month "
					+ "\"totalRevenue\" - the total revenue generated from sales in the last month "
					+ "\"topSellingProducts\" - a list of ProductDTOV3 objects that represent "
					+ "the top selling products in the last month "
					+ "\"revenueBreakdown\" - a list of RevenueDTO objects that represent "
					+ "the revenue breakdown by category in the last month "
					+ "\"conversionRate\" - the conversion rate of the website in the last month. "
					+ "The ProductDTOV3 object has several properties: "
					+ "\"productID\" - the ID of the product "
					+ "\"noOfProductsSold\" - the number of products sold in the last month "
					+ "\"totalSales\" - the total sales of the product in the last month "
					+ "\"categoryName\" - the category name of the product. "
					+ "The RevenueDTO object has several properties: "
					+ "\"categoryName\" - the category name of the revenue breakdown "
					+ "\"categorywiseTotalRevenue\" - the total revenue generated from sales in "
					+ "the last month for that category "
					+ "\"revenuePercentage\" - the percentage of revenue generated from sales "
					+ "in the last month for that category compared to the total revenue "
					+ "generated from sales in the last month.\r\n"
					+ ""
			)
	@GetMapping("/dashboard/analysis/lastmonth")
	public ResponseEntity<SalesAnalysisDTO> getSalesAnalysisOfLastMonthHandler() throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getSalesAnalysisOfLastMonth(), HttpStatus.OK);
	}

	@Operation(summary = "Get sales analysis of an year", 
			description = "With this API operation admin can get SalesAnalysisDTO object that contains information about "
					+ "the sales analysis of the given year. The SalesAnalysisDTO object has "
					+ "several properties: \"TotalSalesAmount\" - the total sales amount of all orders in "
					+ "the given year \"noOfOrders\" - the total number of orders placed in the given year "
					+ "\"avgOrderValue\" - the average value of orders in the given year "
					+ "\"totalRevenue\" - the total revenue generated from sales in the given year "
					+ "\"topSellingProducts\" - a list of ProductDTOV3 objects that represent "
					+ "the top selling products in the given year "
					+ "\"revenueBreakdown\" - a list of RevenueDTO objects that represent the revenue breakdown "
					+ "by category in the given year "
					+ "\"conversionRate\" - the conversion rate of the website in the given year. "
					+ "The ProductDTOV3 object has several properties: "
					+ "\"productID\" - the ID of the product "
					+ "\"noOfProductsSold\" - the number of products sold in the given year "
					+ "\"totalSales\" - the total sales of the product in the given year "
					+ "\"categoryName\" - the category name of the product. "
					+ "The RevenueDTO object has several properties: "
					+ "\"categoryName\" - the category name of the revenue breakdown "
					+ "\"categorywiseTotalRevenue\" - the total revenue generated from sales in the given year "
					+ "for that category \"revenuePercentage\" - the percentage of revenue generated from sales "
					+ "in the given year for that category compared to the total revenue generated from sales "
					+ "in the given year."
			)
	@GetMapping("/dashboard/analysis/year")
	public ResponseEntity<SalesAnalysisDTO> getSalesAnalysisOfYearHandler(@Parameter(description = "yr represents target year. "
			+ " This year should be a past year or the present one") @RequestParam("yr") @NotNull Integer targetYear) throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getSalesAnalysisOfYear(targetYear), HttpStatus.OK);
	}

	@Operation(summary = "Get bestselling products by ratings in a certain duration", 
			description = "With this API endpoint admin can retrieve a list of bestselling products based on their ratings within a certain time period. "
					+ "The endpoint accepts two parameters, syr and eyr, which represent the start and end dates of the duration to be analyzed(eyr > syr). "
					+ "The response will contain a list of ProductDTOV4 objects, each of which represents a bestselling product during the specified duration.\r\n"
					+ "The ProductDTOV4 class has the following fields:\r\n"
					+ "productID: an integer representing the unique identifier of the product.\r\n"
					+ "noOfProductsSold: an integer representing the total number of units of the product sold during the specified duration.\r\n"
					+ "totalSales: a double representing the total sales generated by the product during the specified duration.\r\n"
					+ "categoryName: a string representing the name of the category to which the product belongs.\r\n"
					+ "avgRatings: a double representing the average rating received by the product during the specified duration.\r\n"
					+ "")
	@GetMapping("/dashboard/analysis/bestselling-products-by-ratings")
	public ResponseEntity<List<ProductDTOV4>> getBestsellingProductByRatingInDurationHandler(@Parameter(description = "syr represents start date") @RequestParam("syr") @NotNull LocalDate startDate, @Parameter(description = "eyr represents end date") @RequestParam("eyr") @NotNull LocalDate endDate) throws SalesAnalysisNotFoundException {
		return new ResponseEntity<>(dService.getBestsellingProductByRatingInDuration(startDate, endDate), HttpStatus.OK);
	}

	@Operation(summary = "Get bestselling products for each category in a certain duration", 
			description = "With this API endpoint admin can retrieve the bestselling products for each category "
					+ "within a specified duration. It accepts two parameters, "
					+ "\"syr\" representing the start date and \"eyr\" representing the end date, "
					+ "both of which are required.\r\n"
					+ "ProductDTOV5 fields:\r\n"
					+ "categoryID: an integer representing the ID of the product's category.\r\n"
					+ "categoryName: a string representing the name of the product's category.\r\n"
					+ "productID: an integer representing the ID of the product.\r\n"
					+ "productName: a string representing the name of the product.\r\n"
					+ "salePrice: a double representing the selling price of the product.\r\n"
					+ "costPrice: a double representing the cost price of the product.\r\n"
					+ "noOfProducts: an integer representing the number of products sold in the specified duration.\r\n"
					+ "revenueGenerated: a double representing the revenue generated by the product within the specified duration.")
	@GetMapping("/dashboard/analysis/bestselling-products-by-category")
	public ResponseEntity<Map<String, ProductDTOV5>> getBestsellingProductForEachCategoryInDurationHandler(@Parameter(description = "syr represents start date") @RequestParam("syr") @NotNull LocalDate startDate, @Parameter(description = "eyr represents end date") @RequestParam("eyr") @NotNull LocalDate endDate) throws OrderException {
		return new ResponseEntity<>(dService.getBestsellingProductForEachCategoryInDuration(startDate, endDate), HttpStatus.OK);
	}



}
