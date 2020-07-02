package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.repository.ProductRepository;

@Service
@Transactional
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
	
	public Product getById(Long id) {
		Product product = productRepository.findById(id).get();
		return product;
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public void addOrderToProduct(Long id, List<Order> orders) {
		Product product = productRepository.getOne(id);
		product.setOrders(orders);	
	}
	
	
	
	

}
