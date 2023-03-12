package com.ip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip.enums.PaymentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer paymentID;
	
	@NotNull(message = "Payment Type should not be null")
	@NotBlank(message = "Payment Type should not be blank")
	@NotEmpty(message = "Payment Type should not be empty")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "payment")
	private Orders order;

}
