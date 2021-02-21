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

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Product;
import pl.wpulik.service.CategoryService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ProductService;

@Controller 
public class AdminProductController {
	
	private ProductService productService;
	private CategoryService categoryService;
	private ProductRepoService productRepoService;
	
	@Autowired
	public AdminProductController(ProductService productService, CategoryService categoryService,
			ProductRepoService productRepoService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.productRepoService = productRepoService;
	}
	
	@PostMapping("/save")
	public String addProduct(@ModelAttribute ProductDTO formProduct) {
		if(checkNotEmptyAndValidated(formProduct)) {
			Product product = productService.addNewProduct(
					productService.productMapping(formProduct), 
					formProduct.getProducerId(), 
					formProduct.getCategoryId(), 
					formProduct.getShipmentId(), 
					formProduct.getMultipartFile());
			return String.format("redirect:/updateproduct/%d", product.getId());
		}
		return "redirect:/unproperdata";
	}
	
	private boolean checkNotEmptyAndValidated(ProductDTO product) {
		/*
		 * Validation of description length is set for MySQL database. Could not work properly with different databases
		 */
		if(product.getDescription().length()>65535) {
			System.err.println("Description has more then 65535 characters and is too long");
			return false;
		}	
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
	}
	
	@PostMapping("/updateProd")
	public String updateProduct(@ModelAttribute Product product) {	
		if(checkNotEmptyAndValidated(product)) {
			productRepoService.updateProduct(productService.productUpdate(product));
		return String.format("redirect:/updateproduct/%d", product.getId());
		}
		return "redirect:/unproperdata";
	}
	
	private boolean checkNotEmptyAndValidated(Product product) {
		/*
		 * Validation of description length is set for MySQL database. Could not work properly with different databases
		 */
		if(product.getDescription().length()>65535) {
			System.err.println("Description has more then 65535 characters and is too long");
			return false;
		}	
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
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
	
	@GetMapping("/unproperdata")
	public String unproperData() {
		return "unproperdata";
	}

}
