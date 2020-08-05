package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.wpulik.model.Order;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.OrderService;

@Controller
public class AdminController {
	
	private OrderService orderService;
	
	@Autowired
	public AdminController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	
	@GetMapping("/allorders")
	public String allOrders(Model model) {
		List<Order> orders = new ArrayList<>();
		orders = orderService.getAllOrders();
		model.addAttribute("orders", orders);
		return "/allorderslist";
	}

}
