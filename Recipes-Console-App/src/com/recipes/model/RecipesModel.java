package com.recipes.model;

public class RecipesModel {

	private int id;
	private String title;
	private String description;
	private String ingredients;
	private String category;
	private int cookingTime;

	public RecipesModel(String title, String description, String ingredients, String category, int cookingTime) {
		super();
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
		this.category = category;
		this.cookingTime = cookingTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

}
