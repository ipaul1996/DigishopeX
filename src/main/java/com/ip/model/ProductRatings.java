package com.ip.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames={"customerId", "productId"})
	})
public class ProductRatings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serialno")
	Integer serialNo;
	
	@Column(name = "customerid")
	@NotNull(message = "CustomerID can not be null")
	Integer customerId;
	
	@Column(name = "productid")
	@NotNull(message = "ProductID can not be null")
	Integer productId;
	
	@NotNull(message = "Ratings can not be null")
	Integer ratings;
	

}
