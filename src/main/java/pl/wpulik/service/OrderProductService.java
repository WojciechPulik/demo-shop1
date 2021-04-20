package pl.wpulik.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Order;
import pl.wpulik.model.OrderProduct;
import pl.wpulik.model.Product;

@Service
public class OrderProductService {
	
	private OrderProductRepoService orderProductRepoService;

	public OrderProductService() {}

	@Autowired
	public OrderProductService(OrderProductRepoService orderProductRepoService) {
		this.orderProductRepoService = orderProductRepoService;
	}
	
	public List<OrderProduct> orderProductMapping(List<Product> products){	
		return products
				.stream()
				.map(this::productMapping)
				.collect(Collectors.toList());
	}
	
	public List<OrderProduct> saveOrderProducts(List<OrderProduct> orderProducts, Order order) {
		orderProducts.forEach(op -> {
				op.setOrder(order);
				});
		return orderProducts.stream()
			.map(orderProductRepoService::save)
			.collect(Collectors.toList());
			
	}
	
	private OrderProduct productMapping (Product product) {
		OrderProduct resultProduct = new OrderProduct();
		resultProduct.setProductId(product.getId());
		resultProduct.setIndex(product.getIndex());
		resultProduct.setName(product.getName());
		resultProduct.setPrice(product.getPrice());
		resultProduct.setAddedQuantity(product.getAddedQuantity());
		resultProduct.setSummaryCost(Math
				.round(resultProduct.getAddedQuantity() * resultProduct.getPrice() * 100)/100.0);
		if(product.getDiscount() != null)
			resultProduct.setDiscount(product.getDiscount());
		return resultProduct;
	}
	
	
	
	

}
