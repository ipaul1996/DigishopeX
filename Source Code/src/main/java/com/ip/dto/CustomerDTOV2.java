package com.ip.dto;

import com.ip.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTOV2 {
	
	private String password;

	private String customerName;
	
	private String customerMobile;
	
	private Address address;

}
