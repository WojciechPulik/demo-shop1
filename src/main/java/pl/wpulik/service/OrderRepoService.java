package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Product;
import pl.wpulik.model.Order;
import pl.wpulik.repository.OrderRepository;

@Service
@Transactional
public class OrderRepoService {
	
	private OrderRepository orderRepository;
	private ProductRepoService productRepoService;
	
	@Autowired
	public OrderRepoService(OrderRepository orderRepository, ProductRepoService productRepoService) {
		this.orderRepository = orderRepository;
		this.productRepoService = productRepoService;
	}
	
	public Order getById(Long id) {
		Order order = orderRepository.findById(id).get();
		return order;
	}
	
	public Order addOrder(Order order) {
		order.setDatePurchase(LocalDateTime.now());
		Order addedOrder = orderRepository.save(order);
		return addedOrder;
	}
	
	public Order updateOrder(Order order) {
		Order updatedOrder = orderRepository.save(order);
		return updatedOrder;
	}
	
	public List<Order> getAllOrders(){
		List<Order> resultList = new ArrayList<>();
		resultList = orderRepository.findAll();
		return resultList;
	}
	/* Use only when order is being created!*/ //TODO: nadmiarowe inserty do bazy (albo i nie)
	public void addProductsToOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
		for(Product prod: products) {
			prod.setOrders(productRepoService.getProductOrders(prod.getId()));
			for(int i = 0; i < prod.getAddedQuantity(); i++) {
				order.addProducts(prod);
			}
		}
		orderRepository.save(order);
	}
	
	public Order updateProductsInOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
		for(Product p : products) {
			p.setOrders(productRepoService.getProductOrders(p.getId()));
			order.addProducts(p);
		}		
		return orderRepository.save(order);
	}
	
	public List<Product> getProductsFromOrder (Long orderId){
		List<Product> resultList = new ArrayList<>();
		Order order = orderRepository.findById(orderId).get();
		resultList = order.getProducts();
		return resultList;
	}
	
	public Order removeAllProducts(Long orderId) {
		Order order = orderRepository.findById(orderId).get();
		Set<Product> products = new HashSet<>();
		order.getProducts().forEach(products::add);		
		for(Product p : products) {
			order.removeProducts(p);
		}
		return orderRepository.save(order);
	}

}
