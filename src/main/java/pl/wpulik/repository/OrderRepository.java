package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
