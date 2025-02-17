package com.recipes.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.recipes.database.DatabaseConnection;
import com.recipes.model.RecipesModel;

public class RecipeRepository {

	private Connection connection = DatabaseConnection.getConnection();

	public void save(RecipesModel recipesModel) {

		String query = "INSERT INTO recipes (title, description, ingredients, category, cookingTime) VALUES (?, ? ,?, ?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, recipesModel.getTitle());
			statement.setString(2, recipesModel.getDescription());
			statement.setString(3, recipesModel.getIngredients());
			statement.setString(4, recipesModel.getCategory());
			statement.setInt(5, recipesModel.getCookingTime());
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<RecipesModel> findAll() {
		String query = "SELECT id, title, description, ingredients, category, cookingTime FROM recipes";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			return getProductStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<RecipesModel> findByTitle(String title) {
		String query = "SELECT id, title, description, ingredients, category, cookingTime FROM recipes WHERE title = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, title);
			return getProductStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<RecipesModel> findByCategory(String category) {
		String query = "SELECT id, title, description, ingredients, category, cookingTime FROM recipes WHERE category = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, category);
			return getProductStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteByTitle(String title) {
		String query = "DELETE FROM recipes WHERE title = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, title);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Stream<RecipesModel> getProductStream(PreparedStatement statement) throws SQLException {
		List<RecipesModel> result = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			result.add(getRecipesModelFromResultSet(resultSet));
		}
		return result.stream();
	}

	private RecipesModel getRecipesModelFromResultSet(ResultSet resultSet) {
		try {
			RecipesModel recipesModel = new RecipesModel(resultSet.getString("title"),
					resultSet.getString("description"), resultSet.getString("ingredients"),
					resultSet.getString("category"), resultSet.getInt("cookingTime"));
			recipesModel.setId(resultSet.getInt("id"));
			return recipesModel;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
