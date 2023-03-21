package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.FeedBack;

@Repository
public interface FeedBackRepo extends JpaRepository<FeedBack, Integer>{

}
