package pl.wpulik.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.wpulik.model.Picture;
import pl.wpulik.model.Product;
import pl.wpulik.repository.PictureRepository;
import pl.wpulik.repository.ProductRepository;
import pl.wpulik.utils.FileStorageService;

@Service
public class PictureService {
	
	private PictureRepoService pictureRepoService;
	private ProductRepoService productRepoService;
	private FileStorageService fileStorageService;
	private PictureRepository pictureRepository;
	private ProductRepository productRepository;

	
	@Autowired
	public PictureService(PictureRepoService pictureRepoService, ProductRepoService productRepoService,
			FileStorageService fileStorageService, PictureRepository pictureRepository,
			ProductRepository productRepository) {
		this.pictureRepoService = pictureRepoService;
		this.productRepoService = productRepoService;
		this.fileStorageService = fileStorageService;
		this.pictureRepository = pictureRepository;
		this.productRepository = productRepository;
	}	

	public Picture displayPicture(Long productId) {
		List<Picture> pictures = pictureRepoService.getByProductId(productId);
		Picture picture = new Picture("images/noimage.jpg", "No image");
		if(!pictures.isEmpty())
			picture = pictures.get(0);
		return picture;
	}

	public Picture uploadProductPicture(MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String fileUuidName = uuid.toString() + "." + file.getOriginalFilename().replaceAll("(.)*\\.", "");
		Picture picture = new Picture();
		fileStorageService.saveFile(file, fileUuidName);
		picture.setName(file.getOriginalFilename());
		picture.setUrl("images/" + fileUuidName);
		return pictureRepoService.addPicture(picture);
	}
	
	public Product changeMainPictureForProduct(Long productId, Long pictureId) {
		Product product = productRepoService.getById(productId);
		Picture picture = pictureRepoService.getById(pictureId);
		product.setMainPicture(picture.getUrl());
		return productRepoService.updateProduct(product);
	}
	
	public void removePictureFromProduct(Long pictureId, Long productId) {
		Picture picture = pictureRepository.getOne(pictureId);
		Product product = productRepository.getOne(productId);
		if(picture.getUrl().equals(product.getMainPicture())) {
			product.setMainPicture(null);
			productRepository.save(product);
		}	
		pictureRepoService.removePictureById(pictureId);
		
	}
}
