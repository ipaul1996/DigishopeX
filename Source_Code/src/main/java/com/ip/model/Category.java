package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer categoryId;
	
	@Column(name = "CategoryName", unique = true)
	@NotNull(message = "Category Name Can't be null.")
	@NotBlank(message = "Category Name Can't be Blank.")
	@NotEmpty (message = "Category Name Can't be Empty.")
	private String categoryName;
	
	@Column(name = "ImageURL")
	@NotNull(message = "Image URL Can't be null.")
	@NotBlank(message = "Image URL Can't be Blank.")
	@NotEmpty (message = "Image URL Can't be Empty.")
	private String imageUrl;
	
	@Column(name = "CategoryDescription")
	@NotNull(message = "Description Can't be null.")
	@NotBlank(message = "Description Can't be Blank.")
	@NotEmpty (message = "Description Can't be Empty.")
	private String description;
	
	//Bidirectional
	@JsonIgnore
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "category")
    List<Product> products = new ArrayList<>();

}
