package pl.wpulik.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Category;
import pl.wpulik.model.Product;

@Service
public class CategoryService {
	
	private CategoryRepoService categoryRepoService;
	private ProductRepoService productRepoService;

	@Autowired
	public CategoryService(CategoryRepoService categoryRepoService, ProductRepoService productRepoService) {
		this.categoryRepoService = categoryRepoService;
		this.productRepoService = productRepoService;
	}
	
	public Category categoryMapAndUpdate(Category category) {
		Category catToUpdate = categoryRepoService.getById(category.getId());
		long catToUpdateOverriding = catToUpdate.getOverridingCategoryId();
		catToUpdate.setName(category.getName());
		catToUpdate.setDescription(category.getDescription());
		catToUpdate.setOverridingCategoryId(category.getOverridingCategoryId());
		Category resultCategory = categoryRepoService.updateCategory(catToUpdate);
		haveSubCategorySet(category, catToUpdateOverriding);
		return resultCategory;
	}
	
	public void removeFromCategory(Long productId, Long categoryId) {
		Product product = productRepoService.getById(productId);
		Category category = categoryRepoService.getById(categoryId);
		List<Category> categories = product.getCategories(); 
		List<Product> products = category.getProducts(); 
		categories.remove(category);
		products.remove(product);
		product.setCategories(categories);
		category.setProducts(products);
		productRepoService.updateProduct(product);
		categoryRepoService.updateCategory(category);
	}
	
	//checks if overriding category has subcategory and then set right value:
	private void haveSubCategorySet(Category category, long catToUpdateOverriding) {		
		if(category.getOverridingCategoryId() != catToUpdateOverriding 
				&& catToUpdateOverriding != 0
				&& categoryRepoService.categoriesWithOverridingCategory(catToUpdateOverriding).isEmpty()) {
			Category categoryToSubcategorySet = categoryRepoService.getById(catToUpdateOverriding);
			categoryToSubcategorySet.setHaveSubcategory(false);
			categoryRepoService.updateCategory(categoryToSubcategorySet);			
		}
		if(catToUpdateOverriding == 0
				&& categoryRepoService.categoriesWithOverridingCategory(category.getId()).isEmpty()) {
			category.setHaveSubcategory(false);
			categoryRepoService.updateCategory(category);
		}
	}
	
	

}
