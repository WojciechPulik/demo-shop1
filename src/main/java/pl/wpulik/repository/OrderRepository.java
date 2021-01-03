package pl.wpulik.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	@Query("SELECT o FROM orders o ORDER BY id_order DESC")
	Page<Order> findAllByIdDesc(Pageable pageable);
	
	@Query("SELECT o FROM orders o WHERE user_id=:userId ORDER BY id_order DESC")
	Page<Order> findAllByUserId(Pageable pageable, @Param("userId")Long userId);

}
