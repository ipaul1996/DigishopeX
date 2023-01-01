package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.Admin;
import com.ip.model.User;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{

	Admin findByAdminEmail(String email);

}
