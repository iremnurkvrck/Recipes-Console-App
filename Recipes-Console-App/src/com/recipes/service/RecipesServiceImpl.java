package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.RecipesModel;
import com.recipes.repository.RecipeRepository;

public class RecipesServiceImpl implements RecipesService{

	private RecipeRepository recipeRepository = new RecipeRepository();
	
	@Override
	public void addRecipes(RecipesModel recipesModel) {

		recipeRepository.save(recipesModel);
		
	}

	@Override
	public Stream<RecipesModel> getAllRecipes() {
		
		return recipeRepository.findAll();
	}

	@Override
	public Stream<RecipesModel> findByTitle(String title) {

		return recipeRepository.findByTitle(title);
	}

	@Override
	public void deleteRecipes(String title) {

		recipeRepository.deleteByTitle(title);
	}

	@Override
	public Stream<RecipesModel> findByCategory(String category) {

		return recipeRepository.findByCategory(category);
	}

}
