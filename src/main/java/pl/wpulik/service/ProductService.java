package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Product;

@Service
public class ProductService {
	
	private ProducerRepoService producerRepoService;
	private ShipmentRepoService shipmentRepoService;
	private CategoryRepoService categoryRepoService;
	private ProductRepoService productRepoService;
	
	@Autowired
	public ProductService(ProducerRepoService producerRepoService, ShipmentRepoService shipmentRepoService,
			CategoryRepoService categoryRepoService, ProductRepoService productRepoService) {
		this.producerRepoService = producerRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.categoryRepoService = categoryRepoService;
		this.productRepoService = productRepoService;
	}

	public ProductService() {}
	
	public ProductDTO createProductDTO() {
		ProductDTO productDto = new ProductDTO();
		productDto.setProducers(producerRepoService.getAllProducers());
		productDto.setShipments(shipmentRepoService.getAllShipments());
		productDto.setCategories(categoryRepoService.getAllCategories());
		return productDto;
	}
	
	public Product productMapping(ProductDTO productDto) {
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setQuantity(productDto.getQuantity());
		product.setPrice(productDto.getPrice());
		product.setIndex(productDto.getIndex());
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
	
	public List<Product> findByNameFragment(String phrase){
		String startWithOut = phrase + "%";
		String insideWithOut = "%" + phrase + "%";
		return productRepoService.findByNameFragment(startWithOut, insideWithOut);
	}
	

}
