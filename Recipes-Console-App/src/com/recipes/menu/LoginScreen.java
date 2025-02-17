package com.recipes.menu;

import java.util.Scanner;

import com.recipes.service.AdminScreenImpl;
import com.recipes.service.UserScreenImpl;

public class LoginScreen {

	private Scanner scanner = new Scanner(System.in);
	private UserScreenImpl userScreenImpl = new UserScreenImpl();
	private AdminScreenImpl adminScreenImpl = new AdminScreenImpl();

	public void login() {
		System.out.println("=======================================");
		System.out.println("Welcome to Recipes!");
		System.out.println("=======================================");
		System.out.println("Please choose an option to proceed:");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Admin");
		System.out.println("=======================================");
		System.out.print("Enter your choice (1 or 3): ");

		int choice = scanner.nextInt();
		scanner.nextLine();

		switch (choice) {
		case 1:
			userScreenImpl.loginScreen();
			break;
		case 2:
			userScreenImpl.registerUser();
			break;
		case 3:
			adminScreenImpl.loginScreen();
		default:
			System.out.println("Invalid choice. Please choose '1' for Login or '2' for Register.");
			break;
		}
	}

}
