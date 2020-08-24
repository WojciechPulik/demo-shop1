package pl.wpulik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.PictureRepoService;
import pl.wpulik.service.ProductRepoService;

@Controller
public class HomeController {
	
	private ProductRepoService productRepoService;
	private PictureRepoService pictureService;
	
	@Autowired
	public HomeController(ProductRepoService productRepoService, PictureRepoService pictureService) {
		this.productRepoService = productRepoService;
		this.pictureService = pictureService;
	}
	
	@GetMapping("/")
	public String home (Model model) {
		Integer addedQuantity = 0;
		List<Picture> pictures = new ArrayList<>();
		List<Product> products = productRepoService.getAllProducts();	
		for(Product p: products) {
			if(!pictureService.getByProductId(p.getId()).isEmpty()) {
				p.getPictures().add(pictureService.getByProductId(p.getId()).get(0));
				pictures.add(pictureService.getByProductId(p.getId()).get(0));
			}else {
				Picture noImagePicture = new Picture("images/noimage.jpg", "No Image");
				noImagePicture.setProduct(p);
				pictures.add(noImagePicture);
			}
		}	
		model.addAttribute("pictures", pictures);
		model.addAttribute("products", products);
		model.addAttribute("addedQuantity", addedQuantity);
		return "index";
	}
	

}
