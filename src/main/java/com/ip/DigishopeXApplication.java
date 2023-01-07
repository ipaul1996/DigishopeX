package com.ip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class DigishopeXApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigishopeXApplication.class, args);
	}
	
	

//	public Docket apis() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.ip")).build();
//	}

	
}
