package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Address;
import pl.wpulik.service.AddressRepoService;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.service.OrderService;

@Controller
public class AdminController {
	
	private OrderRepoService orderRepoService;
	private OrderService orderService;
	private AddressRepoService addressRepoService;
	
	@Autowired
	public AdminController(OrderRepoService orderRepoService, OrderService orderService, 
			AddressRepoService addressRepoService) {
		this.orderRepoService = orderRepoService;
		this.orderService = orderService;
		this.addressRepoService = addressRepoService;
	}
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	
	@GetMapping("/editaddress")
	public String editDeliveryAddress(@RequestParam Long orderId, Model model) {
		model.addAttribute("orderId", orderId);
		Address address = new Address();
		if(orderRepoService.getById(orderId).getAddress() != null)
			address = orderRepoService.getById(orderId).getAddress();
		model.addAttribute("address", address);
		return "/admedad";
	}
	
	
	@PostMapping("/changeaddress")
	public String changeAddress(@ModelAttribute Address address, @RequestParam Long orderId) {
		Long addressId = null;
		if(orderRepoService.getById(orderId).getAddress() == null) {
			addressRepoService.addAddress(address);
			orderService.updateAddress(address, orderId);
		}
		else if(orderRepoService.getById(orderId).getAddress().getId() != null) {
			addressId = orderRepoService.getById(orderId).getAddress().getId();
			address.setId(addressId);
			addressRepoService.addAddress(address);			
		}
		return String.format("redirect:/admin/order/editorder?orderId=%d", orderId);
	}
	
	@GetMapping("/outofstock/{orderId}")
	public String outOfStockInfo(@PathVariable Long orderId, Model model) {
		model.addAttribute("orderId", orderId);
		return "/outofstock";
	}
	
	@GetMapping("/unproperdata")
	public String unproperData() {
		return "unproperdata";
	}
	
	
	
}
