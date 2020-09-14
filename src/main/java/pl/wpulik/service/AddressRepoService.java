package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Address;
import pl.wpulik.repository.AddressRepository;
import pl.wpulik.repository.OrderRepository;

@Service
public class AddressRepoService {
	
	private OrderRepository orderRepository;
	private AddressRepository addresRepository;
	
	@Autowired
	public AddressRepoService(OrderRepository orderRepository, AddressRepository addresRepository) {
		this.orderRepository = orderRepository;
		this.addresRepository = addresRepository;
	}
	
	public Address getById(Long id) {
		return addresRepository.getOne(id);
	}
	
	public Address addAddress(Address address) {
		Address addressToSave = addresRepository.save(address);
		return addressToSave;
	}
	
	
}
