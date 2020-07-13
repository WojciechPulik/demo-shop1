package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import pl.wpulik.model.User;
import pl.wpulik.service.OrderService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.UserService;

@Controller
@Scope("session")
public class OrderController {
	
	private OrderService orderService;
	private ProductService productService;
	private UserService userService;
	private List<Product> products = new ArrayList<>();
	private Double totalOrderCost;
		
	@Autowired
	public OrderController(OrderService orderService, ProductService productService, UserService userService) {
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
	}
	
	@GetMapping("/shoppingcard")
	public String shoppingCard(Model model) {
		model.addAttribute("order", new Order());
		Double totalCost = 0.0;
		Double value = 0.0;
		for(Product p: products) {
			value = Math.round(p.getPrice()*p.getAddedQuantity() * 100)/100.0;
			p.setSummaryCost(value);
			totalCost = totalCost + p.getSummaryCost();
		}
		totalOrderCost = Math.round(totalCost * 100)/100.0;
		model.addAttribute("totalCost", totalOrderCost);
		model.addAttribute("products", products);
		return "/shoppingcard";
	}
	
	@PostMapping("/addtoorder")
	public String addToOrder(@RequestParam(name="addedQuantity") Integer addedQuantity, 
			@RequestParam(name="productId") Long productId, Model model) {	
		boolean isAdded = false;
		model.addAttribute("productId", productId);
		model.addAttribute("addedQuantity", addedQuantity);
		Product product = productService.getById(productId);
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
	public String sendOrder(@ModelAttribute Order order, @RequestParam boolean isCashOnDelivery, Model model) {
		model.addAttribute("order",order);	
		order.setCashOnDelivery(isCashOnDelivery);
		User user = new User();
		user = userService.getById(1L);
		order.setUser(user);
		order.setTotalPrice(totalOrderCost);
		Order addedOrder = orderService.addOrder(order);
		Long orderId = addedOrder.getId();
		orderService.addProductsToOrder(orderId, products);
		shoppingCardCleaner();
		return "thanks";
	}
	
	@GetMapping("/clearcard")
	public String shoppingCardCleaner() {
		for(int i = products.size()-1; i >= 0; i--) {
			products.remove(i);
		}
		return "redirect:/";
	}
	
	

}
