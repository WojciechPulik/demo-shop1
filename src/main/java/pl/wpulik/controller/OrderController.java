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
import pl.wpulik.service.OrderService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.ShipmentService;
import pl.wpulik.service.UserService;

@Controller
@Scope("session")
public class OrderController {
	
	private static Double CASH_ON_DELIVERY_COST = 5.0;
	private OrderService orderService;
	private ProductService productService;
	private UserService userService;
	private ShipmentService shipmentService;
	private boolean isCashOnDeliverySet;
	private List<Product> products = new ArrayList<>();
	private Double totalOrderCost;
	private Shipment orderShipment = new Shipment();
		
	@Autowired
	public OrderController(OrderService orderService, ProductService productService, UserService userService, ShipmentService shipmentService) {
		this.orderService = orderService;
		this.productService = productService;
		this.userService = userService;
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
		model.addAttribute("shipments", orderShipment(products));
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
	public String sendOrder(@ModelAttribute Order order, Model model) {
		model.addAttribute("order",order);	
		User user = new User();
		user = userService.getById(1L);
		order.setUser(user);
		order.setTotalPrice(totalOrderCost);
		order.setShipment(orderShipment);
		Order addedOrder = orderService.addOrder(order);
		Long orderId = addedOrder.getId();
		orderService.addProductsToOrder(orderId, products);
		shoppingCardCleaner();
		return "thanks";
	}
	@PostMapping("/setordershipment")
	public String addShipmentToOrder(@ModelAttribute Shipment shipment, @ModelAttribute Order order,
			@RequestParam boolean isCashOnDelivery, Model model) {
		orderShipment = shipmentService.getById(shipment.getId());
		model.addAttribute("shipment",orderShipment);
		model.addAttribute("order",order);
		order.setCashOnDelivery(isCashOnDelivery);
		isCashOnDeliverySet = isCashOnDelivery;
		if(isCashOnDeliverySet)
			orderShipment.setShipmentCost(orderShipment.getShipmentCost() + CASH_ON_DELIVERY_COST);
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
	/*
	 * I am scared of this algorithm below. Will this be some kind of Skynet in the future? ;)
	 * 
	 * Create List with possible shipments for order:
	 */
	private List<Shipment> orderShipment(List<Product> orderProducts){
		Set<Product> setProd = new HashSet<>(orderProducts);
		Set<String> suppliers = new HashSet<>();
		Map<String, Shipment> shipMap = new HashMap<>();
		List<Shipment> resultList = new ArrayList<>();
		//adds every shipment supplier to Set suppliers:
		//creates a Map with pairs: supplier - shipment with the biggest max weight:
		boolean containsKey = false;
		boolean isBigger = false;
		for(Product p: setProd) {
			for(Shipment sh: p.getShipments()) {
				suppliers.add(sh.getSupplier());
				containsKey = shipMap.containsKey(sh.getSupplier());
				if(shipMap.get(sh.getSupplier())!=null) {
					isBigger = sh.getMaxWeight() > (shipMap.get(sh.getSupplier())).getMaxWeight();
				}
				shipMap.put(sh.getSupplier(), containsKey ? (isBigger ?	sh : shipMap.get(sh.getSupplier()) ) : sh);					
			}
		}	
		//creates a Set of Sets with possible suppliers for each product:	
		Set<Set<String>> suppliersProdSet = productShipmentSuppliers(setProd);		
		//removes shipments witch cannot be applied to every product:
		for(String str : suppliers) {
			for(Set<String> set : suppliersProdSet) {
				if(!set.contains(str)) {
					shipMap.remove(str);
				}
			}
		}	
		//result list of shipments possible for this order:
		for(String str : shipMap.keySet()) {
			resultList.add(shipMap.get(str));
		}
		return resultList;
	}	
	
	private Set<Set<String>> productShipmentSuppliers (Set<Product> setProd){
		Set<String> prodSup = new HashSet<>();
		Set<Set<String>> suppliersProdSet = new HashSet<>();
		for(Product p : setProd) {
			for(Shipment sh : p.getShipments()) {
				prodSup.add(sh.getSupplier());
			}
			suppliersProdSet.add(prodSup);
			prodSup = new HashSet<>();
		}
		return suppliersProdSet;
	}

}
