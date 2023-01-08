package com.ip.service;

import java.util.List;

import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.model.Category;

public interface CategoryService {
	
	  public Category addCategory(Category category, String token) throws CredentialException,  CategoryException; 
	  
	  public Category updateCategory(Category category, String token) throws CategoryException, CredentialException;
	  
	  public Category deleteCategory(Integer categoryId, String token) throws CategoryException, CredentialException;
	  
	  public List<Category> getAllCategory() throws CategoryException;


}