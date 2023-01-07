package com.ip.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Cart {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Integer cartId;
	
	private UUID productId;
	
	@NotNull(message = "Customer id can not be null")
	private Integer customerId;
	
	private LocalDate createdDate = LocalDate.now();
	
	@NotNull(message = "Customer id can not be null")
	private Integer quantity;
	
	
	
}
