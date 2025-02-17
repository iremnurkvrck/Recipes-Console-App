package com.recipes.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.recipes.database.DatabaseConnection;
import com.recipes.model.UserModel;

public class UserRepository {

	private Connection connection = DatabaseConnection.getConnection();

	public void save(UserModel user) {
		String query = "INSERT INTO users (username, password) VALUES (?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<UserModel> findByUsername(String username) {
		String query = "SELECT id, username, password FROM users WHERE username = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, username);
			return getUserStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Stream<UserModel> findAll() {

		String query = "SELECT id, name FROM users";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			return getUserStream(statement);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Stream<UserModel> getUserStream(PreparedStatement statement) throws SQLException {
		List<UserModel> resultUser = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			resultUser.add(getUserFromResultSet(resultSet));
		}

		return resultUser.stream();
	}

	private UserModel getUserFromResultSet(ResultSet resultSet) {

		try {
			UserModel user = new UserModel(resultSet.getString("username"), resultSet.getString("password"));

			user.setId(resultSet.getInt("id"));
			return user;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
