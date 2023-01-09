package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Integer cartId;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	private Customer customer;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Product> products = new ArrayList<>();
	
	
	
}
