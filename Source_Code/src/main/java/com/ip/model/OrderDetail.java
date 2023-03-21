package com.ip.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints = {
		 @UniqueConstraint(columnNames={"orderid", "productid"})
})
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderDetailID;
	
	@NotNull(message = "quantity should not be null")
	private Integer quantity;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderID")
	private Orders order;
	
	//Bidirectional
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "productID")
	private Product product;
	
	
	//Bidirectional
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "supplierID")
	private Supplier supplier;

}
