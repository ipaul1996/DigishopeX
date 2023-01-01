package com.ip.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProductRatings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer serialNo;
	
	Integer customerId;
	
	UUID productId;
	
	@NotNull(message = "Ratings can not be null")
	Integer ratings;
	

}
