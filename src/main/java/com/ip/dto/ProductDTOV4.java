package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTOV4 {
	
	private Integer productID;
	
	private Integer noOfProductsSold;
	
	private Double totalSales;
	
	private String categoryName;
	
	private Double avgRatings; 

}
