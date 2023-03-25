package com.ip.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderID;
	
	@JsonIgnore
	private LocalDateTime orderDateTime = LocalDateTime.now();
	
	private LocalDateTime shipDateTime;
	
	private LocalDateTime deliveryDateTime;
	
	@NotNull(message = "total_order_amount should not be null")
	private Double total_order_amount;
	
	private Boolean returnRequested = false;
	
	private Boolean cancelled = false;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	//Bidirectional
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderDetail> details = new ArrayList<>();
	
	//Bidirectional
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "shipperID")
	private Shipper shipper;
	
	//Bidirectional
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "paymentID")
	private Payment payment;
	
	
	//Bidirectional
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "customerID")
	private Customer customer;

}
