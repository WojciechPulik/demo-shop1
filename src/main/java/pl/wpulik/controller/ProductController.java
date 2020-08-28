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

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Producer;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.PictureRepoService;
import pl.wpulik.service.ProducerRepoService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.ShipmentRepoService;

@Controller
public class ProductController {
	
	private ProductRepoService productRepoService;
	private ProductService productService;
	private PictureRepoService pictureRepoService;
	private ShipmentRepoService shipmentRepoService;
	private ProducerRepoService producerRepoService;
	private CategoryRepoService categoryRepoService;
	
	@Autowired
	public ProductController(ProductRepoService productRepoService, PictureRepoService pictureRepoService, 
			ShipmentRepoService shipmentRepoService, ProducerRepoService producerRepoService, CategoryRepoService categoryRepoService,
			ProductService productService) {
		this.productRepoService = productRepoService;
		this.pictureRepoService = pictureRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.producerRepoService = producerRepoService;
		this.categoryRepoService = categoryRepoService;
		this.productService = productService;
	}
	
	@GetMapping("/product")
	public String productCard(@RequestParam Long id, Model model) {
		Product product = productRepoService.getById(id);
		List<Picture> pictures = pictureRepoService.getByProductId(id);
		List<Category> categories = categoryRepoService.getAllCategories();
		Picture picture = new Picture();
		if(!pictures.isEmpty()) {
			picture = pictures.get(0);
		}else {
			picture = new Picture("images/noimage.jpg", "No image");
		}
		String url = picture.getUrl();
		model.addAttribute("url", url);
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		return "productcard";
	}
	
	@PostMapping("/save")
	public String addProduct(@ModelAttribute ProductDTO formProduct) {
		ProductService prodService = new ProductService();	
		if(checkNotEmpty(formProduct)) {
			productRepoService.addProduct(prodService.productMapping(formProduct), formProduct.getProducerId(), 
					formProduct.getCategoryId(), formProduct.getShipmentId());
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/addproduct")
	public String productForm(Model model) {	
		model.addAttribute("formProduct", productService.createProductDTO());
		return "addproduct";
	}
	
	@GetMapping("/updateproduct")
	public String updateProductCard(Model model) {
		List<Shipment> shipments = shipmentRepoService.getAllShipments();
		model.addAttribute("formProduct", new Product());
		model.addAttribute("shipment",  new Shipment());
		model.addAttribute("shipments",  shipments);
		return "updateproduct";
	}
	
	@PostMapping("/updateProd")
	public String updateProduct(@ModelAttribute Product product) {
		Product productToUpdate = new Product();
		productToUpdate = productRepoService.getById(product.getId());
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
		productRepoService.updateProduct(productToUpdate);
		return "redirect:/updateproduct";
	}
	
	@PostMapping("/addShipment")
	public String addShipment(@ModelAttribute Shipment shipment, @RequestParam Long productId) {
		Product productToUpdate = productRepoService.getById(productId);
		productToUpdate.getShipments().add(shipment);
		productRepoService.updateProduct(productToUpdate);
		return "redirect:/updateproduct";
	}
	
	private boolean checkNotEmpty(ProductDTO product) {
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
	}

}



