package com.ip.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip.enums.PaymentType;

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
