package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.UserSession;

@Repository
public interface SessionRepo extends JpaRepository<UserSession, Integer>{

	public UserSession findByToken(String token);

}
