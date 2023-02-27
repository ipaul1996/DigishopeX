package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Shipper;

@Repository
public interface ShipperRepo extends JpaRepository<Shipper, Integer>{

}
