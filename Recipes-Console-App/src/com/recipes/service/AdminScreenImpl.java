package com.recipes.service;

import java.util.Scanner;
import java.util.stream.Stream;

import com.recipes.menu.SearchMenu;
import com.recipes.model.CategoriesModel;
import com.recipes.model.RecipesModel;

public class AdminScreenImpl extends SearchMenu implements MenuService {

	private Scanner scanner = new Scanner(System.in);
	private RecipesServiceImpl recipesService = new RecipesServiceImpl();
	private CategoryServiceImpl categoryServiceImpl = new CategoryServiceImpl();

	@Override
	public void loginScreen() {
		String adminUsername = "admin";
		String adminPassword = "password123";

		System.out.print("Username: ");
		String inputUsername = scanner.nextLine();

		System.out.print("Password: ");
		String inputPassword = scanner.nextLine();

		if (inputUsername.equals(adminUsername) && inputPassword.equals(adminPassword)) {
			System.out.println("Admin login successful!");
			showMainMenu();
		} else {
			System.out.println("Incorrect username or password!");
		}
	}

	@Override
	public void showMainMenu() {
		while (true) {
			System.out.println("\n=======================================");
			System.out.println("  Welcome to the Recipes App  ");
			System.out.println("=======================================");
			System.out.println("1. View All Recipes");
			System.out.println("2. Add a New Recipe");
			System.out.println("3. Search Recipes");
			System.out.println("4. Search Category");
			System.out.println("5. Delete Recipes");
			System.out.println("6. Update Category Name");
			System.out.println("7. Logout");
			System.out.println("=======================================");
			System.out.print("Please select an option (1-6): ");

			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				viewRecipes(recipesService.getAllRecipes());
				break;
			case 2:
				addRecipe();
				break;
			case 3:
				searchRecipe();
				break;
			case 4:
				searchCategory();
				break;
			case 5:
				deleteRecipe();
				break;
				
			case 6:
				updateCategoryName();
				break;
			case 7:
				System.out.println("Logging out... See you next time! ");
				return;
			default:
				System.out.println(" Invalid choice. Please enter a number between 1-6.");
			}
		}
	}

	private void addRecipe() {
		System.out.print("Enter recipe title: ");
		String title = scanner.nextLine();

		System.out.print("Enter category: ");
		String category = scanner.nextLine();

		System.out.print("Enter description: ");
		String description = scanner.nextLine();

		System.out.print("Enter ingredients (comma-separated): ");
		String ingredients = scanner.nextLine();

		System.out.print("Enter cooking time (in minutes): ");
		int cookingTime = scanner.nextInt();
		scanner.nextLine();

		RecipesModel newRecipe = new RecipesModel(title, description, ingredients, category, cookingTime);

		Stream<CategoriesModel> existingCategory = categoryServiceImpl.findByCategory(category);
		if (!existingCategory.findFirst().isPresent()) {
			CategoriesModel newCategoriesModel = new CategoriesModel(category);
			categoryServiceImpl.addCategory(newCategoriesModel);
			System.out.println("Category added to the database.");
		}

		recipesService.addRecipes(newRecipe);

		System.out.println("\nRecipe added successfully!");
	}

	private void deleteRecipe() {
		System.out.println("Enter the name of the recipe you want to delete: ");
		String searchQuery = scanner.nextLine();

		recipesService.deleteRecipes(searchQuery);

		System.out.println("Recipe deleted");

	}
	
	private void updateCategoryName() {
	    System.out.println("Enter the name of the category name: ");
	    String oldName = scanner.nextLine();
	    
	    System.out.println("Enter the name of the new category name: ");
	    String newName = scanner.nextLine();

	    String updatedName = categoryServiceImpl.updateCategoryName(oldName, newName);
	    
	    if (updatedName != null) {
	        System.out.println("New category name is: " + updatedName);
	    }
	}
	

}
