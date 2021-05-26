package pl.wpulik.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.SearchParamDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Product;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.CategoryService;
import pl.wpulik.service.ProductService;

@Controller
public class CategoryController {

	private CategoryRepoService categoryRepoService;
	private CategoryService categoryService;
	private ProductService productService;

	@Autowired
	public CategoryController(CategoryRepoService categoryRepoService, CategoryService categoryService, 
			ProductService productService) {
		this.categoryRepoService = categoryRepoService;
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@GetMapping("/addcategory")
	public String categoryForm(Model model) {
		model.addAttribute("allCategories", categoryRepoService.getAllCategories());
		model.addAttribute("category", new Category());
		return "categoryform";
	}

	@PostMapping("/addcategory")
	public String addCategory(@ModelAttribute Category category) {
		category.setHaveSubcategory(false);
		categoryRepoService.addCategory(category);
		return "/admin";
	}

	@GetMapping("/category")
	public String categoryProducts(@RequestParam Long categoryId,
			Model model,
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
	Integer addedQuantity = 0;
	int currentPage = page.orElse(1);
	int pageSize = size.orElse(6);
	Page<Product> productPage = productService.findPaginatedProducts(PageRequest.of(currentPage - 1, pageSize), categoryId);
	model.addAttribute("productPage", productPage);
	int totalPages = productPage.getTotalPages();
	if(totalPages > 0) {
		List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.collect(Collectors.toList());
		model.addAttribute("pageNumbers", pageNumbers);
	}
	Category subCategory = categoryRepoService.getById(categoryId);
	List<Category> categories = categoryRepoService.getMainCategories();
	List<Category> categoriesTree = categoryRepoService.getCategoriesTreeForCategory(categoryId);
	int treeSize = categoriesTree.size();
	model.addAttribute("categoryId", categoryId);
	model.addAttribute("treeSize", treeSize);
	model.addAttribute("subCategory", subCategory);
	model.addAttribute("categories", categories);
	model.addAttribute("categoriesTree", categoriesTree);
	model.addAttribute("addedQuantity", addedQuantity);
	model.addAttribute("searchPhraseDto", new SearchParamDTO());
	return "categorycard";
	}
	@GetMapping("/editcategory")
	public String editCategoryChoice(Model model) {
		model.addAttribute("allCategories", categoryRepoService.getAllCategories());
		model.addAttribute("category", new Category());
		return "editcategory";
	}
	
	@PostMapping("/categoryedition")
	public String editCategoryForm(@ModelAttribute Category category) {
		return String.format("redirect:/editcategoryform?categoryId=%d", category.getId());
	}
	@GetMapping("/editcategoryform{categoryId}")
	public String editCategory(Model model, @RequestParam Long categoryId){	
		model.addAttribute("allCategories", categoryRepoService.getAllCategories());
		model.addAttribute("category", categoryRepoService.getById(categoryId));
		return "categoryupdateform";
	}
	
	@PostMapping("/updatecategory")
	public String updateCategory(@ModelAttribute Category category) {
		categoryService.categoryMapAndUpdate(category);
		return "admin";
	}

}
