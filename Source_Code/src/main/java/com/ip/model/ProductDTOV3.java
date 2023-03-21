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
public class ProductDTOV3 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Integer serialNO;
	
	private Integer productID;
	
	private Integer noOfProductsSold;
	
	private Double totalSales;
	
	private String categoryName;

	public ProductDTOV3(Integer productID, Integer noOfProductsSold, Double totalSales, String categoryName) {
		super();
		this.productID = productID;
		this.noOfProductsSold = noOfProductsSold;
		this.totalSales = totalSales;
		this.categoryName = categoryName;
	}
	
	

}
