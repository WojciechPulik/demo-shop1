package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Product;
import pl.wpulik.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public Product addProduct(Product product) {
		productRepository.save(product);
		return product;
	}
	
	
	
	

}
