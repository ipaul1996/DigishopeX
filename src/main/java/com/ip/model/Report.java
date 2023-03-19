package com.ip.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer reportID;
	
	@JsonIgnore
	private LocalDateTime reportDateTime = LocalDateTime.now();
	
	@NotNull(message = "Report description Can't be null.")
	@NotBlank(message = "Report description Can't be null.")
	@NotEmpty (message = "Report description Can't be null.")
	private String description;
	
	@NotNull(message = "Admin Name Can't be null.")
	@NotBlank(message = "Admin Name Can't be null.")
	@NotEmpty (message = "Admin Name Can't be null.")
	private String adminName;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "adminID")
	private Admin admin;
	
	

}
