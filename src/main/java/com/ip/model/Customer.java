package com.ip.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
	
	@Email
	@Column(unique = true, name = "Email")
	private String customerEmail;
	
	
	@NotNull(message = "Mobile Number cannot be null.")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Invalid Mobile Number.")
	@Column(name = "Mobile")
	private String customerMobile;
	
	@Embedded
	private Address address;

}
