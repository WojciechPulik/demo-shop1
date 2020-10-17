package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.wpulik.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{
	
	

}
