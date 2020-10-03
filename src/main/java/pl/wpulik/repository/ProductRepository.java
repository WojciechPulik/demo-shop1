package pl.wpulik.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query("SELECT p FROM products p WHERE p.name LIKE :startWith OR p.name LIKE :insideWith")
	List<Product> findByNameFragment(@Param("startWith")String startWith, @Param("insideWith")String insideWith);
	
	Page<Product> findAll(Pageable pageable);
	
	
}
