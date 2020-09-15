package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Address;

@Service
public class AddressService {
	
	private AddressRepoService addresRepoService;
	
	@Autowired
	public AddressService(AddressRepoService addresRepoService) {
		this.addresRepoService = addresRepoService;
	}
	
	public Address defaultAddress() {
		Address address = new Address("00-000", "brak", "brak", "brak", "brak", "brak", "brak", "brak");		
		return address;
	}
	
	
}
