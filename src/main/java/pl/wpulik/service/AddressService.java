package pl.wpulik.service;

import org.springframework.stereotype.Service;

import pl.wpulik.model.Address;

@Service
public class AddressService {
	
	public Address defaultAddress() {
		Address address = new Address("00-000", "brak", "brak", "brak", "brak", "brak", "brak", "brak");		
		return address;
	}
	
	
}
