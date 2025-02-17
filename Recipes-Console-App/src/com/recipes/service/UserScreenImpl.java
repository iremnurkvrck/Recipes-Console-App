package com.recipes.service;

import java.util.Optional;
import java.util.Scanner;

import com.recipes.menu.SearchMenu;
import com.recipes.model.UserModel;

public class UserScreenImpl extends SearchMenu implements MenuService {

	private Scanner scanner = new Scanner(System.in);
	private ApiUserServiceImpl userServiceImpl = new ApiUserServiceImpl();
	private RecipesServiceImpl recipesService = new RecipesServiceImpl();

	@Override
	public void loginScreen() {
		System.out.println("\n--- Login ---");

		System.out.print("Enter your username: ");
		String username = scanner.nextLine();

		System.out.print("Enter your password: ");
		String password = scanner.nextLine();

		Optional<UserModel> user = userServiceImpl.findByUsername(username)
				.filter(u -> u.getPassword().equals(password)).findFirst();

		if (user.isPresent()) {
			System.out.println("Login successful!");
			showMainMenu();
		} else {
			System.out.println("Invalid username or password. Please try again.");
		}
	}

	public void registerUser() {
		System.out.println("\n--- Registration ---");

		System.out.print("Enter new username: ");
		String newUsername = scanner.nextLine();
		System.out.print("Enter new password: ");
		String newPassword = scanner.nextLine();

		UserModel user = new UserModel(newUsername, newPassword);
		userServiceImpl.addUser(user);

		System.out.println("Registration successful! You can now login.");
		loginScreen();
	}

	@Override
	public void showMainMenu() {
		while (true) {
			System.out.println("\n=======================================");
			System.out.println("  Welcome to the Recipes App  ");
			System.out.println("=======================================");
			System.out.println("1. View All Recipes");
			System.out.println("2. Search Recipes");
			System.out.println("3. Search Category");
			System.out.println("4. Logout");
			System.out.println("=======================================");
			System.out.print("Please select an option (1-4): ");

			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				viewRecipes(recipesService.getAllRecipes());
				break;
			case 2:
				searchRecipe();
				break;
			case 3:
				searchCategory();
				break;
			case 4:
				System.out.println("Logging out... See you next time!");
				return;
			default:
				System.out.println("Invalid choice. Please enter a number between 1-4.");
			}
		}
	}

}