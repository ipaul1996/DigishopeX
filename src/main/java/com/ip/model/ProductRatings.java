package com.ip.model;


import jakarta.persistence.Column;
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
	
	@Column(name = "customerID")
	@NotNull(message = "CustomerID can not be null")
	Integer customerId;
	
	@Column(name = "productID")
	@NotNull(message = "ProductID can not be null")
	Integer productId;
	
	@NotNull(message = "Ratings can not be null")
	Integer ratings;
	

}
