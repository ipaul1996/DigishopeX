package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
	
	
	private Integer categoryId;
	
	private String categoryName;

	private String imageUrl;
	
	private String description;

}
