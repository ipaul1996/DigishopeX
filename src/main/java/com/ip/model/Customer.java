package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class Customer extends User{
	
	@NotNull(message = "Customer Name Can't be null.")
	@NotBlank(message = "Customer Name Can't be Blank.")
	@NotEmpty (message = "Customer Name Can't be Empty.")
	@Size(min = 3, max = 20, message = "Customer Name length should be between 3 and 20 characters.")
	@Column(name = "Name")
	private String customerName;
	

	
	@NotNull(message = "Mobile Number cannot be null.")
	@Pattern(regexp = "[6789]{1}[0-9]{9}", message = "Invalid Mobile Number.")
	@Column(name = "Mobile")
	private String customerMobile;
	
	@Embedded
	private Address address;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
	private Cart cart;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
	private List<FeedBack> feedBack = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
	private List<Orders> orders = new ArrayList<>();

}
