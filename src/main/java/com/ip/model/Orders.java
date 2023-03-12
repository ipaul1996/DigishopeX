package com.ip.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderID;
	
	@JsonIgnore
	private LocalDate orderDate = LocalDate.now();
	
	private LocalDate shipDate;
	
	private LocalDate deliveryDate;
	
	@NotNull(message = "total_order_amount should not be null")
	private Double total_order_amount;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderDetail> details = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "shipperID")
	private Shipper shipper;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "paymentID")
	private Payment payment;
	
	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "customerID")
	private Customer customer;

}
