package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{

}
