package com.ip.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class BlackListedToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "serialno")
	private Integer serialNo;
	
	@Column(unique = true, length = 1000)
	private String token;

}
