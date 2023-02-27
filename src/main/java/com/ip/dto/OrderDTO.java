package com.ip.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ip.model.OrderDetail;

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
public class OrderDTO {
	
	private Integer orderID;
	
	private Integer customerID;
	
	private List<OrderDetail> details;
	
	private Double total_order_amount;
	
	private LocalDateTime orderDateTime;
	
	

}
