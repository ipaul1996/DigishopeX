package com.ip.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
	@Column(name = "NAME")
	private String customername;
	
	@Email
	@Column(unique = true, name = "EMAIL")
	private String customeremail;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
			message = "Password must contain 8 characters and should have atleast 1 Upper Case, 1 Small Case, 1 Number and 1 Special Character")
	@Column(name = "PASSWORD")
	private String customerpassword;
	
	@NotNull(message = "Mobile Number cannot be null.")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Invalid Mobile Number.")
	@Column(name = "MOBILE")
	private String customermobile;
	
	@Embedded
	private Address address;

}
