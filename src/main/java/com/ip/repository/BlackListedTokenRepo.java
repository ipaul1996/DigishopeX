package com.ip.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.BlackListedToken;

@Repository
public interface BlackListedTokenRepo extends JpaRepository<BlackListedToken, Integer>{

	Optional<BlackListedToken> findByToken(String token);

}
