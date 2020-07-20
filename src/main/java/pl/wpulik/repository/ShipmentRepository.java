package pl.wpulik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
	
	
}
