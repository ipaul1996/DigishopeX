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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(uniqueConstraints={
	 @UniqueConstraint(columnNames={"customerid", "productid"})
})
public class FeedBack {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer feedBackID;
	
	@NotNull(message = "Comment Can't be null.")
	@NotBlank(message = "Comment Can't be Blank.")
	@NotEmpty (message = "Comment Can't be Empty.")
	private String comment;
	
	@JsonIgnore
	private LocalDateTime feedBack_Date_Time = LocalDateTime.now();
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "customerid")
	private Customer customer;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "productid")
	private Product product;

}
