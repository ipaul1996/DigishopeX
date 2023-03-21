package com.ip.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	
	private String conversionRate;
	
	
	private LocalDate date;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<ProductDTOV3> topSellingProducts;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<RevenueDTO> revenueBreakdown;
	
	
	

}
