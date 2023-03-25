package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private Integer cartId;
	
    //Bidirectional
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	private Customer customer;
	
	//Bidirectional
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Product> products = new ArrayList<>();
	
	
	
}
