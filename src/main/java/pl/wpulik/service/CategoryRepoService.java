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
	private List<Category> categoriesTree = new ArrayList<>();
	
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
		Category categoryToSetHaveSubcategory = getById(category.getOverridingCategoryId());
		categoryToSetHaveSubcategory.setHaveSubcategory(true);
		categoryRepository.save(categoryToSetHaveSubcategory);
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
	
	public List<Category> getCategoriesTreeForCategory(Long categoryId){
		List <Category> catTree = getCategoriesTree(categoryId);
		List <Category> reversedCatTree = new ArrayList<>();
		for(int i = catTree.size() - 1; i >= 0; i--) {
			reversedCatTree.add(catTree.get(i));
		}
		clearCategoriesTree();
		return reversedCatTree;
		
	}
	
	public Page<Product> getAllActiveProductsByCategory(Pageable pageable, Long categoryId){
		return productRepository.findAllActiveByCategory(pageable, categoryId);
	}
	
	public void addCategoryToShipment(Long shipmentId, List<Category> categories) {
		Shipment shipment = shipmentRepository.findById(shipmentId).get();
		for(Category cat: categories)
			shipment.addCategories(cat);
		shipment = shipmentRepository.save(shipment);
	}
	
	public List<Category> allRelatedCategories(Long categoryId){
		Category category = categoryRepository.findById(categoryId).get();
		resultRelatedList.add(category);
		if(category.getOverridingCategoryId() != 0) 
			allRelatedCategories(category.getOverridingCategoryId()); //Recursion
		return resultRelatedList;
	}
	
	private List<Category> getCategoriesTree(Long categoryId){	
		Category category = getById(categoryId);
		//if(category.getHaveSubcategory())
			category.setSubcategories(categoryRepository.getAllMainCategories(categoryId));
		categoriesTree.add(category);
		if(category.getOverridingCategoryId() != 0)
			return getCategoriesTree(category.getOverridingCategoryId()); //Recursion
		return categoriesTree;
	}
	
	private void clearResultRelatedList() {
		resultRelatedList = new ArrayList<>();
	}	
	
	private void clearCategoriesTree() {
		categoriesTree = new ArrayList<>();
	}
	
	
	
	

}
