package com.ip.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
