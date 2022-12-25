package com.ip.dto;

import com.ip.enums.UserType;

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

public class LoginDTO {
	
	private String email;
	private String password;
	private UserType userType;

}
