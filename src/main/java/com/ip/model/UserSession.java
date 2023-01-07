package com.ip.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

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
@Entity
public class UserSession {
	
	
	@Id
	private Integer userid;
	
	private String token;
	private LocalDateTime logindatetime;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	

}
