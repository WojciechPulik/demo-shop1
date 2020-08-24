package pl.wpulik.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.wpulik.model.Category;
import pl.wpulik.service.CategoryRepoService;

@Controller
public class CategoryController {
	
	private CategoryRepoService categoryRepoService;
		
	public CategoryController(CategoryRepoService categoryRepoService) {
		this.categoryRepoService = categoryRepoService;
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

}
