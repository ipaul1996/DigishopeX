package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Customer;
import com.ip.model.User;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	Customer findByCustomerEmail(String email);

}
