package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Product;
import pl.wpulik.model.Order;
import pl.wpulik.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	private OrderRepository orderRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public Order getById(Long id) {
		Order order = orderRepository.findById(id).get();
		return order;
	}
	
	public Order addOrder(Order order) {
		Date date = new Date();
		order.setDatePurchase(date);
		Order addedOrder = orderRepository.save(order);
		return addedOrder;
	}
	
	public void addProductsToOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
		for(Product prod: products) {
			for(int i = 0; i < prod.getAddedQuantity(); i++) {
				order.addProducts(prod);
			}
		}
		orderRepository.save(order);
	}
	
	public List<Product> getProductsFromOrder (Long orderId){
		List<Product> resultList = new ArrayList<>();
		Order order = orderRepository.findById(orderId).get();
		resultList = order.getProducts();
		return resultList;
	}

}
