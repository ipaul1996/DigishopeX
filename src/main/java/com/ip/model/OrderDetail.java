package com.ip.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

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
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderDetailID;
	
	@NotNull(message = "quantity should not be null")
	private Integer quantity;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderID")
	private Orders order;
	
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productID")
	private Product product;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "supplierID")
	private Supplier supplier;

}
