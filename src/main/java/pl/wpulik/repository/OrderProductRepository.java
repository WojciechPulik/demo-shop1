package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.wpulik.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>{

}
