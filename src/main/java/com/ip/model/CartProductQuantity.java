package com.ip.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
