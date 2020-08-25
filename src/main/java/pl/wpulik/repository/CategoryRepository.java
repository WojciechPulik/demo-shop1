package pl.wpulik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Category;
import pl.wpulik.model.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
