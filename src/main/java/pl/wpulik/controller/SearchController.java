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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.SearchParamDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Product;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.ProductService;

@Controller
public class SearchController {
	
	private ProductService productService;
	private CategoryRepoService categoryRepoService;
	
	@Autowired
	public SearchController(ProductService productService, CategoryRepoService categoryRepoService) {
		this.productService = productService;
		this.categoryRepoService = categoryRepoService;
	}

	@PostMapping("/usersearch")
	public String searchByNameFragment(@ModelAttribute SearchParamDTO searchPhrase, Model model) {
		model.addAttribute("searchPhrase", searchPhrase);
		return String.format("redirect:/usersearchresult/searchPhrase=%s/", searchPhrase.getPhrase());
	}
	
	@GetMapping("/usersearchresult/searchPhrase={searchPhrase}/")
	public String resultByNameFragmentPage (Model model, @PathVariable String searchPhrase,
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		Integer addedQuantity = 0;
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(4);
		Page<Product> productPage = productService.findByNameFragmentPage(
				PageRequest.of(currentPage - 1, pageSize), searchPhrase);
		model.addAttribute("productPage", productPage);
		int totalPages = productPage.getTotalPages();
		if(totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		List<Category> categories = categoryRepoService.getMainCategories();	
		model.addAttribute("categories", categories);
		model.addAttribute("addedQuantity", addedQuantity);
		model.addAttribute("searchPhrase", searchPhrase);
		model.addAttribute("searchPhraseDto", new SearchParamDTO());
		return "searchresult";
	}

}
