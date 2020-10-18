package pl.wpulik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Shipment;
import pl.wpulik.service.ShipmentRepoService;

@Controller
public class ShipmentController {
	
	private ShipmentRepoService shipmentRepoService;
	
	@Autowired
	public ShipmentController(ShipmentRepoService shipmentRepoService) {
		this.shipmentRepoService = shipmentRepoService;
	}

	@GetMapping("/createshipment")
	public String addNewShipment(Model model) {
		model.addAttribute("shipment", new Shipment());
		return "/shipmentcard";
	}
	
	@PostMapping("/saveshipment")
	public String saveShipment(@ModelAttribute Shipment shipment) {
		shipmentRepoService.addShipment(shipment);
		return "/admin";
	}
	
	@GetMapping("/editshipment")
	public String shipmentEdition(Model model) {
		List<Shipment> shipments = shipmentRepoService.getAllShipments();
		model.addAttribute("shipments",  shipments);
		return "/shipmentstoupdate";
	}
	
	@GetMapping("/editshipmentcard")
	public String shipmentToUpdate(@RequestParam Long shipmentId, Model model) {
		model.addAttribute("shipment", shipmentRepoService.getById(shipmentId));
		return "/shipmenteditcard";
	}
	
	@PostMapping("/updateshipment")
	public String updateShipment(@ModelAttribute Shipment shipment) {
		System.out.println(shipment.getId());
		shipmentRepoService.updateShipment(shipment);
		return "redirect:/editshipment";
	}	

}
