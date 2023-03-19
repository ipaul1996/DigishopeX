package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Supplier;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer>{

}
