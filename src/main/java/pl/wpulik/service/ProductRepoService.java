package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Order;
import pl.wpulik.model.OrderProduct;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.repository.ProductRepository;
import pl.wpulik.repository.ShipmentRepository;

@Service
@Transactional
public class ProductRepoService {
	
	private ProductRepository productRepository;
	private ShipmentRepository shipmentRepository;
	
	@Autowired
	public ProductRepoService(ProductRepository productRepository, ShipmentRepository shipmentRepository) {
		this.productRepository = productRepository;
		this.shipmentRepository = shipmentRepository;
	}
	
	public ProductRepoService() {}
	
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
	
	public Page<Product> getAllProducts(Pageable pageable){
		return productRepository.findAllOrderByIdDesc(pageable);
	}
	
	public Page<Product> getAllActiveProducts(Pageable pageable){
		return productRepository.findAllActive(pageable);
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
	
	public Page<Product> findByNameFragmentPage(Pageable pageable, String startWith, String insideWith){
		return productRepository.findByNameFragmentPage(pageable,startWith, insideWith);
		
	}
	public void removeShipmentFromProduct(Long productId, Long shipmentId) {
		Product product = productRepository.findById(productId).get();
		Shipment shipment = shipmentRepository.findById(shipmentId).get();
		product.getShipments().removeIf(next -> next.getId() == (shipmentId));
		shipment.getProducts().removeIf(next -> next.getId() == (productId));
		shipmentRepository.save(shipment);
		productRepository.save(product);
	}
	
	public void changeProductStockQuantity(List<OrderProduct> orderProducts){
		for(OrderProduct op : orderProducts) {
			substractFromProductStock(op.getProductId(), op.getAddedQuantity());
		}
	}
	
	public void updateProductStockQuantity(List<OrderProduct> orderProducts, Long productId, Integer newQuantity) {
		Stream<OrderProduct> productStream = orderProducts.stream();
		Integer addedQuantity = productStream
						.filter(p -> p.getProductId()==productId)
						.mapToInt(p -> p.getAddedQuantity())
						.sum();
		Product product = productRepository.getOne(productId);
		product.setQuantity(product.getQuantity() + addedQuantity - newQuantity);
		product = productRepository.save(product);
	}
	
	public boolean isStockEnough(Long productId, Integer newQuantity, Integer addedToOrder) {
		Product product = productRepository.getOne(productId);
		return product.getQuantity() >= newQuantity - addedToOrder;
	}
	
	private Product substractFromProductStock(Long productId, Integer productQuantity) {
		Product product = productRepository.getOne(productId);
		product.setQuantity(product.getQuantity() - productQuantity);
		return productRepository.save(product);
	}
	

	
	

}
