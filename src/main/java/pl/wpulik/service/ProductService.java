package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Category;
import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;

@Service
public class ProductService {
	
	private ProducerRepoService producerRepoService;
	private ShipmentRepoService shipmentRepoService;
	private CategoryRepoService categoryRepoService;
	private CategoryService categoryService;
	private ProductRepoService productRepoService;
	private PictureRepoService pictureRepoService;
	private PictureService pictureService;
	
	@Autowired
	public ProductService(ProducerRepoService producerRepoService, ShipmentRepoService shipmentRepoService,
			CategoryRepoService categoryRepoService, CategoryService categoryService, ProductRepoService productRepoService, 
			PictureRepoService pictureRepoService, PictureService pictureService) {
		this.producerRepoService = producerRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.categoryRepoService = categoryRepoService;
		this.categoryService = categoryService;
		this.productRepoService = productRepoService;
		this.pictureRepoService = pictureRepoService;
		this.pictureService = pictureService;
	}

	public ProductService() {}
	
	//TODO: refactor this method
	public Product addNewProduct(Product product, Long producerId, Long categoryId, Long shipmentId, MultipartFile file ) {
		product.setProducer(producerRepoService.getById(producerId));
		product.setCategories(categoryRepoService.allRelatedCategories(categoryId));
		product.getShipments().add(shipmentRepoService.getById(shipmentId));
		Picture picture = pictureService.uploadProductPicture(file);
		if(!file.isEmpty());
			product.getPictures().add(picture);
		productRepoService.addProduct(product);
		return product;
	}
	
	public Page<Product> findPaginatedProducts(Pageable pageable, Long categoryId){
		Page<Product> productPage = setMainPictureInProduct(pageable, categoryId);
		return productPage;	
	}
	
	public Page<Product> findPaginatedProductsForAdmin(Pageable pageable){
		return productRepoService.getAllProducts(pageable);
	}
	
	public ProductDTO createProductDTO() {
		ProductDTO productDto = new ProductDTO();
		productDto.setProducers(producerRepoService.getAllProducers());
		productDto.setShipments(shipmentRepoService.getAllShipments());
		productDto.setCategories(categoryRepoService.getAllCategories());
		return productDto;
	}
	
	public Product productMapping(ProductDTO productDto) {
		System.out.println(productDto.getCategoryId());
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setQuantity(productDto.getQuantity());
		product.setPrice(productDto.getPrice());
		product.setIndex(productDto.getIndex());
		if(productDto.getMainCategoryId()==null) {
			productDto.setMainCategoryId(productDto.getCategoryId());
		}
		product.setMainCategoryId(productDto.getMainCategoryId());
		
		return product;
	}
	
	public Product productUpdate(Product product) {
		Product productToUpdate = new Product();
		productToUpdate = productRepoService.getById(product.getId());
		System.out.println(productToUpdate);
		if(!product.getName().isEmpty())
			productToUpdate.setName(product.getName());
		if(!product.getDescription().isEmpty())
			productToUpdate.setDescription(product.getDescription());
		if(product.getQuantity()!= 0)
			productToUpdate.setQuantity(product.getQuantity());
		if(product.getPrice()!=null)
			productToUpdate.setPrice(product.getPrice());
		if(!product.getIndex().isEmpty())
			productToUpdate.setIndex(product.getIndex());
		return productToUpdate;
	}
	
	public Product setProductActiv(boolean isActive, Long productId) {
		Product toUpdate = productRepoService.getById(productId);
		toUpdate.setActive(isActive);
		return productRepoService.updateProduct(toUpdate);	
	}
	
	public List<Product> findByNameFragment(String phrase){
		String startWithOut = phrase + "%";
		String insideWithOut = "%" + phrase + "%";
		return productRepoService.findByNameFragment(startWithOut, insideWithOut);
	}
	
	public Product setMainCategory(Long productId, Long categoryId) {
		Product product = productRepoService.getById(productId);
		List<Category> categories = new ArrayList<>();
		product.getCategories().forEach(categories::add);
		for(Category c : categories)
			categoryService.removeFromCategory(productId, c.getId());
		product.setMainCategoryId(categoryId);
		product.setCategories(categoryRepoService.allRelatedCategories(categoryId));
		categoryRepoService.clearResultReladteList();
		return productRepoService.updateProduct(product);
	}
	
	public Product setAdditionalCategory(Long productId, Long categoryId) {
		Product product = productRepoService.getById(productId);
		Category category = categoryRepoService.getById(categoryId);
		product.getCategories().add(category);
		return productRepoService.updateProduct(product);
	}
	
	
	private Page<Product> setMainPictureInProduct(Pageable pageable, Long categoryId) {
		List<Product> products = new ArrayList<>();
		Page<Product> productsPage = new PageImpl<>(products);
		if(categoryId != null) {
			productsPage = categoryRepoService.getAllActiveProductsByCategory(pageable, categoryId);
		} else {
			productsPage = productRepoService.getAllActiveProducts(pageable);
		}
		for(Product p: productsPage) {
			if(!pictureRepoService.getByProductId(p.getId()).isEmpty()) {				
				p.setMainPicture(pictureRepoService
						.getByProductId(p.getId())
						.get(0).getUrl());
			}else {
				p.setMainPicture("images/noimage.jpg");
			}
		}
		return productsPage;
	}
}
