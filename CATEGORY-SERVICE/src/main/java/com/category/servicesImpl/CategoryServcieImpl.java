package com.category.servicesImpl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.category.dto.SalonRequestDto;
import com.category.model.Category;
import com.category.repository.CategoryRepository;
import com.category.services.CategoryService;

@Service
public class CategoryServcieImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category saveCategory(Category category, SalonRequestDto salonRequestDto) {
		Category newCategory = new Category();
		newCategory.setImage(category.getImage());
		newCategory.setName(category.getName());
		newCategory.setSalonId(salonRequestDto.getId());
		return categoryRepository.save(newCategory);
	}

	@Override
	public Set<Category> getCategoriesBySalon(Long id) {
		return categoryRepository.findBySalonId(id);
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No id found !!!"));
	}

	@Override
	public void deleteCategoryById(Long id,SalonRequestDto salonRequestDto) {
		Category category = categoryRepository.findById(id).orElseThrow(null);
		if(!category.getSalonId().equals(salonRequestDto.getId())) {
			throw  new RuntimeException("No id found !!!");
		}
		categoryRepository.delete(category);
	}

}
