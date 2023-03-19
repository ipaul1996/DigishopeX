package com.ip.service;

import java.util.List;
import java.util.Map;

import com.ip.dto.CategoryPriceDTO;
import com.ip.dto.CategoryRatingsDTO;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Product;

public interface ProductService {
	
	public Product createProduct(ProductDTO pdto) throws CategoryException, ProductException;
	
	public Product addExistingProduct(ProductDTOV2 pdto) throws ProductException;
	
	public Product updateProduct(ProductDTO pdto) throws ProductException, CategoryException;
	
	public Product deleteProduct(Integer productid) throws ProductException;
	
	public Product getProductByProductId(Integer productId) throws ProductException;
	
	public List<Product> getProductsByCategoryId(Integer categoryId) throws ProductException, CategoryException;

	public List<Product> getProductsByCategoryName(String categoryName) throws ProductException, CategoryException;
	
	public Map<Category, List<Product>> getAllProductsCategorywise() throws ProductException, CategoryException;
		
	public List<Product> sortProductsByNameAscendingForACategory(Integer categoryId) throws ProductException, CategoryException;
	
	public List<Product> sortProductsByNameDescendingForACategory(Integer categoryId) throws ProductException, CategoryException;
	
	public List<Product> sortProductsByPriceAscendingForACategory(Integer categoryId) throws ProductException, CategoryException;
	
	public List<Product> sortProductsByPriceDescendingForACategory(Integer categoryId) throws ProductException, CategoryException;

	public List<Product> sortProductsByRatingsAscendingForACategory(Integer categoryId) throws ProductException, CategoryException;
	
	public List<Product> sortProductsByRatingsDescendingForACategory(Integer categoryId) throws ProductException, CategoryException;

    public List<Product> filterProductsByRatingsForACategory(Integer categoryId, Integer minRatings, Integer maxRatings) throws ProductException, CategoryException;
	
	public List<Product> filterProductsByPriceForACategory(Integer categoryId, Double minPrice, Double maxPrice) throws ProductException, CategoryException;

	public Product rateAProduct(Integer productId, Integer ratings) throws ProductException;
	
	public Product editRatingsOfAProduct(Integer productId, Integer ratings) throws ProductException;
}
