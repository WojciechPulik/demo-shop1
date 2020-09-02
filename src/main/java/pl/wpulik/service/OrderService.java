package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.OrderStatusDTO;
import pl.wpulik.dto.StatusDTO;
import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;

@Service
public class OrderService {
	
	private OrderRepoService orderRepoService;
	private ProductRepoService productRepoService;
	
	public OrderService () {}
	
	@Autowired
	public OrderService(OrderRepoService orderRepoService, ProductRepoService productRepoService) {
		this.orderRepoService = orderRepoService;
		this.productRepoService = productRepoService;
	}
	public OrderDTO orderDtoMapping(Order order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setId(order.getId());
		orderDto.setProducts(order.getProducts());
		orderDto.setDatePurchase(order.getDatePurchase());
		orderDto.setDateRecived(order.getDateRecived());
		orderDto.setDateSent(order.getDateSent());
		orderDto.setOrderDetails(order.getOrderDetails());
		orderDto.setUser(order.getUser());
		orderDto.setShipment(order.getShipment());
		orderDto.setRecieved(order.isRecieved());
		orderDto.setCashOnDelivery(order.isCashOnDelivery());
		orderDto.setPayed(order.isPayed());
		orderDto.setSent(order.isSent());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setStatus(status(order.getId()));		
		return orderDto;
	}
	public OrderStatusDTO createStatus() {
		List<StatusDTO> statuses = new ArrayList<>();
		statuses.add(new StatusDTO(1L, "new", "Złożone"));
		statuses.add(new StatusDTO(2L, "recived", "Odebrane"));
		statuses.add(new StatusDTO(3L, "sent", "Wysłane"));
		OrderStatusDTO orderStatus = new OrderStatusDTO();
		orderStatus.setStatusList(statuses);
		return orderStatus;
	}
	
	public StatusDTO statusMapping(Long statusId) {
		for(StatusDTO sd : createStatus().getStatusList()) {
			if(sd.getId()==statusId)
				return sd;
		}
		return createStatus().getStatusList().get(0);
	}
	
	public String status(Long orderId) {
		Order order = orderRepoService.getById(orderId);
		if(order.isRecieved())
			return "Odebrane";
		if(order.isSent())
			return "Wysłane";
		return "Złożone";
		
	}
	
	public Order updateStatus(Order order, String status) {
		if(status.equals("new")) {
			order.setRecieved(false);
			order.setSent(false);
		}
		if(status.equals("recived")) {
			order.setRecieved(true);
			order.setSent(false);
			order.setDateRecived(LocalDateTime.now());
		}
		if(status.equals("sent")) {
			order.setRecieved(false);
			order.setSent(true);
			order.setDateSent(LocalDateTime.now());
		}
		return order;
	}
	
	public Order updateQuantity (Long orderId, Long productId, Integer newQuantity) {
		Order order = orderRepoService.getById(orderId);
		List<Product> products = new ArrayList<>();
		order.getProducts().forEach(products::add);
		Product productToUpdate = productRepoService.getById(productId);
		products.removeIf(next -> next.equals(productToUpdate));
		for(int i = 0; i < newQuantity; i++) {
			products.add(productToUpdate);
		}
		orderRepoService.removeAllProducts(orderId);
		orderRepoService.updateProductsInOrder(orderId, products);
		recountOrderCost(orderId);
		return order;
	}
	
	public Order recountOrderCost(Long orderId) {
		Order order = orderRepoService.getById(orderId);
		Double totalCost = 0.0;
		for(Product p : order.getProducts()) {
			totalCost += p.getPrice();
		}
		totalCost += order.getShipment().getShipmentCost();
		if(order.isCashOnDelivery())
			totalCost += Shipment.CASH_ON_DELIVERY_COST;
		totalCost = Math.round(totalCost * 100)/100.0;
		order.setTotalPrice(totalCost);
		return orderRepoService.updateOrder(order);
	}
	
	
	

}
