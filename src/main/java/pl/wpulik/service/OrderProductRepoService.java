package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Order;
import pl.wpulik.model.OrderProduct;
import pl.wpulik.repository.OrderProductRepository;
import pl.wpulik.repository.OrderRepository;

@Service
@Transactional
public class OrderProductRepoService {
	
	private OrderProductRepository orderProductRepository;
	private OrderRepository orderRepository;
	
	
	@Autowired
	public OrderProductRepoService(OrderProductRepository orderProductRepository, OrderRepository orderRepository) {
		this.orderProductRepository = orderProductRepository;
		this.orderRepository = orderRepository;
	}
	
	public OrderProduct getById(Long orderProductId) {
		return orderProductRepository.findById(orderProductId).get();
	}
	public OrderProduct save(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}
	
	public Order removeAllProductsFromOrder(Long orderId) {
		Order order = orderRepository.getOne(orderId);
		order.getOrderProducts().clear();	
		return orderRepository.save(order);
	}
	
	
	

}
