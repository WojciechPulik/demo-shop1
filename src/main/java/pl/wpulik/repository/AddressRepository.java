package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	


}
