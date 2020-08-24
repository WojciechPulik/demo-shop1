package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Picture;
import pl.wpulik.repository.PictureRepository;

@Transactional
@Service
public class PictureRepoService {
	
	private PictureRepository pictureRepository;
	
	@Autowired
	public PictureRepoService(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}
	
	public Picture getById(Long id) {
		Picture picture = pictureRepository.findById(id).get();
		return picture;
	}
	
	public Picture addPicture(Picture picture) {
		pictureRepository.save(picture);
		return picture;		
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
