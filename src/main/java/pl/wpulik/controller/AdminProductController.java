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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.CategoryService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.ShipmentRepoService;

@Controller 
@RequestMapping("/admin/product")
public class AdminProductController {
	
	private ProductService productService;
	private CategoryService categoryService;
	private ProductRepoService productRepoService;
	private ShipmentRepoService shipmentRepoService;
	private CategoryRepoService categoryRepoService;
	
	@Autowired
	public AdminProductController(ProductService productService, CategoryService categoryService,
			ProductRepoService productRepoService, ShipmentRepoService shipmentRepoService,
			CategoryRepoService categoryRepoService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.productRepoService = productRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.categoryRepoService = categoryRepoService;
	}
	
	@GetMapping("/addproduct")
	public String productForm(Model model) {	
		model.addAttribute("formProduct", productService.createProductDTO());
		return "addproduct";
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
			return String.format("redirect:/admin/product/updateproduct/%d", product.getId());
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
	
	@GetMapping("/updateproduct/{prodId}")
	public String updateProductCard(@PathVariable Long prodId, Model model) {
		List<Shipment> shipments = shipmentRepoService.getAllShipments();
		List<Category> categories = categoryRepoService.getAllCategories();
		Product product = productRepoService.getById(prodId);
		model.addAttribute("formProduct", product);
		model.addAttribute("shipment",  new Shipment());
		model.addAttribute("shipments",  shipments);
		model.addAttribute("category",  new Category());
		model.addAttribute("categories",  categories);
		model.addAttribute("picture",  new Picture());
		if(product.getMainCategoryId()!=null)
			model.addAttribute("mainCategoryName", categoryRepoService.getById(product.getMainCategoryId()).getName());
		if(product.getMainCategoryId()==null)
			model.addAttribute("mainCategoryName", "brak kategorii głównej");
		return "updateproduct";
	}
	
	@PostMapping("/updateProd")
	public String updateProduct(@ModelAttribute Product product) {	
		if(checkNotEmptyAndValidated(product)) {
			productRepoService.updateProduct(productService.productUpdate(product));
		return String.format("redirect:/admin/product/updateproduct/%d", product.getId());
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
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}
	
	@PostMapping("/setmaincategory")
	public String setMainCategoryForProduct(@RequestParam Long productId, @ModelAttribute Category category) {
		productService.setMainCategory(productId, category.getId());
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}
	
	@PostMapping("/setnewcategory")
	public String addProductToCategory(@RequestParam Long productId, @ModelAttribute Category category) {
		productService.setAdditionalCategory(productId, category.getId());
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}

	@PostMapping("/removefromcategory")
	public String removeProductFromCategory(@RequestParam Long productId, @ModelAttribute Category category) {
		categoryService.removeFromCategory(productId, category.getId());
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}
	
	@PostMapping("/addShipment")
	public String addShipment(@ModelAttribute Shipment shipment, @RequestParam Long productId) {
		Product productToUpdate = productRepoService.getById(productId);
		productToUpdate.getShipments().add(shipment);
		productRepoService.updateProduct(productToUpdate);
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}
	
	@PostMapping("/dropshipment")
	public String dropShipment(@ModelAttribute Shipment shipment, @RequestParam Long productId) {
		System.out.println("dostawa do usunięcia: " + shipment.toString());
		System.out.println("product ID: " + productId);
		productRepoService.removeShipmentFromProduct(productId, shipment.getId());
		return String.format("redirect:/admin/product/updateproduct/%d", productId);
	}

}
