package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.OrderStatusDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.AddressRepoService;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.service.OrderService;
import pl.wpulik.service.ShipmentRepoService;
import pl.wpulik.service.ShipmentService;

@Controller
public class AdminController {
	
	private OrderRepoService orderRepoService;
	private OrderService orderService;
	private ShipmentService shipmentService;
	private ShipmentRepoService shipmentRepoService;
	private AddressRepoService addressRepoService;
	
	@Autowired
	public AdminController(OrderRepoService orderRepoService, OrderService orderService, 
			ShipmentService shipmentService, ShipmentRepoService shipmentRepoService, AddressRepoService addressRepoService) {
		this.orderRepoService = orderRepoService;
		this.orderService = orderService;
		this.shipmentService = shipmentService;
		this.shipmentRepoService = shipmentRepoService;
		this.addressRepoService = addressRepoService;
	}
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	
	@GetMapping("/allorders")
	public String allOrders(Model model) {
		List<Order> orders = new ArrayList<>();
		List<OrderDTO> dtoOrders = new ArrayList<>();
		orders = orderRepoService.getAllOrders();
		for(Order o : orders) 
			dtoOrders.add(orderService.orderDtoMapping(o));	
		dtoOrders.sort(Comparator.comparing(OrderDTO::getId).reversed());
		model.addAttribute("orders", dtoOrders);
		return "/allorderslist";
	}
	
	@GetMapping("/editorder")
	public String editOrder(@RequestParam Long id, Model model) {
		String status = orderService.status(id);
		OrderStatusDTO orderStatus = orderService.createStatus();		
		orderStatus.setOrderId(id);
		Order order = orderRepoService.getById(id);
		Address address = order.getAddress();
		if(order.isCashOnDelivery())
			order.getShipment().setShipmentCost(order.getShipment().getShipmentCost() + Shipment.CASH_ON_DELIVERY_COST);
		Set<Product> products = new HashSet<>();
		Map<Product, Integer> productsMap = new HashMap<>();
		for(Product p : order.getProducts()) {
			productsMap.put(p, (productsMap.get(p) == null ? 1 : productsMap.get(p) + 1));
		}
		Product product = new Product();
		for(Product p : productsMap.keySet()) {
			product = p;
			product.setAddedQuantity(productsMap.get(p));
			products.add(product);
		}
		model.addAttribute("address", address);
		model.addAttribute("shipments", shipmentService.orderShipment(order.getProducts()));
		model.addAttribute("shipment", new Shipment());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("formOrderStatus", orderService.createStatus());
		model.addAttribute("products",  products);
		model.addAttribute("order", order);
		model.addAttribute("status", status);
		return "/admeditorder";
	}
	
	@PostMapping("/updatequantity")
	public String updateQuantity(@RequestParam Long orderId, @RequestParam Long productId, 
			@RequestParam Integer newQuantity, Model model) {	
		orderService.updateQuantity(orderId, productId, newQuantity);
		return editOrder(orderId, model);
	}
	
	@PostMapping("/removeproduct")
	public String removeProductfromOrder(@RequestParam Long orderId, @RequestParam Long productId, Model model) {
		orderService.updateQuantity(orderId, productId, 0);
		return editOrder(orderId, model);
	}

	@PostMapping("/setstatus") 
	public String setOrderStatus(@ModelAttribute OrderStatusDTO formOrderStatus, Model model) {
		Order order = orderRepoService.getById(formOrderStatus.getOrderId());
		model.addAttribute("formOrderStatus",  formOrderStatus);
		String status = orderService.statusMapping(formOrderStatus.getOrderStatusId()).getStatus();
		order = orderService.updateStatus(order, status);
		orderRepoService.updateOrder(order);
		return editOrder(formOrderStatus.getOrderId(), model);
		
	}
	
	@PostMapping("/changeshipment")
	public String changeShipment(@ModelAttribute Shipment shipment, @RequestParam Long orderId, Model model) {
		Order order = orderRepoService.getById(orderId);
		order.setShipment(shipmentRepoService.getById(shipment.getId()));
		orderService.recountOrderCost(order);
		return editOrder(orderId, model);
	}
	
	@PostMapping("/cashondelivery")
	public String setCashOnDelivery(@RequestParam boolean isCashOnDelivery, @RequestParam Long orderId, Model model) {
		Order order = orderRepoService.getById(orderId);
		order.setCashOnDelivery(isCashOnDelivery);
		orderService.recountOrderCost(order);
		return editOrder(orderId, model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
