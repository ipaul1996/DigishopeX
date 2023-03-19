package com.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@OpenAPIDefinition(info = @Info(title = "DigishopeX E-Commerce REST API", version = "1.1", description = "This API provides "
		+ "endpoints for managing admin, customer, guest-user, categories, products, cart, orders, payments, Supplier, Shipper "
		+ "Feedback, Report for an e-commerce platform. Spring Security has been used for authentication and authorization."),
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
