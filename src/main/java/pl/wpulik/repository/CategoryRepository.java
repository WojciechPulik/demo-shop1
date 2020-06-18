package pl.wpulik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
