package pl.wpulik.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	@Query("SELECT c FROM categories c WHERE c.overridingCategoryId = :categoryId")
	List<Category> getAllMainCategories(@Param("categoryId") Long categoryId);

}
