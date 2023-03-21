package com.ip.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.model.DaywiseSalesData;



@Repository
public interface SalesDataRepo extends JpaRepository<DaywiseSalesData, Integer> {

	Optional<DaywiseSalesData> findByDate(LocalDate now);

}
