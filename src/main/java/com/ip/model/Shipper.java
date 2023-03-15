package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Shipper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer shipperID;
	
	
	@NotNull(message = "Company Name Can't be null.")
	@NotBlank(message = "Company Name Can't be Blank.")
	@NotEmpty (message = "Company Name Can't be Empty.")
	private String companyName;
	
	@Column(name = "mobile")
	@NotNull(message = "Mobile Number Can't be null.")
	@NotBlank(message = "Mobile Number Can't be Blank.")
	@NotEmpty (message = "Mobile Number Can't be Empty.")
	private String mobileNumber;
	
	
	private Boolean active = true;
	
	//Bidirectional
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "shipper")
	private List<Orders> orders = new ArrayList<>();

}
