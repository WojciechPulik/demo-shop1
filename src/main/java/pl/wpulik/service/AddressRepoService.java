package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Address;
import pl.wpulik.repository.AddressRepository;

@Service
public class AddressRepoService {
	
	private AddressRepository addressRepository;
	
	@Autowired
	public AddressRepoService(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	public Address getById(Long id) {
		return addressRepository.getOne(id);
	}
	
	public Address addAddress(Address address) {
		return addressRepository.save(address);
		
	}
	
	
	
}
