package pl.wpulik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("SELECT o FROM orders o Order BY id_order DESC")
	Page<Order> findAllByIdDesc(Pageable pageable);

}
