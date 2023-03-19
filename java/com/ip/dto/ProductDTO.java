package com.ip.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
	
	private Integer productId;
	private String productImage;
	private String productName;
	private String description;
	private Double costPrice;
	private Double price;
	private Integer quanity;
	
	private String categoryName;
	

}
