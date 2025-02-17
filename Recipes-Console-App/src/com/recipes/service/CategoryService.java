package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.CategoriesModel;

public interface CategoryService {

	public void addCategory(CategoriesModel category);

	public Stream<CategoriesModel> getAllCategory();

	public Stream<CategoriesModel> findByCategory(String name);

	public String updateCategoryName(String oldName, String newName);

	public void deleteCategory(String name);

}
