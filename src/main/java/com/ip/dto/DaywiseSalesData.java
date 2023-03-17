package com.ip.dto;

import java.time.LocalDate;
import java.util.List;

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
public class DaywiseSalesData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serialNo;
	
	
	private Double totalSalesAmount;
	
	
	private Integer noOfOrders;
	
	
	private Double avgOrderValue;
	
	
	private Double totalRevenue;
	
	
	private List<ProductDTOV3> topSellingProducts;
	
	
	private List<RevenueDTO> revenueBreakdown;
	
	
	private String conversionRate;
	
	
	private LocalDate date;
	
	
	

}
