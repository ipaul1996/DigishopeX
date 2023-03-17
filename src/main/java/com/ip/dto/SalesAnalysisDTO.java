package com.ip.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesAnalysisDTO {
	
	private Double TotalSalesAmount;	
	private Integer noOfOrders;
    private Double avgOrderValue;
	private Double totalRevenue;
	private List<ProductDTOV3> topSellingProducts;
	private List<RevenueDTO> revenueBreakdown;
	private String conversionRate;

}
