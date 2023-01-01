package com.ip.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID>{

	

}
