package com.ip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.OrderDetail;

@Repository
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer>{

	Optional<OrderDetail> findByOrderAndProduct(Integer orderID, Integer productID);

}
