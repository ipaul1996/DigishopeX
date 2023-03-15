package com.ip.dto;

import java.time.LocalDateTime;

import com.ip.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ReturnRequestDTO {
	
	private Integer orderID;
	
	private LocalDateTime requestedON;
	
	private LocalDateTime fulfillON;
	
	private LocalDateTime refundON;
	
	private OrderStatus status;
	
	

}
