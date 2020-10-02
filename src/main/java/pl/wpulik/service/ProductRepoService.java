package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Order;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.repository.CategoryRepository;
import pl.wpulik.repository.ProducerRepository;
import pl.wpulik.repository.ProductRepository;
import pl.wpulik.repository.ShipmentRepository;

@Service
@Transactional
public class ProductRepoService {
	
	private ProductRepository productRepository;
	private ProducerRepository producerRepository;
	private CategoryRepository categoryRepository;
	private ShipmentRepository shipmentRepository;
	
	@Autowired
	public ProductRepoService(ProductRepository productRepository, ProducerRepository producerRepository, CategoryRepository categoryRepository,
			ShipmentRepository shipmentRepository) {
		this.productRepository = productRepository;
		this.producerRepository = producerRepository;
		this.categoryRepository = categoryRepository;
		this.shipmentRepository = shipmentRepository;
	}
	
	public ProductRepoService() {}
	
	public Product addProduct(Product product, Long producerId, Long categoryId, Long shipmentId ) {
		product.setProducer(producerRepository.findById(producerId).get());
		product.getCategories().add(categoryRepository.findById(categoryId).get());
		product.getShipments().add(shipmentRepository.findById(shipmentId).get());
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
	
	public Page<Product> getAllProducts(Pageable pageable){
		return productRepository.findAll(pageable);
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
	
	public List<Product> findByNameFragment(String startWith, String insideWith){
		return productRepository.findByNameFragment(startWith, insideWith);
		
	}
	public void removeShipmentFromProduct(Long productId, Long shipmentId) {
		Product product = productRepository.getOne(productId);
		Shipment shipment = shipmentRepository.getOne(shipmentId);
		product.getShipments().removeIf(next -> next.getId() == (shipmentId));
		shipment.getProducts().removeIf(next -> next.getId() == (productId));
		shipmentRepository.save(shipment);
		productRepository.save(product);
	}
	

	
	

}
