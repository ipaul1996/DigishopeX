package com.ip.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private Integer serialNo;
	
	private Integer cartId;
	
	private Integer productId;
	
	private LocalDate createdDate = LocalDate.now();
	
	private Integer productQuantity;
	
	

}
