package com.ip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.dto.CategoryDTO;
import com.ip.exception.CategoryException;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.repository.CategoryRepo;



@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepo cRepo;
	
	
	public String formatString(String string) {
		
		if(string.isBlank() || string.isBlank() || string == null) {
			return string;
		}
		
		return  Character.toUpperCase(string.charAt(0)) + string.substring(1).toLowerCase();		
	}

	
	@Override
	public Category addCategory(Category category) throws CategoryException {
		
		
		
		if(category.getCategoryId() != null) {
			throw new CategoryException("Category id is auto generated, you need not provide it explicitly...");
		}
		
		if(cRepo.findByCategoryName(formatString(category.getCategoryName())) != null) {
			throw new CategoryException("Category already exists");
		}
		
		category.setCategoryName(formatString(category.getCategoryName()));
	    
	    return cRepo.save(category);
		
	}

	
	@Override
	public Category updateCategory(CategoryDTO category) throws CategoryException {
		
		
		if(category.getCategoryId() == null) {
			throw new CategoryException("You need to provide correct category id to pergform this action ");
		}
		
	    Optional<Category> op =  cRepo.findById(category.getCategoryId());
	    
	    if(op.isEmpty()) {
	    	throw new CategoryException("Invalid category id");
	    }
	    
	    if(category.getCategoryName() != null) {
	    	op.get().setCategoryName(formatString(category.getCategoryName()));
	    }
	    
	    if(category.getDescription() != null) {
	    	op.get().setDescription(category.getDescription());
	    }
	    
	    if(category.getImageUrl() != null) {
	    	op.get().setImageUrl(category.getImageUrl());
	    }
	    
	    return cRepo.save(op.get());
	}

	
	@Override
	public Category deleteCategory(Integer categoryId) throws CategoryException {
		
	    Optional<Category> op =  cRepo.findById(categoryId);
	    
	    if(op.isEmpty()) {
	    	throw new CategoryException("Invalid category id");
	    }
	    
	    List<Product> products = op.get().getProducts();
	    
	    products.stream().forEach(e -> e.setCategory(null));
	    
	    products.clear();
	    
	    cRepo.delete(op.get());
	    
		return op.get();
	}
	
	
	@Override
	public List<Category> getAllCategory() throws CategoryException {
		
		List<Category> allCategory = cRepo.findAll();
		
		if(allCategory.isEmpty()) {
			throw new CategoryException("No category found");
		}
		return allCategory;
	}

}
