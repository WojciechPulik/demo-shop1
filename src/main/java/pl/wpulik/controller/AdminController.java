package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.OrderStatusDTO;
import pl.wpulik.model.Address;
import pl.wpulik.model.Order;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.AddressRepoService;
import pl.wpulik.service.AddressService;
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
	private AddressService addressService;
	
	@Autowired
	public AdminController(OrderRepoService orderRepoService, OrderService orderService, 
			ShipmentService shipmentService, ShipmentRepoService shipmentRepoService, AddressRepoService addressRepoService,
			AddressService addressService) {
		this.orderRepoService = orderRepoService;
		this.orderService = orderService;
		this.shipmentService = shipmentService;
		this.shipmentRepoService = shipmentRepoService;
		this.addressRepoService = addressRepoService;
		this.addressService = addressService;
	}
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	
	@GetMapping("/allorders") //TODO: move some code to service
	public String allOrders(Model model,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(27);
		List<OrderDTO> dtoOrders = new ArrayList<>();	
		Page<Order> orderPage = orderService.findPaginatedOrders(PageRequest.of(currentPage - 1, pageSize));
		for(Order o : orderPage.getContent())
			dtoOrders.add(orderService.orderDtoMapping(o));
		dtoOrders.sort(Comparator.comparing(OrderDTO::getId).reversed());
		int totalPages = orderPage.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("orderPage", orderPage);
		model.addAttribute("orders", dtoOrders);
		return "/allorderslist";
	}
	
	@GetMapping("/editorder")
	public String editOrder(@RequestParam Long id, Model model) {
		String status = orderService.status(id);
		OrderStatusDTO orderStatus = orderService.createStatus();		
		orderStatus.setOrderId(id);
		Order order = orderRepoService.getById(id);	
		order = orderService.editOrderValues(order);		
		model.addAttribute("address", order.getAddress());
		model.addAttribute("shipments", shipmentService.orderShipment(order.getProducts()));
		model.addAttribute("shipment", new Shipment());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("formOrderStatus", orderService.createStatus());
		model.addAttribute("products",  orderService.editedOrderProducts(order));
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
		return "redirect:/allorders";
	}
	
	
	
	
	
	
	
	
	
	
	
}
