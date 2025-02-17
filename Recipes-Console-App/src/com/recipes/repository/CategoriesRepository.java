package com.recipes.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.recipes.database.DatabaseConnection;
import com.recipes.model.CategoriesModel;

public class CategoriesRepository {

	private Connection connection = DatabaseConnection.getConnection();

	public void categorySave(CategoriesModel category) {
		String query = "INSERT INTO categories (name) VALUES (?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, category.getName());
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<CategoriesModel> findAll() {
		String query = "SELECT id, name FROM categories";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			return getCategoryStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<CategoriesModel> findCategoryName(String name) {
		String query = "SELECT id, name FROM categories WHERE name = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			return getCategoryStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String updateCategoryName(String oldName, String newName) {
		String updatedName = null;
		String updateCategoryQuery = "UPDATE categories SET name = ? WHERE name = ?";
		String updateRecipesQuery = "UPDATE recipes SET category = ? WHERE category = ?";

		try {
			connection.setAutoCommit(false);

			try (PreparedStatement categoryStatement = connection.prepareStatement(updateCategoryQuery)) {
				categoryStatement.setString(1, newName);
				categoryStatement.setString(2, oldName);
				int affectedCategories = categoryStatement.executeUpdate();

				if (affectedCategories == 0) {
					connection.rollback();
					return "No matching category found in categories table";
				}
			}

			try (PreparedStatement recipeStatement = connection.prepareStatement(updateRecipesQuery)) {
				recipeStatement.setString(1, newName);
				recipeStatement.setString(2, oldName);
				recipeStatement.executeUpdate();
			}

			connection.commit();
			updatedName = newName;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			updatedName = "Error updating category: " + e.getMessage();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException autoCommitEx) {
				autoCommitEx.printStackTrace();
			}
		}

		return updatedName;
	}

	public void categoryDeleteByName(String name) {
		String query = "DELETE FROM categories WHERE name = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Stream<CategoriesModel> getCategoryStream(PreparedStatement statement) throws SQLException {
		List<CategoriesModel> categoryResult = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			categoryResult.add(getCategoryFromResultSet(resultSet));
		}
		return categoryResult.stream();
	}

	private CategoriesModel getCategoryFromResultSet(ResultSet resultSet) {
		try {
			CategoriesModel category = new CategoriesModel(resultSet.getString("name"));
			category.setId(resultSet.getInt("id"));
			return category;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}