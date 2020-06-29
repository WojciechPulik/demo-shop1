package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Picture;
import pl.wpulik.repository.PictureRepository;

@Transactional
@Service
public class PictureService {
	
	private PictureRepository pictureRepository;
	
	@Autowired
	public PictureService(PictureRepository pictureRepository) {
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
	
	
	

}
