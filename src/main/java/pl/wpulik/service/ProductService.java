package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Order;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
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
		List<Shipment> productShipments = new ArrayList<>();
		product.getShipments().forEach(productShipments::add);
		return product;
	}
	
	public Product updateProduct(Product product) {
		productRepository.save(product);
		return product;
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Picture> getPictures(Long productId){
		Product product = productRepository.findById(productId).get();
		List<Picture> pictures = product.getPictures();
		return pictures;
	}
	
	public void addOrderToProduct(Long id, List<Order> orders) {
		Product product = productRepository.findById(id).get();
		product.setOrders(orders);	
	}
	
	
	
	

}
