package com.ip.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "ProductID")
	private UUID productId;
	
	@Column(name = "ProductImage")
	@NotNull(message = "Image URL Can't be null.")
	@NotBlank(message = "Image URL Can't be Blank.")
	@NotEmpty (message = "Image URL Can't be Empty.")
	private String productImage;
	
	@Column(name = "ProductName")
	@NotNull(message = "Product Name Can't be null.")
	@NotBlank(message = "Product Name Can't be Blank.")
	@NotEmpty (message = "Product Name Can't be Empty.")
	private String productName;
	
	@Column(name = "ProductDescription")
	@NotNull(message = "Description Can't be null.")
	@NotBlank(message = "Description Can't be Blank.")
	@NotEmpty (message = "Description Can't be Empty.")
	private String description;
	
	@Column(name = "ProductPrice")
	@NotNull(message = "Price Can't be null.")
	@DecimalMin(value = "1.0", message = "Minimum price should be 1.0")
	private Double price;
	
	@Column(name = "AverageProductRatings")
	@Min(value = 0, message = "Minimum ratings should be 0.0")
	@Max(value = 5, message = "Maximum ratings should be 5.0")
	private Double avgRatings = 0.0;
	
	@Column(name = "NoOfPeopleRated")
	private Integer noOfPeopleRated = 0;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	List<ProductRatings> productRatings = new ArrayList<>();
	
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "CategoryId" )
	Category category;
	
	@Column(name = "StockQuantity")
	private Integer stockQuantity = 0;
	

}
