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
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Supplier {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer supplierID;
	

	@NotNull(message = "Company Name Can't be null.")
	@NotBlank(message = "Company Name Can't be Blank.")
	@NotEmpty (message = "Company Name Can't be Empty.")
	private String companyName;
	
	private String city;
	
	private String state;
	
	private String pincode;
	
	@Column(name = "mobile")
	@NotNull(message = "Mobile Number Can't be null.")
	@NotBlank(message = "Mobile Number Can't be Blank.")
	@NotEmpty (message = "Mobile Number Can't be Empty.")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Invalid Mobile Number.")
	private String mobileNumber;
	
	@Column(name = "categoryname")
	@NotNull(message = "Category name can't be null.")
	@NotBlank(message = "Category name can't be Blank.")
	@NotEmpty (message = "Category name can't be Empty.")
	private String categoryName;
	
	
	private Boolean active = true;
	
	//Bidirectional
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "supplier")
	private List<OrderDetail> details = new ArrayList<>();
	
	

}
