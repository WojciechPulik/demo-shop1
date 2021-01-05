package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.OrderStatusDTO;
import pl.wpulik.dto.StatusDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.Order;
import pl.wpulik.model.OrderProduct;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;

@Service
public class OrderService {
	
	private OrderRepoService orderRepoService;
	private OrderProductRepoService orderProductRepoService;
	private AddressService addressService;
	private ProductRepoService productRepoService;
	
	public OrderService () {}
	
	@Autowired
	public OrderService(OrderRepoService orderRepoService, OrderProductRepoService orderProductRepoService,
			AddressService addressService, ProductRepoService productRepoService) {
		this.orderRepoService = orderRepoService;
		this.orderProductRepoService = orderProductRepoService;
		this.addressService = addressService;
		this.productRepoService = productRepoService;
	}
	
	public Order addOrder(Order order) {
		return orderRepoService.addOrder(order);
	}
	
	public void addProductsToOrder(Long orderId, List<Product> products){
		orderRepoService.addProductsToOrder(orderId, products);
	}
	
	public Page<Order> findPaginatedOrders(Pageable pageable){
		return orderRepoService.getAllOrders(pageable);
	}
	
	public OrderDTO orderDtoMapping(Order order) {
		OrderDTO orderDto = new OrderDTO();
		orderDto.setId(order.getId());
		orderDto.setOrderProducts(order.getOrderProducts());
		orderDto.setDatePurchase(order.getDatePurchase());
		orderDto.setDateRecived(order.getDateRecived());
		orderDto.setDateSent(order.getDateSent());
		orderDto.setFormattedPurchaseTime(order.getFormattedPurchaseTime());
		orderDto.setFormattedReciveTime(order.getFormattedReciveTime());
		orderDto.setFormattedSentTime(order.getFormattedSentTime());
		orderDto.setOrderDetails(order.getOrderDetails());
		orderDto.setUser(order.getUser());
		orderDto.setShipment(order.getShipment());
		orderDto.setRecieved(order.isRecieved());
		orderDto.setCashOnDelivery(order.isCashOnDelivery());
		orderDto.setPayed(order.isPayed());
		orderDto.setSent(order.isSent());
		orderDto.setCanceled(order.isCanceled());
		orderDto.setTotalPrice(order.getTotalPrice());
		orderDto.setStatus(status(order.getId()));		
		return orderDto;
	}
	public OrderStatusDTO createStatus() {
		List<StatusDTO> statuses = new ArrayList<>();
		statuses.add(new StatusDTO(1L, "new", "Złożone"));
		statuses.add(new StatusDTO(2L, "recived", "Odebrane"));
		statuses.add(new StatusDTO(3L, "sent", "Wysłane"));
		statuses.add(new StatusDTO(4L, "canceled", "Anuluj Zamówienie"));
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
		if(order.isCanceled())
			return "Anulowane";
		return "Złożone";
		
	}
	
	public Order updateStatus(Order order, String status) {
		if(status.equals("new")) {
			order.setRecieved(false);
			order.setSent(false);
			order.setCanceled(false);
		}
		if(status.equals("recived")) {
			order.setRecieved(true);
			order.setSent(false);
			order.setCanceled(false);
			order.setDateRecived(LocalDateTime.now());
		}
		if(status.equals("sent")) {
			order.setRecieved(false);
			order.setSent(true);
			order.setCanceled(false);
			order.setDateSent(LocalDateTime.now());
		}
		if(status.equals("canceled")) {
			order.setRecieved(false);
			order.setSent(false);
			order.setCanceled(true);
		}
		return order;
	}
	
	
	
	public Order updateOrderProductQuantity(Long orderId, Long orderProductId, Integer newQuantity) {
		Order order = orderRepoService.getById(orderId);
		List<OrderProduct> products = new ArrayList<>();
		order.getOrderProducts().forEach(products::add);
		OrderProduct productToUpdate = orderProductRepoService.getById(orderProductId);
		//update the product stock quantity in DB by the product quantity in the order:
		productRepoService.updateProductStockQuantity(products, productToUpdate.getProductId(), newQuantity); 
		orderProductRepoService.removeAllProductsFromOrder(orderId);
		products.removeIf(next -> next.getId() == orderProductId);
		productToUpdate.setAddedQuantity(newQuantity);
		products.add(productToUpdate);
		order.setOrderProducts(products);
		return order;
	}
	
	public Order removeOrderProductFromOrder(Long orderId, Long orderProductId) {
		Order order = orderRepoService.getById(orderId);
		List<OrderProduct> orderProducts = new ArrayList<>();
		order.getOrderProducts().forEach(orderProducts::add);
		Long productId = orderProducts.stream()
							.filter(p -> p.getId()==orderProductId)
							.mapToLong(p -> p.getProductId())
							.findFirst()
							.getAsLong();
		//update the product stock quantity in DB after product removal from the order:
		productRepoService.updateProductStockQuantity(orderProducts, productId, 0);
		orderProducts.removeIf(next -> next.getId() == orderProductId);
		orderProductRepoService.removeAllProductsFromOrder(orderId);
		orderProductRepoService.removeOrderProduct(orderProductId);
		order.setOrderProducts(orderProducts);
		return order;
	}
	
	public Order recountOrderCost(Order order) {
		Double totalCost = 0.0;
		for(OrderProduct p : order.getOrderProducts()) {
			totalCost += p.valueOfAll();
		}
		totalCost += order.getShipment().getShipmentCost();
		if(order.isCashOnDelivery())
			totalCost += Shipment.CASH_ON_DELIVERY_COST;
		totalCost = Math.round(totalCost * 100)/100.0;
		order.setTotalPrice(totalCost);
		return orderRepoService.updateOrder(order);
	}
	
	public Order updateAddress(Address address, Long orderId) {
		Order order = orderRepoService.getById(orderId);
		order.setAddress(address);
		order = orderRepoService.updateOrder(order);
		return order;
	}
	
	public Order editOrderValues(Order order) {
		Address address = order.getAddress();
		if(address==null) {
			address = addressService.defaultAddress();
			order.setAddress(address);
		}
		if(order.isCashOnDelivery())
			order.getShipment().setShipmentCost(order.getShipment().getShipmentCost() + Shipment.CASH_ON_DELIVERY_COST);
		return order;
	}
	
	public int currentCountOfProductsInOrder(List<Product> products, Long productId) {
		return products.stream()
		.filter(p -> p.getId()==productId)
		.mapToInt(p ->p.getAddedQuantity())
		.sum(); 
	}
}
