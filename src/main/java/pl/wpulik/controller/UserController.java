package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.service.AddressService;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.service.OrderService;
import pl.wpulik.service.UserService;
import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.RegistrationDTO;
import pl.wpulik.dto.UserDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.User;
import pl.wpulik.model.UserRole;
import pl.wpulik.repository.UserRoleRepository;

@Controller
public class UserController {
	
	private UserService userService;
	private AddressService addressService;
	private UserRoleRepository userRoleRepository;
	private OrderRepoService orderRepoService;
	private OrderService orderService;
	
	@Autowired
	public UserController(UserService userService, AddressService addressService,
			UserRoleRepository userRoleRepository, OrderRepoService orderRepoService,
			OrderService orderService) {
		this.userService = userService;
		this.addressService = addressService;
		this.userRoleRepository = userRoleRepository;
		this.orderRepoService = orderRepoService;
		this.orderService = orderService;
	}
	
	@GetMapping("/userpanel")
	public String userPanel(Model model) {
		UserDTO userDto = userService.getDtoById(1L);//TODO:change after logging implementation
		model.addAttribute("userDto", userDto);
		return "userpanel";
	}

	@GetMapping("/registration")
	public String registrationForm(Model model) {
		RegistrationDTO registDto = RegistrationDTO.toDtoMapping(new User(), new Address());
		model.addAttribute("registDto", registDto);
		return "registrationform";		
	}
	
	@PostMapping("/register")
	public String registerNewUser(@ModelAttribute RegistrationDTO registDto) {
		System.out.println(registDto.toString());
		userService.addUser(RegistrationDTO.fromDtoMapping(registDto));
		return "redirect:/userpanel";
	}
	
	@GetMapping("/userdetails")
	public String userDetails(Model model) {
		UserDTO userDto = userService.getDtoById(1L);//TODO:change after logging implementation
		model.addAttribute("userDto", userDto);
		return "userdetails";
	}
	
	@GetMapping("/userdetailsedit")
	public String userDetailsEdition(Model model) {
		UserDTO userDto = userService.getDtoById(1L);//TODO:change after logging implementation
		model.addAttribute("userDto", userDto);
		return "userdetailseditform";
	}
	
	@PostMapping("/updateuser")
	public String updateUser(@ModelAttribute UserDTO userDto) {
		User user = UserDTO.fromDtoMapping(userDto);
		System.out.println(user.toString() + "\n" + user.getAddress().toString());
		userService.updateUser(user);
		return "redirect:/userdetails";
	}
	
	@GetMapping("/userorders")
	public String userOrders(Model model, 
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		UserDTO userDto = userService.getDtoById(1L);//TODO:change after logging implementation
		List<OrderDTO> dtoOrders = new ArrayList<>();
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(16);
		Page<Order> ordersPage = userService.getAllUserOrders(
				PageRequest.of(currentPage - 1, pageSize), userDto.getId());
		for(Order o : ordersPage.getContent()) 
			dtoOrders.add(orderService.orderDtoMapping(o));
		model.addAttribute("ordersPage", ordersPage);
		model.addAttribute("dtoOrders", dtoOrders);
		int totalPages = ordersPage.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("userDto", userDto);
		return "userorderlist";
	}
	
	@GetMapping("/orderdetails")
	public String userOrderDetails(Model model, @RequestParam Long orderId, @RequestParam Long userId) {
		UserDTO userDto = userService.getDtoById(userId);
		Order order = orderRepoService.getById(orderId);
		String status = orderService.status(orderId);
		order = orderService.editOrderValues(order);
		model.addAttribute("products",  order.getOrderProducts());
		model.addAttribute("address", order.getAddress());
		model.addAttribute("userDto", userDto);
		model.addAttribute("order", order);
		model.addAttribute("status", status);
		return "orderdetails";
	}
	
	
	
	
	
}
