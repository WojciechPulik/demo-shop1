package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Producer;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProducerService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.ShipmentService;

@Controller
public class ProductController {
	
	private ProductService productService;
	private PictureService pictureService;
	private ShipmentService shipmentService;
	private ProducerService producerService;
	
	@Autowired
	public ProductController(ProductService productService, PictureService pictureService, 
			ShipmentService shipmentService, ProducerService producerService) {
		this.productService = productService;
		this.pictureService = pictureService;
		this.shipmentService = shipmentService;
		this.producerService = producerService;
	}
	
	@GetMapping("/product")
	public String productCard(@RequestParam Long id, Model model) {
		Product product = productService.getById(id);
		List<Picture> pictures = pictureService.getByProductId(id);
		Picture picture = new Picture();
		if(!pictures.isEmpty()) {
			picture = pictures.get(0);
		}else {
			picture = new Picture("images/noimage.jpg", "No image");
		}
		String url = picture.getUrl();
		model.addAttribute("url", url);
		model.addAttribute("product", product);
		return "productcard";
	}
	
	@PostMapping("/save")
	public String addProduct(@ModelAttribute Product formProduct, @ModelAttribute Producer producer, Model model) {
		if(checkNotEmpty(formProduct)) {
		model.addAttribute("formProduct", formProduct);
		model.addAttribute("producer", producer);
		formProduct.setId(null);
		productService.addProduct(formProduct, producer.getId());
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/addproduct")
	public String productForm(Model model) {
		List<Producer> producers = new ArrayList<>();
		producers = producerService.getAllProducers();
		model.addAttribute("formProduct", new Product());
		model.addAttribute("producer", new Producer());
		model.addAttribute("producers", producers);
		return "addproduct";
	}
	/*
	 * ****************************************************
	 */
	@GetMapping("/updateproduct")
	public String updateProductCard(Model model) {
		List<Shipment> shipments = shipmentService.getAllShipments();
		model.addAttribute("formProduct", new Product());
		model.addAttribute("shipments",  shipments);
		return "updateproduct";
	}
	
	@PostMapping("/updateProd")
	public String updateProduct(@ModelAttribute Product product) {
		Product productToUpdate = new Product();
		productToUpdate = productService.getById(product.getId());
		System.out.println(productToUpdate);
		if(!product.getName().isEmpty())
			productToUpdate.setName(product.getName());
		if(!product.getDescription().isEmpty())
			productToUpdate.setDescription(product.getDescription());
		if(product.getQuantity()!= 0)
			productToUpdate.setQuantity(product.getQuantity());
		if(product.getPrice()!=null)
			productToUpdate.setPrice(product.getPrice());
		if(!product.getIndex().isEmpty())
			productToUpdate.setIndex(product.getIndex());
		productService.updateProduct(productToUpdate);
		return "redirect:/updateproduct";
	}
	
	@PostMapping("/addShipment")
	public String addShipment(@RequestParam Long shipmentId, @RequestParam Long productId) {
		Product productToUpdate = productService.getById(productId);
		Shipment shipmentToAdd = shipmentService.getById(shipmentId);
		productToUpdate.getShipments().add(shipmentToAdd);
		productService.updateProduct(productToUpdate);
		return "redirect:/updateproduct";
	}
	
	private boolean checkNotEmpty(Product product) {
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
	}

}



