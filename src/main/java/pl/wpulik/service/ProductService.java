package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Product;

@Service
public class ProductService {
	
	private ProducerRepoService producerRepoService;
	private ShipmentRepoService shipmentRepoService;
	private CategoryRepoService categoryRepoService;
	
	@Autowired
	public ProductService(ProducerRepoService producerRepoService, ShipmentRepoService shipmentRepoService,
			CategoryRepoService categoryRepoService) {
		this.producerRepoService = producerRepoService;
		this.shipmentRepoService = shipmentRepoService;
		this.categoryRepoService = categoryRepoService;
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
	
	

}
