package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

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
	private ProductRepoService productRepoService;
	private OrderProductService orderProductService;
	private OrderProductRepoService orderProductRepoService;
	
	@Autowired
	public OrderRepoService(OrderRepository orderRepository, ProductRepoService productRepoService,
			OrderProductService orderProductService, OrderProductRepoService orderProductRepoService) {
		this.orderRepository = orderRepository;
		this.productRepoService = productRepoService;
		this.orderProductService = orderProductService;
		this.orderProductRepoService = orderProductRepoService;
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
	/*After refactoring*/
	public void addProductsToOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
		List<OrderProduct> orderProducts = orderProductService.orderProductMapping(products);
		orderProductService.saveOrderProducts(orderProducts, order);
		
		orderRepository.save(order);
	}
	
	/*new method for OrderProduct*/ //to remove?
	public Order updateOrderProductsInOrder(Long orderId, List<OrderProduct> products) {
		Order order = orderRepository.getOne(orderId);
		for(OrderProduct op : products) {
			orderProductRepoService.save(op);
			order.getOrderProducts().add(op);
		}
		return orderRepository.save(order);
	}

	//TODO: remove after refactoring
	public List<Product> getProductsFromOrder (Long orderId){
		List<Product> resultList = new ArrayList<>();
		Order order = orderRepository.findById(orderId).get();
		resultList = order.getProducts();
		return resultList;
	}
	//TODO: remove after refactoring
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
