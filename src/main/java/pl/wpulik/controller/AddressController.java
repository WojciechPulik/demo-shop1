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
import pl.wpulik.model.Order;
import pl.wpulik.service.AddressRepoService;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.utils.MailService;
import pl.wpulik.utils.MailMessagesContent;

@Controller
public class AddressController {
	
	private AddressRepoService addressRepoService;
	private OrderRepoService orderRepoService;
	private CategoryRepoService categoryRepoService;
	private MailService mailService;
	
	@Autowired
	public AddressController(AddressRepoService addressRepoService, OrderRepoService orderRepoService, 
			CategoryRepoService categoryRepoService, MailService mailService) {
		this.addressRepoService = addressRepoService;
		this.orderRepoService = orderRepoService;
		this.categoryRepoService = categoryRepoService;
		this.mailService = mailService;
	}
	
	@GetMapping("/deliveryaddress/{orderId}")
	public String addressToOrder(@PathVariable Long orderId, Model model) {
		model.addAttribute("categories", categoryRepoService.getAllCategories());
		model.addAttribute("orderId", orderId);
		model.addAttribute("address", new Address());	
		return "/orderaddress";
	}
	
	@PostMapping("/addrestoorder")
	public String addAddressToOrder(@ModelAttribute Address address, @RequestParam Long orderId) {
		Address addressToAdd = addressRepoService.addAddress(address);
		Order order = orderRepoService.getById(orderId);
		order.setAddress(addressToAdd);
		orderRepoService.updateOrder(order);
		mailService.sendMail(address.getEmail(), 
				MailMessagesContent.getOrderConfirmationMessageSubject() + orderId, 
				MailMessagesContent.getOrderConfirmationMessage(address.getFirstName()), true);
		return "redirect:/orderclosing";
	}

}





