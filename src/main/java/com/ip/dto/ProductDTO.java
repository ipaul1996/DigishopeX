package com.ip.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {
	
	private UUID productId;
	private String productImage;
	private String productName;
	private String description;
	private Double price;
	private Integer quanity;
	
	private String categoryName;
	

}
