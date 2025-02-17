package com.recipes.service;

import java.util.stream.Stream;

import com.recipes.model.UserModel;

public interface ApiUserService {

	public void addUser(UserModel user);

	public Stream<UserModel> getAllUser();

	public Stream<UserModel> findByUsername(String username);
}
