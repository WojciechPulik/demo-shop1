package pl.wpulik.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.model.Category;
import pl.wpulik.model.Product;
import pl.wpulik.service.CategoryService;
import pl.wpulik.service.ProductService;

@Controller 
public class AdminProductController {
	
	private ProductService productService;
	private CategoryService categoryService;
	
	@Autowired
	public AdminProductController(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}
	
	
	@GetMapping("/allproductslist")
	public String allProductsList(Model model, 
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(27);
		Page<Product> productPage = productService.findPaginatedProductsForAdmin(PageRequest.of(currentPage - 1, pageSize));
		int totalPages = productPage.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(currentPage, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("productPage", productPage);
		return "allproductslist";
	}
	
	@PostMapping("/setproductactive")
	public String setProductActive(@RequestParam boolean isActive, @RequestParam Long productId) {
		productService.setProductActiv(isActive, productId);
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@PostMapping("/setmaincategory")
	public String setMainCategoryForProduct(@RequestParam Long productId, @ModelAttribute Category category) {
		productService.setMainCategory(productId, category.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@PostMapping("/setnewcategory")
	public String addProductToCategory(@RequestParam Long productId, @ModelAttribute Category category) {
		productService.setAdditionalCategory(productId, category.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}

	@PostMapping("/removefromcategory")
	public String removeProductFromCategory(@RequestParam Long productId, @ModelAttribute Category category) {
		categoryService.removeFromCategory(productId, category.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}

}
