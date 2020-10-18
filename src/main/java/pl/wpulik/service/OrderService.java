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
import pl.wpulik.model.Shipment;

@Service
public class OrderService {
	
	private OrderRepoService orderRepoService;
	private OrderProductRepoService orderProductRepoService;
	private AddressService addressService;
	
	public OrderService () {}
	
	@Autowired
	public OrderService(OrderRepoService orderRepoService, OrderProductRepoService orderProductRepoService,
			AddressService addressService) {
		this.orderRepoService = orderRepoService;
		this.orderProductRepoService = orderProductRepoService;
		this.addressService = addressService;
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
	
	
	/*New method for OrderProduct*/
	public Order updateOrderProductQuantity(Long orderId, Long productId, Integer newQuantity) {
		Order order = orderRepoService.getById(orderId);
		List<OrderProduct> products = new ArrayList<>();
		order.getOrderProducts().forEach(products::add);
		OrderProduct productToUpdate = orderProductRepoService.getById(productId);
		orderProductRepoService.removeAllProductsFromOrder(orderId);
		products.removeIf(next -> next.getId() == productId);
		productToUpdate.setAddedQuantity(newQuantity);
		products.add(productToUpdate);
		order.setOrderProducts(products);
		return order;
	}
	
	public Order removeOrderProductFromOrder(Long orderId, Long productId) {
		Order order = orderRepoService.getById(orderId);
		List<OrderProduct> orderProducts = new ArrayList<>();
		order.getOrderProducts().forEach(orderProducts::add);
		orderProducts.removeIf(next -> next.getId() == productId);
		orderProductRepoService.removeAllProductsFromOrder(orderId);
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
}
