package com.ip.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class ReturnedOrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serialNO;
	
	
	private Integer productID;
	
	
	private Integer quantity;
	
	//Bidirectional
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "orderid")
	private ReturnRequestedOrders order;
	
	

}
