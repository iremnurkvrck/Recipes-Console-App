package com.recipes.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.recipes.model.CategoriesModel;
import com.recipes.model.RecipesModel;
import com.recipes.service.CategoryServiceImpl;
import com.recipes.service.RecipesServiceImpl;

public abstract class SearchMenu {

	private Scanner scanner = new Scanner(System.in);
	private RecipesServiceImpl recipesService = new RecipesServiceImpl();
	private CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();

	protected void viewRecipes(Stream<RecipesModel> recipes) {
		recipes.forEach(recipe -> {
			System.out.println("\nTitle: " + recipe.getTitle());
			System.out.println("Category: " + recipe.getCategory());
			System.out.println("Description: " + recipe.getDescription());
			System.out.println("Ingredients: " + recipe.getIngredients());
			System.out.println("Cooking Time: " + recipe.getCookingTime() + " minutes");
			System.out.println("-----------------------------------");
		});
	}

	protected void searchRecipe() {
		System.out.print("Enter recipe title: ");
		String searchQuery = scanner.nextLine().toLowerCase();

		Stream<RecipesModel> foundRecipes = recipesService.findByTitle(searchQuery)
				.filter(recipe -> recipe.getTitle().toLowerCase().contains(searchQuery));

		foundRecipes.forEach(recipe -> {
			System.out.println("\nTitle: " + recipe.getTitle());
			System.out.println("Description: " + recipe.getDescription());
			System.out.println("Ingredients: " + recipe.getIngredients());
			System.out.println("Cooking Time: " + recipe.getCookingTime() + " minutes");
			System.out.println("-----------------------------------");
		});
	}

	protected void searchCategory() {
		System.out.println("\nAll Categories:");

		Stream<CategoriesModel> allCategories = categoryServiceImpl.getAllCategory();
		List<CategoriesModel> categoriesList = new ArrayList<>();
		allCategories.forEach(cat -> {
			categoriesList.add(cat);
			System.out.println(cat.getName());
		});

		System.out.print("\nEnter category name: ");
		String searchQuery = scanner.nextLine().toLowerCase();

		Optional<CategoriesModel> selectedCategory = categoriesList.stream()
				.filter(category -> category.getName().toLowerCase().equals(searchQuery)).findFirst();

		if (selectedCategory.isPresent()) {
			System.out.println("\nRecipes in " + selectedCategory.get().getName() + " category:");

			Stream<RecipesModel> recipesInCategory = recipesService.findByCategory(selectedCategory.get().getName());

			List<RecipesModel> recipesList = recipesInCategory.collect(Collectors.toList());

			if (!recipesList.isEmpty()) {
				viewRecipes(recipesList.stream());
			} else {
				System.out.println("No recipes found in this category.");
			}
		} else {
			System.out.println("Category not found. Please try again.");
		}
	}
}