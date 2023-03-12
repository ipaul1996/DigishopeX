package com.ip.model;



import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
	
	@NotNull(message = "State Can't be null.")
	@NotBlank(message = "State Can't be Blank.")
	@NotEmpty (message = "State Can't be Empty.")
	@Column(name = "State")
	private String state;
	
	@NotNull(message = "City Can't be null.")
	@NotBlank(message = "City Can't be Blank.")
	@NotEmpty (message = "City Can't be Empty.")
	@Column(name = "City")
	private String city;
	
	@NotNull(message = "Pincode Can't be null.")
	@NotBlank(message = "Pincode Can't be Blank.")
	@NotEmpty (message = "Pincode Can't be Empty.")
	@Column(name = "Pincode")
	private String pincode;


}
