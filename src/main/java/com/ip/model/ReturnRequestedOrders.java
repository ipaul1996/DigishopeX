package com.ip.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class ReturnRequestedOrders {
	
	@Id
	private Integer orderID;
	
	private LocalDateTime requestedON;
	
	
	private LocalDateTime fulfillON;
	
	
	private LocalDateTime refundON;
	
	
	private Boolean stockUpdated = false;
	
	
	//Bidirectional
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<ReturnedOrderDetails> details = new ArrayList<>();
	
	
	
	
	

}
