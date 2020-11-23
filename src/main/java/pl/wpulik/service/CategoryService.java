package pl.wpulik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wpulik.model.Category;

@Service
public class CategoryService {
	
	private CategoryRepoService categoryRepoService;

	@Autowired
	public CategoryService(CategoryRepoService categoryRepoService) {
		this.categoryRepoService = categoryRepoService;
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
