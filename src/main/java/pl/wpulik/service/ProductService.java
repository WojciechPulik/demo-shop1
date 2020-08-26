package pl.wpulik.service;

import pl.wpulik.dto.ProductDTO;
import pl.wpulik.model.Product;

public class ProductService {
	
	
	public Product productDtoConversion(ProductDTO productDto) {
		Product product = new Product();
		product.setId(productDto.getId());
		/* TO DO */
		return product;
	}

}
