package com.ip.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class CancelledOrders {
	
	@Id
	private Integer orderID;
	
	private LocalDateTime cancelledON;
		
	private LocalDateTime refundON;
	
	

}
