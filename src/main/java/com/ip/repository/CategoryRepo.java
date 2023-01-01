package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	public Category findByCategoryName(String categoryName);

}
