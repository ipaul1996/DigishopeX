package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer>{

}
