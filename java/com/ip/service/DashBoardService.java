package com.ip.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ip.dto.ProductDTOV4;
import com.ip.dto.ProductDTOV5;
import com.ip.dto.SalesAnalysisDTO;
import com.ip.exception.OrderException;
import com.ip.exception.SalesAnalysisNotFoundException;
import com.ip.model.DaywiseSalesData;

public interface DashBoardService {
	
	public void doTodaySalesAnalysis();

	
	public DaywiseSalesData getTodaySalesAnalysis() throws SalesAnalysisNotFoundException;
	
	
	public DaywiseSalesData getSalesAnalysisOfDate(LocalDate targetDate) throws SalesAnalysisNotFoundException;
	
	
	public SalesAnalysisDTO getSalesAnalysisOfLastWeek() throws SalesAnalysisNotFoundException;
	
	
	public SalesAnalysisDTO getSalesAnalysisOfLastMonth() throws SalesAnalysisNotFoundException;


	public SalesAnalysisDTO getSalesAnalysisOfYear(Integer targetYear) throws SalesAnalysisNotFoundException;


	public List<ProductDTOV4> getBestsellingProductByRatingInDuration(LocalDate startDate, LocalDate endDate) throws SalesAnalysisNotFoundException;


	public Map<String, ProductDTOV5> getBestsellingProductForEachCategoryInDuration(LocalDate startDate, LocalDate endDate) throws OrderException;

}
