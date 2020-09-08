package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.model.User;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ShipmentRepoService;
import pl.wpulik.service.ShipmentService;
import pl.wpulik.service.UserService;

@Controller
@Scope("session")
public class OrderController {
	
	private OrderRepoService orderRepoService;
	private ProductRepoService productRepoService;
	private UserService userService;
	private ShipmentRepoService shipmentRepoService;
	private ShipmentService shipmentService;
	private boolean isCashOnDeliverySet;
	private List<Product> products = new ArrayList<>();
	private Double totalOrderCost;
	private Shipment orderShipment = new Shipment();
		
	@Autowired
	public OrderController(OrderRepoService orderRepoService, ProductRepoService productRepoService, 
			UserService userService, ShipmentRepoService shipmentRepoService, ShipmentService shipmentService) {
		this.orderRepoService = orderRepoService;
		this.productRepoService = productRepoService;
		this.userService = userService;
		this.shipmentRepoService = shipmentRepoService;
		this.shipmentService = shipmentService;
	}
	
	@GetMapping("/shoppingcard")
	public String shoppingCard(Model model) {
		if(orderShipment.getShipmentCost()==null)
			orderShipment.setShipmentCost(0.0);
		model.addAttribute("order", new Order());
		Double totalCost = 0.0;
		Double value = 0.0;
		totalCost = 0.0;
		for(Product p: products) {
			value = Math.round(p.getPrice()*p.getAddedQuantity() * 100)/100.0;
			p.setSummaryCost(value);
			totalCost = totalCost + p.getSummaryCost();
		}	
		if(orderShipment.getShipmentCost()!=null) {
			totalCost = totalCost + orderShipment.getShipmentCost();
		}
		totalOrderCost = Math.round(totalCost * 100)/100.0;
		model.addAttribute("shipments", shipmentService.orderShipment(products));
		model.addAttribute("shipment", orderShipment);
		model.addAttribute("totalCost", totalOrderCost);
		model.addAttribute("products", products);
		model.addAttribute("isCashOnDelivery", isCashOnDeliverySet);
		return "/shoppingcard";
	}
	
	@PostMapping("/addtoorder")
	public String addToOrder(@RequestParam(name="addedQuantity") Integer addedQuantity, 
			@RequestParam(name="productId") Long productId, Model model) {	
		orderShipment = new Shipment();
		isCashOnDeliverySet = false;
		boolean isAdded = false;
		model.addAttribute("productId", productId);
		model.addAttribute("addedQuantity", addedQuantity);
		Product product = productRepoService.getById(productId);
		product.setAddedQuantity(addedQuantity);
		for(Product p: products) {
			if(p.getId()==product.getId()) {
				p.setAddedQuantity(p.getAddedQuantity() + addedQuantity);
				isAdded = true;
			}
		}
		if(!isAdded) {
			products.add(product);
		}
		return "redirect:/";
	}
	
	@PostMapping("/sendorder")
	public String sendOrder(@ModelAttribute Order order, Model model) {
		model.addAttribute("order",order);	
		User user = new User();
		user = userService.getById(1L);
		order.setUser(user);
		order.setTotalPrice(totalOrderCost);
		order.setShipment(orderShipment);
		order.setCashOnDelivery(isCashOnDeliverySet);
		Order addedOrder = orderRepoService.addOrder(order);
		Long orderId = addedOrder.getId();
		orderRepoService.addProductsToOrder(orderId, products);
		shoppingCardCleaner();
		return String.format("redirect:/deliveryaddress/%d", orderId);
	}
	@PostMapping("/setordershipment")
	public String addShipmentToOrder(@ModelAttribute Shipment shipment, @ModelAttribute Order order,
			@RequestParam boolean isCashOnDelivery, Model model) {
		orderShipment = shipmentRepoService.getById(shipment.getId());
		model.addAttribute("shipment",orderShipment);
		model.addAttribute("order",order);
		order.setCashOnDelivery(isCashOnDelivery);
		isCashOnDeliverySet = isCashOnDelivery;
		if(isCashOnDeliverySet)
			orderShipment.setShipmentCost(orderShipment.getShipmentCost() + Shipment.CASH_ON_DELIVERY_COST);
		order.setShipment(orderShipment);
		return "redirect:/shoppingcard";
	}
	
	@GetMapping("/clearcard")
	public String shoppingCardCleaner() {
		for(int i = products.size()-1; i >= 0; i--) {
			products.remove(i);
		}
		totalOrderCost = 0.0;
		orderShipment.setShipmentCost(0.0);
		isCashOnDeliverySet = false;
		return "redirect:/shoppingcard";
	}
	
}
