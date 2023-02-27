package com.ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.TrackOrder;

@Repository
public interface TrackOrderRepo extends JpaRepository<TrackOrder, Integer> {

}
