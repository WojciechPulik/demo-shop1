package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.OrderProduct;
import pl.wpulik.repository.OrderProductRepository;

@Service
@Transactional
public class OrderProductRepoService {
	
	private OrderProductRepository orderProductRepository;
	
	@Autowired
	public OrderProductRepoService(OrderProductRepository orderProductRepository) {
		this.orderProductRepository = orderProductRepository;
	}
	
	public OrderProduct save(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}
	
	
	

}
