package pl.wpulik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductService;

@Controller
public class ProductController {
	
	private ProductService productService;
	private PictureService pictureService;
	
	@Autowired
	public ProductController(ProductService productService, PictureService pictureService) {
		this.productService = productService;
		this.pictureService = pictureService;
	}
	
	@GetMapping("/product")
	public String productCard(@RequestParam Long id, Model model) {
		Product product = productService.getById(id);
		List<Picture> pictures = pictureService.getByProductId(id);
		Picture picture = pictures.get(0);
		String url = picture.getUrl();
		model.addAttribute("url", url);
		model.addAttribute("product", product);
		return "productcard";
	}
	
	@PostMapping("/save")
	public String addProduct(@ModelAttribute Product formProduct, Model model) {
		if(checkNotEmpty(formProduct)) {
		model.addAttribute("formProduct", formProduct);
		productService.addProduct(formProduct);
		}
		return "redirect:/";
	}
	
	@GetMapping("/addproduct")
	public String productForm(Model model) {
		model.addAttribute("formProduct", new Product());
		return "addproduct";
	}
	
	private boolean checkNotEmpty(Product product) {
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
	}

}



