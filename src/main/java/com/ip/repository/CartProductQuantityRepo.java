package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.CartProductQuantity;

@Repository
public interface CartProductQuantityRepo extends JpaRepository<CartProductQuantity, Integer>{

	public CartProductQuantity findByCartIdAndProductId(Integer cId, Integer pId);
}
