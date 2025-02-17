package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.RecipesModel;

public interface RecipesService {

	public void addRecipes(RecipesModel recipesModel);

	public Stream<RecipesModel> getAllRecipes();

	public Stream<RecipesModel> findByTitle(String title);

	public Stream<RecipesModel> findByCategory(String category);

	public void deleteRecipes(String title);

}
