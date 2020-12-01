package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Picture;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductService;

@Controller
public class PictureController {
	
	private PictureService pictureService;
	private ProductService productService;
	
	@Autowired
	public PictureController(PictureService pictureService, ProductService productService) {
		this.pictureService = pictureService;
		this.productService = productService;
	}
	
	@PostMapping(value="/productimagesupdate", params="action=setMain")
	public String changeMainPicture(@ModelAttribute Picture picture, @RequestParam Long productId) {
		pictureService.changeMainPictureForProduct(productId, picture.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@PostMapping(value="/productimagesupdate", params="action=remove")
	public String removePictureFromProduct(@ModelAttribute Picture picture, @RequestParam Long productId) {
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	
	

}
