package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.utils.FileStorageService;

@Service
public class PictureService {
	
	private PictureRepoService pictureRepoService;
	private FileStorageService fileStorageService;
	
	@Autowired
	public PictureService(PictureRepoService pictureRepoService, FileStorageService fileStorageService) {
		this.pictureRepoService = pictureRepoService;
		this.fileStorageService = fileStorageService;
	}

	public Picture displayPicture(Long productId) {
		List<Picture> pictures = pictureRepoService.getByProductId(productId);
		Picture picture = new Picture("images/noimage.jpg", "No image");
		if(!pictures.isEmpty())
			picture = pictures.get(0);
		return picture;
	}
	
	public Picture uploadProductPicture(MultipartFile file) {
		Picture picture = new Picture();
		fileStorageService.saveFile(file);
		picture.setName(file.getOriginalFilename());
		picture.setUrl("images/" + file.getOriginalFilename());
		return pictureRepoService.addPicture(picture);
	}
}
