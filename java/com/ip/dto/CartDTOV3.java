package com.ip.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartDTOV3 {
	
	private Integer quantity;
	
	private Double subtotal;
	
	private List<CartDTOV2> list;

}
