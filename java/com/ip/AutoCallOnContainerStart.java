package com.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ip.service.OrderService;

import jakarta.annotation.PostConstruct;

@Component
public class AutoCallOnContainerStart {
	
	@Autowired
	private OrderService oService;
	
	@PostConstruct
	public void onStartCall() {
		oService.updateStock();
		oService.updateOrderStatus();
	}

}
