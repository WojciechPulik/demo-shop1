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
	
	@Autowired
	public OrderRepoService(OrderRepository orderRepository, OrderProductService orderProductService,
			ProductRepoService productRepoService) {
		this.orderRepository = orderRepository;
		this.orderProductService = orderProductService;
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
	
	public Page<Order> getAllOrders(Pageable pageable){
		return orderRepository.findAllByIdDesc(pageable);
	}
	
	/* Use only when order is being created!*/ //TODO: nadmiarowe inserty do bazy (albo i nie)
	public void addProductsToOrder(Long orderId, List<Product> products) {
		Order order = orderRepository.findById(orderId).get();
		List<OrderProduct> orderProducts = orderProductService.orderProductMapping(products);
		orderProductService.saveOrderProducts(orderProducts, order);
		productRepoService.changeProductStockQuantity(orderProducts);
		orderRepository.save(order);
	}
	
	 public Order orderWithProducts (Long orderId){
		Order order = getById(orderId);
		for(OrderProduct op : order.getOrderProducts()) 
			order.getProducts()
				.add(productRepoService.getById(op.getProductId()));
		return order;
	}
	 
	 public Page<Order> findAllUserOrders(Pageable pageable, Long userId){
		 return orderRepository.findAllByUserId(pageable, userId);
	 }
	 
	
	

}
