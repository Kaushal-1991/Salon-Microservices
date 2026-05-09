package com.category.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.category.dto.SalonRequestDto;
import com.category.model.Category;
import com.category.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> createCategory(Category category){
		SalonRequestDto salonRequestDto = new  SalonRequestDto();
		salonRequestDto.setId(1L);
		Category saveCategory = categoryService.saveCategory(category, salonRequestDto);
		return new ResponseEntity<Category>(saveCategory,HttpStatus.CREATED);
	} 
	
	@GetMapping("/salon/{salonId}")
	public ResponseEntity<Set<Category>> getCategoryBySalon(@PathVariable Long salonId){
		Set<Category> category = categoryService.getCategoriesBySalon(salonId);
		return new ResponseEntity<Set<Category>>(category,HttpStatus.OK);
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
		Category category = categoryService.getCategoryById(id);
		return new ResponseEntity<Category>(category,HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		SalonRequestDto salonRequestDto = new  SalonRequestDto();
		salonRequestDto.setId(1L);
		categoryService.deleteCategoryById(id,salonRequestDto);
		return new ResponseEntity<String>("Category Deleted Sucessfully !!!",HttpStatus.OK);
	}
	
	
}
