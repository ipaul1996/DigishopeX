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
public class CategoryRatingsDTO {
	
	private Integer categoryId;
	private Integer minRatings;
	private Integer maxRatings;

}
