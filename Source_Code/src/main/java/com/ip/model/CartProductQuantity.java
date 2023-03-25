package com.ip.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CartProductQuantity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serialNO")
	private Integer serialNo;
	
	
	@Column(name = "cartID")
	@NotNull(message = "CartID should not be null")
	private Integer cartId;
	
	@Column(name = "productID")
	@NotNull(message = "ProductID should not be null")
	private Integer productId;
	
	private LocalDate createdDate = LocalDate.now();
	
	@NotNull(message = "ProductQuantity should not be null")
	@Min(value = 1, message = "Minimum product quantity should be 1")
	private Integer productQuantity;
	
	

}
