package com.ip.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "shipper")
	private Orders order;

}
