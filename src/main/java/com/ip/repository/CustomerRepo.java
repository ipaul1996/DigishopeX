package com.ip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	Optional<Customer> findByEmail(String username);

}
