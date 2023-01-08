package com.ip.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.enums.UserType;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.model.Category;
import com.ip.model.UserSession;
import com.ip.repository.CategoryRepo;
import com.ip.repository.SessionRepo;



@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	SessionRepo sRepo;
	
	@Autowired
	CategoryRepo cRepo;

	
	@Override
	public Category addCategory(Category category, String token) throws CredentialException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		if(category.getCategoryId() != null) {
			throw new CategoryException("Category id is auto generated, you need not provide it explicitly...");
		}
	    
	    return cRepo.save(category);
		
	}

	
	@Override
	public Category updateCategory(Category category, String token) throws CategoryException, CredentialException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		if(category.getCategoryId() == null) {
			throw new CategoryException("You need to provide correct category id to pergform this action ");
		}
		
	    Optional<Category> op =  cRepo.findById(category.getCategoryId());
	    
	    if(op.isEmpty()) {
	    	throw new CategoryException("Invalid category id");
	    }
	    
	    if(category.getCategoryName() != null) {
	    	op.get().setCategoryName(category.getCategoryName());
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
	public Category deleteCategory(Integer categoryId, String token) throws CategoryException, CredentialException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
	    Optional<Category> op =  cRepo.findById(categoryId);
	    
	    if(op.isEmpty()) {
	    	throw new CategoryException("Invalid category id");
	    }
	    
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
