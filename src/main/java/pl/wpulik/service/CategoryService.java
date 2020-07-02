package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Category;
import pl.wpulik.model.Shipment;
import pl.wpulik.repository.CategoryRepository;
import pl.wpulik.repository.ShipmentRepository;

@Transactional
@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	private ShipmentRepository shipmentRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository, ShipmentRepository shipmentRepository) {
		this.categoryRepository = categoryRepository;
		this.shipmentRepository = shipmentRepository;
	}
	
	public Category addCategory(Category category) {
		categoryRepository.save(category);
		return category;
	}
	
	public Category getById(Long id) {
		Category category = categoryRepository.findById(id).get();
		return category;
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public void addCategoryToShipment(Long shipmentId, List<Category> categories) {
		Shipment shipment = shipmentRepository.findById(shipmentId).get();
		for(Category cat: categories)
			shipment.addCategories(cat);
		shipment = shipmentRepository.save(shipment);
		System.out.println(shipment.toString());
	}
	
	
	
	
	
	

}
