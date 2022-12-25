package com.ip.model;

import java.time.LocalDateTime;

import com.ip.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
public class User_Session {
	
	@Email
	@Column(unique = true)
	@Id
	private int email;	
	private String key;
	private LocalDateTime logindatetime;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	

}
