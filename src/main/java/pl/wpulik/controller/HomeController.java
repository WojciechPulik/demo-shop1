package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductService;

@Controller
public class HomeController {
	
	private ProductService productService;
	private PictureService pictureService;
	
	@Autowired
	public HomeController(ProductService productService, PictureService pictureService) {
		this.productService = productService;
		this.pictureService = pictureService;
	}
	
	@GetMapping("/")
	public String home (Model model) {
		Integer addedQuantity = 0;
		List<Picture> pictures = new ArrayList<>();
		List<Product> products = productService.getAllProducts();	
		for(Product p: products) {
			if(!pictureService.getByProductId(p.getId()).isEmpty()) {
				p.getPictures().add(pictureService.getByProductId(p.getId()).get(0));
				pictures.add(pictureService.getByProductId(p.getId()).get(0));
			}
		}	
		model.addAttribute("pictures", pictures);
		model.addAttribute("products", products);
		model.addAttribute("addedQuantity", addedQuantity);
		return "index";
	}

}
