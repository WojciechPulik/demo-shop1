package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.PictureRepoService;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ProductService;

@Controller
public class PictureController {
	
	private PictureService pictureService;
	private PictureRepoService pictureRepoService;
	private ProductRepoService productRepoService;
	
	@Autowired
	public PictureController(PictureService pictureService, PictureRepoService pictureRepoService,
			ProductRepoService productRepoService) {
		this.pictureService = pictureService;
		this.pictureRepoService = pictureRepoService;
		this.productRepoService = productRepoService;
	}
	
	@PostMapping(value="/productimagesupdate", params="action=setMain")
	public String changeMainPicture(@ModelAttribute Picture picture, @RequestParam Long productId) {
		pictureService.changeMainPictureForProduct(productId, picture.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@PostMapping(value="/productimagesupdate", params="action=remove")
	public String removePictureFromProduct(@ModelAttribute Picture picture, @RequestParam Long productId) {
		pictureService.removePictureFromProduct(picture.getId(), productId);
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@GetMapping("/bigpicture")
	public String displayPictureInNewWindow(@RequestParam String url, @RequestParam String productName,Model model) {
		model.addAttribute("path", url);
		model.addAttribute("title", productName);
		return "bigpicture";
	}
	
	
	

}
