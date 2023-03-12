package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Admin extends User{
	
	@NotNull(message = "Admin Name Can't be null.")
	@NotBlank(message = "Admin Name Can't be Blank.")
	@NotEmpty (message = "Admin Name Can't be Empty.")
	@Size(min = 3, max = 20, message = "Admin Name length should be between 3 and 20 characters.")
	@Column(name = "Name")
	private String adminName;

	
	@NotNull(message = "Mobile Number cannot be null.")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Invalid Mobile Number.")
	@Column(name = "Mobile")
	private String adminMobile;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "admin")
	private List<Report> reports = new ArrayList<>();

}
