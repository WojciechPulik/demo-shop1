package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Address;
import pl.wpulik.repository.AddressRepository;

@Service
public class AddressRepoService {
	
	private AddressRepository addresRepository;
	
	@Autowired
	public AddressRepoService(AddressRepository addresRepository) {
		this.addresRepository = addresRepository;
	}
	
	public Address getById(Long id) {
		return addresRepository.getOne(id);
	}
	
	public Address addAddress(Address address) {
		return addresRepository.save(address);
		
	}
	
	
}
