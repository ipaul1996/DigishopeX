package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

}
