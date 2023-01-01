package com.ip.service;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ip.dto.CategoryPriceDTO;
import com.ip.dto.CategoryRatingsDTO;
import com.ip.dto.ProductDTO;
import com.ip.dto.ProductDTOV2;
import com.ip.enums.UserType;
import com.ip.exception.CategoryException;
import com.ip.exception.CredentialException;
import com.ip.exception.ProductException;
import com.ip.model.Category;
import com.ip.model.Product;
import com.ip.model.ProductRatings;
import com.ip.model.UserSession;
import com.ip.repository.CategoryRepo;
import com.ip.repository.ProductRatingsRepo;
import com.ip.repository.ProductRepo;
import com.ip.repository.SessionRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo pRepo;
	
	@Autowired
	private CategoryRepo cRepo;
	
	@Autowired
	private SessionRepo sRepo;
	
	@Autowired
	private ProductRatingsRepo prRepo;

	
	@Override
	public Product createProduct(ProductDTO pdto, String token) throws CredentialException, CategoryException {
		
        UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		Product p = new Product();
		p.setProductName(pdto.getProductName());
		p.setProductImage(pdto.getProductImage());
		p.setDescription(pdto.getDescription());
		p.setPrice(pdto.getPrice());
		p.setStockQuantity(pdto.getQuanity());
		
		Category category = cRepo.findByCategoryName(pdto.getCategoryName());
		
		if(category == null) {
			throw new CategoryException("Please give a valid category name");
		}
		
		p.setCategory(category);
		
		category.getProducts().add(p);
		
		return pRepo.save(p);
		
	}
	
	
	@Override
	public Product addExistingProduct(ProductDTOV2 pdto, String token) throws CredentialException, ProductException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		Optional<Product> op = pRepo.findById(pdto.getProductId());
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		op.get().setStockQuantity(op.get().getStockQuantity() + pdto.getQuantity());         
		
		return pRepo.save(op.get());
		
	}

	
	@Override
	public Product updateProduct(ProductDTO pdto, String token) throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		Optional<Product> op =  pRepo.findById(pdto.getProductId());
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		Product p = op.get();
		
		p.setProductName(pdto.getProductName());
		p.setProductImage(pdto.getProductImage());
		p.setDescription(pdto.getDescription());
		p.setPrice(pdto.getPrice());
		
		Category category = cRepo.findByCategoryName(pdto.getCategoryName());
		
		if(category == null) {
			throw new CategoryException("Please give a valid category name");
		}
		
		p.setCategory(category);
		
		category.getProducts().add(p);
		
		return pRepo.save(p);
	}

	
	@Override
	public Product deleteProduct(UUID productId, String token) throws CredentialException, ProductException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.CUSTOMER) {
			throw new CredentialException("Please login as an admin");
		}
		
		Optional<Product> op =  pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		pRepo.delete(op.get());
		
		return op.get();
	}

	
	@Override
	public Product getProductByProductId(UUID productId, String token)
			throws CredentialException, ProductException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Product> op = pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		return op.get();
	}

	
	@Override
	public List<Product> getProductsByCategoryId(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
			
		return products;
	}

	
	@Override
	public List<Product> getProductsByCategoryName(String categoryName, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
	    Category category = cRepo.findByCategoryName(categoryName);
		
		if(category == null) {
			throw new CategoryException("Invalid category name");
		}
		
		List<Product> products =  category.getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + categoryName);
		}
			
		return products;
	}

	
	@Override
	public Map<Category, List<Product>> getAllProductsCategorywise(String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		List<Category> allCategory = cRepo.findAll();
		
		if(allCategory.isEmpty()) {
			throw new CategoryException("No category found");
		}
		
		Map<Category, List<Product>> map = new HashMap<>();
		
		allCategory.forEach(c -> {
			 map.put(c, c.getProducts());
		});
		
		return map;
	}

	
	@Override
	public List<Product> sortProductsByNameAscendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getProductName().compareTo(p2.getProductName());
		});
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByNameDescendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
        UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p2.getProductName().compareTo(p1.getProductName());
		});
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByPriceAscendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getPrice() > p2.getPrice() ? +1 : p1.getPrice() < p2.getPrice() ? -1 : 0;
		});
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByPriceDescendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
	
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getPrice() < p2.getPrice() ? +1 : p1.getPrice() > p2.getPrice() ? -1 : 0;
		});
		
		return products;
	}

	
	@Override
	public List<Product> sortProductsByRatingsAscendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getAvgRatings() > p2.getAvgRatings() ? +1 : p1.getAvgRatings() < p2.getAvgRatings() ? -1 : 0;
		});
		
		return products;
		
	}

	
	@Override
	public List<Product> sortProductsByRatingsDescendingForACategory(Integer categoryId, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op = cRepo.findById(categoryId);
		
		if(op.isEmpty()) {
			throw new CategoryException("Invalid category id");
		}
		
		List<Product> products =  op.get().getProducts();
		
		if(products.isEmpty()) {		
			throw new ProductException("No product found in the category " + op.get().getCategoryName());
		}
		
		Collections.sort(products, (p1, p2) -> {
			return p1.getAvgRatings() < p2.getAvgRatings() ? +1 : p1.getAvgRatings() > p2.getAvgRatings() ? -1 : 0;
		});
		
		return products;
	}

	
	@Override
	public List<Product> filterProductsByRatingsForACategory(CategoryRatingsDTO dto, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op =  cRepo.findById(dto.getCategoryId());
		
		if(op.isEmpty()) {
			throw new CategoryException("No category found with the is " + dto.getCategoryId());
		}
		
		Double maxRatings = dto.getMaxRatings().doubleValue();
		Double minRatings = dto.getMinRatings().doubleValue();
		
		if(maxRatings <= minRatings || minRatings < 0) {
			throw new ProductException("Invalid ratings range");
		}
		
		List<Product> products =  op.get().getProducts().stream()
																							 .filter(p -> p.getAvgRatings() >= minRatings && p.getAvgRatings() <= maxRatings)
																							 .collect(Collectors.toList());
		
		return products;
	}

	
	@Override
	public List<Product> filterProductsByPriceForACategory(CategoryPriceDTO dto, String token)
			throws CredentialException, ProductException, CategoryException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null) {
			throw new CredentialException("Please login to get product details");
		}
		
		Optional<Category> op =  cRepo.findById(dto.getCategoryId());
		
		if(op.isEmpty()) {
			throw new CategoryException("No category found with the is " + dto.getCategoryId());
		}
		
		Double maxPrice = dto.getMaxPrice();
		Double minPrice = dto.getMinPrice();
		
		if(maxPrice <= minPrice || minPrice < 1) {
			throw new ProductException("Invalid price range");
		}
		
		List<Product> products =  op.get().getProducts().stream()
																							.filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
																							.collect(Collectors.toList());
		
		return products;
		
	}

	
	@Override
	public Product rateAProduct(UUID productId, Integer ratings, String token)
			throws CredentialException, ProductException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.ADMIN) {
			throw new CredentialException("Please login as a customer");
		}
		
		Integer customerId = userSession.getUserid();
		
		Optional<Product> op =  pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(ratings < 0 || ratings > 5) {
			 throw new ProductException("Ratings should be an integeral value between 0 to 5");
		}
		
		ProductRatings productRatings =  prRepo.findByCustomerIdAndProductId(customerId, productId);
		
		if(productRatings != null) {
			throw new ProductException("You have already rated for the selected product. If you wish you can edit.");
		}
		
		ProductRatings pr = new ProductRatings();
		pr.setCustomerId(customerId);
		pr.setProductId(productId);
		pr.setRatings(ratings);
		
		Integer noOfPeople = op.get().getNoOfPeopleRated();
		Double avgRatings =  op.get().getAvgRatings();
		
		DecimalFormat df = new DecimalFormat("#.#");
		Double newAvgRatings = Double.valueOf(df.format(((avgRatings * noOfPeople) + ratings)/(noOfPeople + 1)));
		
		op.get().setAvgRatings(newAvgRatings);
		op.get().setNoOfPeopleRated(noOfPeople + 1);
		op.get().getProductRatings().add(pr);
		
		return pRepo.save(op.get());
		
	}


	@Override
	public Product editRatingsOfAProduct(UUID productId, Integer ratings, String token)
			throws CredentialException, ProductException {
		
		UserSession userSession = sRepo.findByToken(token);
		
		if(userSession == null || userSession.getUserType() == UserType.ADMIN) {
			throw new CredentialException("Please login as a customer");
		}
		
		Integer customerId = userSession.getUserid();
		
		Optional<Product> op =  pRepo.findById(productId);
		
		if(op.isEmpty()) {
			throw new ProductException("Invalid product id");
		}
		
		if(ratings < 0 || ratings > 5) {
			 throw new ProductException("Ratings should be an integeral value between 0 to 5");
		}
		
		ProductRatings productRatings =  prRepo.findByCustomerIdAndProductId(customerId, productId);
		
		if(productRatings == null) {
			throw new ProductException("You haven't rated the product yet");
		}
		
		op.get().getProductRatings().remove(productRatings);
		
		Integer oldRatings = productRatings.getRatings();		
		productRatings.setRatings(ratings);
		
		Integer noOfPeople = op.get().getNoOfPeopleRated();
		Double avgRatings =  op.get().getAvgRatings();
		
		DecimalFormat df = new DecimalFormat("#.#");
		Double newAvgRatings = Double.valueOf(df.format(((avgRatings * noOfPeople) - oldRatings + ratings)/noOfPeople));
		
		op.get().setAvgRatings(newAvgRatings);
		op.get().setNoOfPeopleRated(noOfPeople + 1);
		op.get().getProductRatings().add(productRatings);
		
		return pRepo.save(op.get());
	}

}
