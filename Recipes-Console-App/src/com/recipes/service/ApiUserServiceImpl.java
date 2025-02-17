package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.UserModel;
import com.recipes.repository.UserRepository;

public class ApiUserServiceImpl implements ApiUserService {

	UserRepository userRepository = new UserRepository();

	@Override
	public void addUser(UserModel user) {

		userRepository.save(user);
	}

	@Override
	public Stream<UserModel> getAllUser() {

		return userRepository.findAll();
	}

	@Override
	public Stream<UserModel> findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

}
