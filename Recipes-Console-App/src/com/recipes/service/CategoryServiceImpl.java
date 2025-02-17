package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.CategoriesModel;
import com.recipes.repository.CategoriesRepository;

public class CategoryServiceImpl implements CategoryService{
	
	CategoriesRepository categoryRepository = new CategoriesRepository();

	@Override
	public void addCategory(CategoriesModel category) {

		categoryRepository.categorySave(category);
	}

	@Override
	public Stream<CategoriesModel> getAllCategory() {

		return categoryRepository.findAll();
	}

	@Override
	public Stream<CategoriesModel> findByCategory(String name) {

		return categoryRepository.findCategoryName(name);
	}

	@Override
	public void deleteCategory(String name) {

		categoryRepository.categoryDeleteByName(name);
	}

	@Override
	public String updateCategoryName(String oldName, String newName) {
        String updatedName = categoryRepository.updateCategoryName(oldName, newName);
        
        if (updatedName == null) {
            System.out.println("Kategori güncellenemedi!");
        }
        
        return updatedName;
    }

}