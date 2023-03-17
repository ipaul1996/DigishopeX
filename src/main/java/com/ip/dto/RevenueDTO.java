package com.ip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RevenueDTO {
	
	private String categoryName;
	
	private Double categorywiseTotalRevenue;
	
	private String revenuePercentage;

}
