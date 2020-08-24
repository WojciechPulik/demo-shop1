package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.wpulik.model.Producer;
import pl.wpulik.service.ProducerRepoService;

@Controller
public class ProducerController {
	
	private ProducerRepoService producerRepoService;
	
	@Autowired
	public ProducerController(ProducerRepoService producerRepoService) {
		this.producerRepoService = producerRepoService;
	}
	
	@GetMapping("/addproducer")
	public String addProducer(Model model) {
		model.addAttribute("producer", new Producer());
		return "addproducer";
	}
	
	@PostMapping("/saveproducer")
	public String saveProducer(@ModelAttribute Producer producer) {
		producerRepoService.addProducer(producer);		
		return "redirect:/addproducer";
	}

}
