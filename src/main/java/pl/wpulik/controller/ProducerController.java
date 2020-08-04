package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.wpulik.model.Producer;
import pl.wpulik.service.ProducerService;

@Controller
public class ProducerController {
	
	private ProducerService producerService;
	
	@Autowired
	public ProducerController(ProducerService producerService) {
		this.producerService = producerService;
	}
	
	@GetMapping("/addproducer")
	public String addProducer(Model model) {
		model.addAttribute("producer", new Producer());
		return "addproducer";
	}
	
	@PostMapping("/saveproducer")
	public String saveProducer(@ModelAttribute Producer producer) {
		producerService.addProducer(producer);		
		return "redirect:/addproducer";
	}

}
