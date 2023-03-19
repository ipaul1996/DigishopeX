package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartDTOV2 {
	
	private Integer cartId;
	private String categoryName;
	private Integer productId;
	private String productName;	
	private String imageUrl;
	private Integer quantity;
	private Double price;

}
