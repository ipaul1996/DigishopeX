package com.ip.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
	
	public Product createProduct(ProductDTO pdto, String token) throws CredentialException, CategoryException;
	
	public Product addExistingProduct(ProductDTOV2 pdto, String token) throws CredentialException, ProductException;
	
	public Product updateProduct(ProductDTO pdto, String token) throws CredentialException, ProductException, CategoryException;
	
	public Product deleteProduct(UUID productid, String token) throws CredentialException, ProductException;
	
	public Product getProductByProductId(UUID productId, String token) throws CredentialException, ProductException;
	
	public List<Product> getProductsByCategoryId(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;

	public List<Product> getProductsByCategoryName(String categoryName, String token) throws CredentialException, ProductException, CategoryException;
	
	public Map<Category, List<Product>> getAllProductsCategorywise(String token) throws CredentialException, ProductException, CategoryException;
		
	public List<Product> sortProductsByNameAscendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;
	
	public List<Product> sortProductsByNameDescendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;
	
	public List<Product> sortProductsByPriceAscendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;
	
	public List<Product> sortProductsByPriceDescendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;

	public List<Product> sortProductsByRatingsAscendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;
	
	public List<Product> sortProductsByRatingsDescendingForACategory(Integer categoryId, String token) throws CredentialException, ProductException, CategoryException;

    public List<Product> filterProductsByRatingsForACategory(CategoryRatingsDTO dto, String token) throws CredentialException, ProductException, CategoryException;
	
	public List<Product> filterProductsByPriceForACategory(CategoryPriceDTO dto, String token) throws CredentialException, ProductException, CategoryException;

	public Product rateAProduct(UUID productId, Integer ratings, String token) throws CredentialException, ProductException;
	
	public Product editRatingsOfAProduct(UUID productId, Integer ratings, String token) throws CredentialException, ProductException;
}
