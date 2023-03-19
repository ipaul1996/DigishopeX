package com.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "DigishopeX E-Commerce REST API", version = "1.1",
						description = "This application provides a comprehensive set of RESTful "
								+ "web services that can handle various operations of an E-commerce "
								+ "website. There are three types of users - Admin, Customer, and "
								+ "Guest-user, each with their own set of privileges and functionalities.\r\n"
								+ "Admins have the highest level of access and can perform a wide "
								+ "range of operations, such as registering as an admin, "
								+ "creating product categories, adding products to categories, "
								+ "manipulating a list of products, registering suppliers and shippers, "
								+ "and changing their active status, viewing and tracking orders, "
								+ "obtaining sales analysis, and viewing bestselling products. "
								+ "These features give admins complete control over the E-commerce website, "
								+ "allowing them to manage products, suppliers, and customers efficiently.\r\n"
								+ "Customers can register themselves on the platform and view available categories "
								+ "and products, sort and filter products according to their preferences, add, update, "
								+ "and delete products from their cart, place orders, cancel orders, and request returns. "
								+ "Customers can also track their order status, rate products, and access other "
								+ "functionalities that are specific to them.\r\n"
								+ "Guest-users, who have not registered on the platform, also have access to some limited "
								+ "features such as viewing available products and sorting and filtering products.\r\n"
								+ "The application is secured with Spring Security, which provides robust authentication and "
								+ "authorization mechanisms to ensure that users are only able to access the functionalities that "
								+ "they are authorized to use. CORS feature is also incorporated so that the web services can be "
								+ "accessed through any type of client, making it easier for users to interact with the application.\r\n"
								+ "Overall, this RESTful web service provides extensive functionalities to perform various CRUD "
								+ "operations required in an E-commerce application"
								+ ""
						),
						security = {
									@SecurityRequirement(name = "basicAuth"),
									@SecurityRequirement(name = "bearerToken")
								   },
						servers = {
									@Server(url = "/", description = "Default Server URL")
								  }
					
						)

@SecuritySchemes({
	@SecurityScheme(name = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic"),
	@SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
})
public class DigishopeXApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigishopeXApplication.class, args);
	}
	
	
}
