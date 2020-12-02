package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.repository.PictureRepository;
import pl.wpulik.repository.ProductRepository;

@Transactional
@Service
public class PictureRepoService {
	
	private PictureRepository pictureRepository;
	private ProductRepository productRepository;
	
	@Autowired
	public PictureRepoService(PictureRepository pictureRepository, ProductRepository productRepository) {
		this.pictureRepository = pictureRepository;
		this.productRepository = productRepository;
	}
	
	public Picture getById(Long id) {
		Picture picture = pictureRepository.findById(id).get();
		return picture;
	}
	
	public Picture addPicture(Picture picture) {
		pictureRepository.save(picture);
		return picture;		
	}
	
	public void removePictureById(Long pictureId) {
		pictureRepository.deleteById(pictureId);
	}
	
	public List<Picture> getByProductId(Long productId){
		List<Picture> pictures = pictureRepository.getByProductId(productId);
		return pictures;
	}
	
	public List<Picture> getAllPictures(){
		List<Picture> pictures = pictureRepository.findAll();
		return pictures;
	}
	
	
	

}
