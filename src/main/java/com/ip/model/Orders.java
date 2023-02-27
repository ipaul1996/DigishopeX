package com.ip.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private LocalDateTime orderDateTime = LocalDateTime.now();
	
	private LocalDateTime shipDateTime;
	
	private LocalDateTime deliveryDateTime;
	
	@NotNull(message = "total_order_amount should not be null")
	private Double total_order_amount;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderDetail> details = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "shipperID")
	private Shipper shipper;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "paymentID")
	private Payment payment;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "order")
	private TrackOrder trackOrder;

}
