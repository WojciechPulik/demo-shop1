package pl.wpulik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductRepoService;

@Controller
public class FileUploadController {
	
	private PictureService pictureService;
	private ProductRepoService productRepoService;
	
	@Autowired
	public FileUploadController(PictureService pictureService, ProductRepoService productRepoService) {
		this.pictureService = pictureService;
		this.productRepoService = productRepoService;
	}
	
	@PostMapping("/uploadproductimage")
	public String productImageUpload(@RequestParam Long productId, @RequestParam MultipartFile file) {
		Picture picture = pictureService.uploadProductPicture(file);
		Product product = productRepoService.getById(productId);
		product.getPictures().add(picture);
		productRepoService.updateProduct(product);	
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	
	

}
