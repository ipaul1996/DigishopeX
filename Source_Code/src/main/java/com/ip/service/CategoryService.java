package com.ip.service;

import java.util.List;

import com.ip.dto.CategoryDTO;
import com.ip.exception.CategoryException;
import com.ip.model.Category;

public interface CategoryService {
	
	  public Category addCategory(Category category) throws CategoryException; 
	  
	  public Category updateCategory(CategoryDTO category) throws CategoryException;
	  
	  public Category deleteCategory(Integer categoryId) throws CategoryException;
	  
	  public List<Category> getAllCategory() throws CategoryException;


}