package pl.wpulik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.wpulik.model.Category;
import pl.wpulik.model.Product;
import pl.wpulik.model.Shipment;
import pl.wpulik.repository.CategoryRepository;
import pl.wpulik.repository.ProductRepository;
import pl.wpulik.repository.ShipmentRepository;

@Transactional
@Service
public class CategoryRepoService {
	
	private List<Category> resultRelatedList = new ArrayList<>();
	private List<Category> resultNodeList = new ArrayList<>(0);
	
	private CategoryRepository categoryRepository;
	private ShipmentRepository shipmentRepository;
	private ProductRepository productRepository;
	
	@Autowired
	public CategoryRepoService(CategoryRepository categoryRepository, ShipmentRepository shipmentRepository,
			ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.shipmentRepository = shipmentRepository;
		this.productRepository = productRepository;
	}
	
	public CategoryRepoService() {}
	
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
	public List<Category> getMainCategories(){
		return categoryRepository.getAllMainCategories(0L);
	}
	
	public List<Category> getAllCategoriesForCategory(Long categoryId){//TODO check if works well
		List<Category> resultList = new ArrayList<>();
		for(Category c : categoryRepository.getAllMainCategories(categoryId)) {
			if(c.getOverridingCategoryId() != 0)
				resultList.add(categoryNodes(c));
		}
		resultList.forEach(System.out::print);
		return resultList;
	}
	
	public Page<Product> getAllActiveProductsByCategory(Pageable pageable, Long categoryId){
		return productRepository.findAllActiveByCategory(pageable, categoryId);
	}
	
	public void addCategoryToShipment(Long shipmentId, List<Category> categories) {
		Shipment shipment = shipmentRepository.findById(shipmentId).get();
		for(Category cat: categories)
			shipment.addCategories(cat);
		shipment = shipmentRepository.save(shipment);
		System.out.println(shipment.toString());
	}
	
	public List<Category> allRelatedCategories(Long categoryId){
		Category category = categoryRepository.findById(categoryId).get();
		resultRelatedList.add(category);
		if(category.getOverridingCategoryId() != 0) 
			allRelatedCategories(category.getOverridingCategoryId()); //Recursion
		return resultRelatedList;
	}
	
	private Category categoryNodes(Category category) {//TODO check if works well; can I make loop easier?
		resultNodeList.add(category);
		if(category.getOverridingCategoryId() != 0) 
			categoryNodes(categoryRepository.findById(category.getOverridingCategoryId()).get()); //Recursion
		//creating category object with nested higher category objects:
		int size = resultNodeList.size();
		Category resultCategory = category;
		if(category.getOverridingCategoryId() != 0) {
			resultCategory = resultNodeList.get(size - 2); 
			resultCategory.setOverridingCategory(resultNodeList.get(size - 1));
			Category tempCategory = new Category();	
			if(size >= 3) {
				for(int i = size - 3; i <= 0 ; i--) {
					tempCategory = resultNodeList.get(i);
					tempCategory.setOverridingCategory(resultCategory);
					resultCategory = tempCategory;		
				}
			}
			System.out.println(resultCategory.getName() + " -> " + resultCategory.getOverridingCategory().getName());
		}
		return resultCategory;
		
	}
	
	
	
	
	
	

}
