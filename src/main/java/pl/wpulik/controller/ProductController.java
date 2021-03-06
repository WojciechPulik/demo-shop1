package pl.wpulik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.wpulik.dto.SearchParamDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.service.CategoryRepoService;
import pl.wpulik.service.PictureService;
import pl.wpulik.service.ProductRepoService;
import pl.wpulik.service.ProductService;
import pl.wpulik.service.ShipmentRepoService;

@Controller
public class ProductController {
	
	private ProductRepoService productRepoService;
	private ProductService productService;
	private ShipmentRepoService shipmentRepoService;
	private CategoryRepoService categoryRepoService; 
	private PictureService pictureService;
	
	@Autowired
	public ProductController(ProductRepoService productRepoService, ShipmentRepoService shipmentRepoService, 
			CategoryRepoService categoryRepoService, ProductService productService, PictureService pictureService) {
		this.productRepoService = productRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.categoryRepoService = categoryRepoService;
		this.productService = productService;
		this.pictureService = pictureService;
	}
	
	@GetMapping("/product")
	public String productCard(@RequestParam(name="id") Long productId, 
			@RequestParam(defaultValue="true", required = true) Boolean isAvailable, 
			@RequestParam(required=false) String url,
			@RequestParam(required=false) Long categoryId, Model model) {
		Product product = productRepoService.getById(productId);
		product.setIsAvailable(isAvailable);
		List<Category> categories = categoryRepoService.getMainCategories();
		model.addAttribute("categories", categories);
		if(categoryId!=null) {
			Category subCategory = categoryRepoService.getById(categoryId);
			List<Category> categoriesTree = categoryRepoService.getCategoriesTreeForCategory(categoryId);
			int treeSize = categoriesTree.size();
			model.addAttribute("categoryId", categoryId);
			model.addAttribute("treeSize", treeSize);
			model.addAttribute("subCategory", subCategory);
			model.addAttribute("categoriesTree", categoriesTree);
		}
		Picture picture = pictureService.displayPicture(productId);
		if(url==null && product.getMainPicture()==null)
			url = picture.getUrl();
		if(url==null && product.getMainPicture()!=null)
			url = product.getMainPicture();
		model.addAttribute("url", url);
		model.addAttribute("product", product);
		return "productcard";
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
	
	
	@PostMapping("/addShipment")
	public String addShipment(@ModelAttribute Shipment shipment, @RequestParam Long productId) {
		Product productToUpdate = productRepoService.getById(productId);
		productToUpdate.getShipments().add(shipment);
		productRepoService.updateProduct(productToUpdate);
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@PostMapping("/dropshipment")
	public String dropShipment(@ModelAttribute Shipment shipment, @RequestParam Long productId) {
		System.out.println("dostawa do usunięcia: " + shipment.toString());
		System.out.println("product ID: " + productId);
		productRepoService.removeShipmentFromProduct(productId, shipment.getId());
		return String.format("redirect:/updateproduct/%d", productId);
	}
	
	@GetMapping("/chooseproducts")
	public String productChoice(Model model) {
		model.addAttribute("searchPhrase", new SearchParamDTO());
		return "/productschoice";
	}
	
	@PostMapping("/search")
	public String findByNameFragment(@ModelAttribute SearchParamDTO searchPhrase, Model model) {
		model.addAttribute("searchPhrase", searchPhrase);
		return String.format("redirect:/foundproducts/%s", searchPhrase.getPhrase());
	}
	
	@GetMapping("/foundproducts/{searchPhrase}")
	public String foundProducts(@PathVariable String searchPhrase, Model model) {
		List<Product> foundProducts = productService.findByNameFragment(searchPhrase);
		model.addAttribute("foundProducts", foundProducts);
		return "/foundproductslist";
	}

}



