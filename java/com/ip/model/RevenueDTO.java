package com.ip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RevenueDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer serialNO;
	
	private String categoryName;
	
	private Double categorywiseTotalRevenue;
	
	private String revenuePercentage;

	public RevenueDTO(String categoryName, Double categorywiseTotalRevenue, String revenuePercentage) {
		super();
		this.categoryName = categoryName;
		this.categorywiseTotalRevenue = categorywiseTotalRevenue;
		this.revenuePercentage = revenuePercentage;
	}
	
	

}
