package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Picture;

@Service
public class PictureService {
	
	private PictureRepoService pictureRepoService;
	
	@Autowired
	public PictureService(PictureRepoService pictureRepoService) {
		this.pictureRepoService = pictureRepoService;
	}

	public Picture displayPicture(Long productId) {
		List<Picture> pictures = pictureRepoService.getByProductId(productId);
		Picture picture = new Picture("images/noimage.jpg", "No image");
		if(!pictures.isEmpty())
			picture = pictures.get(0);
		return picture;
	}
}
