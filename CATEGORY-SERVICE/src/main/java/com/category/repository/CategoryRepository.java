package com.category.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	Set<Category> findBySalonId(Long id);
}
