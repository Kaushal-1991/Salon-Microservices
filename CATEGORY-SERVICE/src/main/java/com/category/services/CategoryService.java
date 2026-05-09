package com.category.services;

import java.util.Set;

import com.category.dto.SalonRequestDto;
import com.category.model.Category;

public interface CategoryService {

	Category saveCategory(Category category , SalonRequestDto salonRequestDto);
	Set<Category> getCategoriesBySalon(Long id);
	Category getCategoryById(Long id);
	void deleteCategoryById(Long id,SalonRequestDto dto);
}
