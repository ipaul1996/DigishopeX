package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryPriceDTO {
	
	private Integer categoryId;
	private Double minPrice;
	private Double maxPrice;

}
