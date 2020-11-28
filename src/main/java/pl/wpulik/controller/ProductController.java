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

import pl.wpulik.dto.ProductDTO;
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
			@RequestParam(required=false) String url, Model model) {
		Product product = productRepoService.getById(productId);
		product.setIsAvailable(isAvailable);
		List<Category> categories = categoryRepoService.getMainCategories();	
		Picture picture = pictureService.displayPicture(productId);
		if(url==null)
			url = picture.getUrl();
		model.addAttribute("url", url);
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		return "productcard";
	}
	
	@PostMapping("/save")//TODO: move to AdminProductController
	public String addProduct(@ModelAttribute ProductDTO formProduct) {
		if(checkNotEmpty(formProduct)) {
			productService.addNewProduct(productService.productMapping(formProduct), 
					formProduct.getProducerId(), 
					formProduct.getCategoryId(), 
					formProduct.getShipmentId(), 
					formProduct.getMultipartFile());
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/addproduct")
	public String productForm(Model model) {	
		model.addAttribute("formProduct", productService.createProductDTO());
		return "addproduct";
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
		if(product.getMainCategoryId()!=null)
			model.addAttribute("mainCategoryName", categoryRepoService.getById(product.getMainCategoryId()).getName());
		if(product.getMainCategoryId()==null)
			model.addAttribute("mainCategoryName", "brak kategorii głównej");
		return "updateproduct";
	}
	
	@PostMapping("/updateProd")
	public String updateProduct(@ModelAttribute Product product) {	
		productRepoService.updateProduct(productService.productUpdate(product));
		return String.format("redirect:/updateproduct/%d", product.getId());
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
	
	private boolean checkNotEmpty(ProductDTO product) {
		return product.getName()!=null && product.getDescription()!=null && product.getPrice()!=null;
	}

}



