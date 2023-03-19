package com.ip.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ProductID")
	private Integer productId;
	
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
	
	
	@Column(name = "CostPrice")
	@NotNull(message = "Cost price can't be null.")
	@DecimalMin(value = "1.0", message = "Minimum cost price should be 1.0")
	private Double costPrice;
	
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
	
	@Column(name = "StockQuantity")
	private Integer stockQuantity = 0;
	
	//Unidirectional
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	List<ProductRatings> productRatings = new ArrayList<>();
	
	
	//Bidirectional
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "CategoryID", referencedColumnName = "categoryId", nullable = true )
	Category category;
	
	//Bidirectional
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
	private List<Cart> cartList = new ArrayList<>();
	
	
	//Bidirectional
	@JsonIgnore
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "product")
	private OrderDetail detail;
	
	
	//Bidirectional
	@JsonIgnore
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
	private List<FeedBack> feedBack = new ArrayList<>();


	@Override
	public int hashCode() {
		return Objects.hash(avgRatings, costPrice, description, noOfPeopleRated, price, productId, productImage,
				productName, stockQuantity);
	}


	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		
		return Objects.equals(avgRatings, other.avgRatings) 
				&& Objects.equals(costPrice, other.costPrice)
				&& Objects.equals(description, other.description)
				&& Objects.equals(noOfPeopleRated, other.noOfPeopleRated) 
				&& Objects.equals(price, other.price)
				&& Objects.equals(productId, other.productId) 
				&& Objects.equals(productImage, other.productImage)
				&& Objects.equals(productName, other.productName)
				&& Objects.equals(stockQuantity, other.stockQuantity);
	}
	
	
	
	

}
