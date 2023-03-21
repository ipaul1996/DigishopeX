package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.ReturnedOrderDetails;

@Repository
public interface ReturnedOrderDetailsRepo extends JpaRepository<ReturnedOrderDetails, Integer>{

}
