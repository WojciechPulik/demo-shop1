package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.OrderDTO;
import pl.wpulik.dto.OrderStatusDTO;
import pl.wpulik.model.Order;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.OrderRepoService;
import pl.wpulik.service.OrderService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ShipmentRepoService;
import pl.wpulik.service.ShipmentService;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
	
	private OrderService orderService;
	private OrderRepoService orderRepoService;
	private ShipmentService shipmentService;
	private ShipmentRepoService shipmentRepoService;
	private ProductRepoService productRepoService;
	
	@Autowired
	public AdminOrderController(OrderService orderService, OrderRepoService orderRepoService,
			ShipmentService shipmentService, ShipmentRepoService shipmentRepoService,
			ProductRepoService productRepoService) {
		this.orderService = orderService;
		this.orderRepoService = orderRepoService;
		this.shipmentService = shipmentService;
		this.shipmentRepoService = shipmentRepoService;
		this.productRepoService = productRepoService;
	}

	@GetMapping("/allorders") 
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
		return "allorderslist";
	}

	@GetMapping("/editorder")
	public String editOrder(@RequestParam Long orderId, Model model) {
		String status = orderService.status(orderId);
		OrderStatusDTO orderStatus = orderService.createStatus();		
		orderStatus.setOrderId(orderId);
		Order order = orderRepoService.orderWithProducts(orderId);
		order = orderService.editOrderValues(order);
		model.addAttribute("address", order.getAddress());
		model.addAttribute("shipments", shipmentService.orderShipment(order.getProducts()));
		model.addAttribute("shipment", new Shipment());
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("formOrderStatus", orderService.createStatus());
		model.addAttribute("products",  order.getOrderProducts());
		model.addAttribute("order", order);
		model.addAttribute("status", status);
		return "/admeditorder";
	}
	
	@PostMapping("/updatequantity")
	public String updateQuantity(@RequestParam Long orderId, @RequestParam Long orderProductId, 
			@RequestParam Integer newQuantity, @RequestParam Integer addedToOrder, 
			@RequestParam Long productId,Model model) {	
		if(!productRepoService.isStockEnough(productId, newQuantity, addedToOrder))
			return String.format("redirect:/outofstock/%d", orderId);
		Order order = orderService.updateOrderProductQuantity(orderId, orderProductId, newQuantity);
		orderService.recountOrderCost(order);
		return String.format("redirect:/admin/order/editorder?orderId=%d", orderId);
	}
	
	@PostMapping("/removeproduct")
	public String removeProductfromOrder(@RequestParam Long orderId, @RequestParam Long productId, Model model) {
		Order order = orderService.removeOrderProductFromOrder(orderId, productId);
		orderService.recountOrderCost(order);
		return String.format("redirect:/admin/order/editorder?orderId=%d", orderId); 
	}

	@PostMapping("/setstatus") 
	public String setOrderStatus(@ModelAttribute OrderStatusDTO formOrderStatus, Model model) {
		Order order = orderRepoService.getById(formOrderStatus.getOrderId());
		model.addAttribute("formOrderStatus",  formOrderStatus);
		String status = orderService.statusMapping(formOrderStatus.getOrderStatusId()).getStatus();
		order = orderService.updateStatus(order, status);
		orderRepoService.updateOrder(order);
		return String.format("redirect:/admin/order/editorder?orderId=%d", order.getId());
		
	}
	
	@PostMapping("/changeshipment")
	public String changeShipment(@ModelAttribute Shipment shipment, @RequestParam Long orderId, Model model) {
		Order order = orderRepoService.getById(orderId);
		order.setShipment(shipmentRepoService.getById(shipment.getId()));
		orderService.recountOrderCost(order);
		return String.format("redirect:/admin/order/editorder?orderId=%d", orderId);
	}
	
	@PostMapping("/cashondelivery")
	public String setCashOnDelivery(@RequestParam boolean isCashOnDelivery, @RequestParam Long orderId, Model model) {
		Order order = orderRepoService.getById(orderId);
		order.setCashOnDelivery(isCashOnDelivery);
		orderService.recountOrderCost(order);
		return String.format("redirect:/admin/order/editorder?orderId=%d", orderId);
	}

}
