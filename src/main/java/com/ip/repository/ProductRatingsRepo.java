package com.ip.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.ProductRatings;

@Repository
public interface ProductRatingsRepo extends JpaRepository<ProductRatings, Integer>{
	
	public ProductRatings findByCustomerIdAndProductId(Integer customerId, UUID productId);

}
