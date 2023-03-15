package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.ReturnRequestedOrders;

@Repository
public interface ReturnRequestedOrdersRepo extends JpaRepository<ReturnRequestedOrders, Integer>{

}
