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

import pl.wpulik.model.Category;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.PictureRepoService;

@Controller
public class CategoryController {
	
	private CategoryRepoService categoryRepoService;
	private PictureRepoService pictureService;
		
	@Autowired
	public CategoryController(CategoryRepoService categoryRepoService, PictureRepoService pictureService) {
		this.categoryRepoService = categoryRepoService;
		this.pictureService = pictureService;
	}

	@GetMapping("/addcategory")
	public String categoryForm(Model model) {
		model.addAttribute("category", new Category());
		return "categoryform";
	}
	
	@PostMapping("/addcategory")
	public String addCategory(@ModelAttribute Category category) {
		categoryRepoService.addCategory(category);
		return "/admin";
	}
	
	@GetMapping("/category")
	public String categoryProducts(@RequestParam Long id, Model model) {	
		List<Picture> pictures = new ArrayList<>();
		List<Category> categories = categoryRepoService.getAllCategories();
		Category category = categoryRepoService.getById(id);
		List<Product> products = category.getProducts();
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
		model.addAttribute("category", category);
		model.addAttribute("categories", categories);
		return "categorycard";
	}

}
