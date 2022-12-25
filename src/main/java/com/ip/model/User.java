package com.ip.model;

import com.ip.enums.UserType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
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
@MappedSuperclass
public abstract class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userid;
	
	@NotNull(message = "User type Can't be null.")
	@Enumerated(EnumType.STRING)
	private UserType userType;
	

}
