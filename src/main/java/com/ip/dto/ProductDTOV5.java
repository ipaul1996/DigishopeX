package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTOV5 {
	
	private Integer categoryID;
	
	
	private String categoryName;
	
	
	private Integer productID;
	
	
	private String productName;
	
	
	private Double salePrice;
	
	
	private Double costPrice;
	
	
	private Integer noOfProducts;
	
	
	private Double revenueGenerated;

}
