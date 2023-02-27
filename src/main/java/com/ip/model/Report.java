package com.ip.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
