package com.ip.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ip.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class TrackOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serialNO;
	
	
	@NotNull(message = "Order Status Can't be null.")
	@NotBlank(message = "Order Status Can't be Blank.")
	@NotEmpty (message = "Order Status Can't be Empty.")
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "orderid")
	private Orders order;

}
