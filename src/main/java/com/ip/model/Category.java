package com.ip.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
	
	@JsonIgnore
	@OneToMany(cascade =  CascadeType.ALL, mappedBy = "category")
    List<Product> products = new ArrayList<>();

}
