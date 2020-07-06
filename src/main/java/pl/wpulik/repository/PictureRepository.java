package pl.wpulik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wpulik.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long>{
	
	List<Picture> getByProductId(Long productId);
	
}