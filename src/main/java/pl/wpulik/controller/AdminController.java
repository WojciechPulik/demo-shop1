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

import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.OrderRepoService;

@Controller
public class AdminController {
	
	private OrderRepoService orderRepoService;
	
	@Autowired
	public AdminController(OrderRepoService orderRepoService) {
		this.orderRepoService = orderRepoService;
	}
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	
	@GetMapping("/allorders")
	public String allOrders(Model model) {
		List<Order> orders = new ArrayList<>();
		orders = orderRepoService.getAllOrders();
		orders.sort(Comparator.comparing(Order::getId).reversed());
		model.addAttribute("orders", orders);
		return "/allorderslist";
	}
	
	@GetMapping("/editorder")
	public String editOrder(@RequestParam Long id, Model model) {
		Order order = orderRepoService.getById(id);
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
		model.addAttribute("products",  products);
		model.addAttribute("order", order);
		return "/admeditorder";
	}
	
	@PostMapping("/updatequantity")
	public String updateQuantity(@RequestParam Long orderId, @RequestParam Long productId, 
			@RequestParam Long newQuantity, Model model) {
		System.out.println("order Id: " + orderId);
		System.out.println("product Id: " + productId);
		System.out.println("newQuantity " + newQuantity);
		/*TODO: create orderService.class, create methods that update order*/
		//orderRepoService.addOrder(order);
		return editOrder(orderId, model);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
