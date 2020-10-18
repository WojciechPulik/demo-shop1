package pl.wpulik.service;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Product;
import pl.wpulik.model.Order;
import pl.wpulik.model.OrderProduct;
import pl.wpulik.repository.OrderRepository;

@Service
@Transactional
public class OrderRepoService {
	
	private OrderRepository orderRepository;
	private OrderProductService orderProductService;
	private ProductRepoService productRepoService;
	private OrderProductService orderProductService;
	
	@Autowired
<<<<<<< HEAD
	public OrderRepoService(OrderRepository orderRepository, ProductRepoService productRepoService,
			OrderProductService orderProductService) {
=======
	public OrderRepoService(OrderRepository orderRepository, OrderProductService orderProductService,
			ProductRepoService productRepoService) {
>>>>>>> order-refactoring
		this.orderRepository = orderRepository;
		this.orderProductService = orderProductService;
		this.productRepoService = productRepoService;
		this.orderProductService = orderProductService;
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
	
	public Page<Order> getAllOrders(Pageable pageable){
		return orderRepository.findAllByIdDesc(pageable);
	}
	
	/* Use only when order is being created!*/ //TODO: nadmiarowe inserty do bazy (albo i nie)
	public void addProductsToOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
<<<<<<< HEAD
		// Adds also OrderProduct to DB
		List<OrderProduct> orderProducts = orderProductService.orderProductMapping(products);
		orderProductService.saveOrderProducts(orderProducts, order);
		order.setOrderProducts(orderProducts);
		
		for(Product prod: products) {
			prod.setOrders(productRepoService.getProductOrders(prod.getId()));
			for(int i = 0; i < prod.getAddedQuantity(); i++) {
				order.addProducts(prod);
			}
		}
=======
		List<OrderProduct> orderProducts = orderProductService.orderProductMapping(products);
		orderProductService.saveOrderProducts(orderProducts, order);	
>>>>>>> order-refactoring
		orderRepository.save(order);
	}
	
	 public Order orderWithProducts (Long orderId){
		Order order = getById(orderId);
		for(OrderProduct op : order.getOrderProducts()) 
			order.getProducts()
				.add(productRepoService.getById(op.getProductId()));
		return order;
	}
	
	

}
