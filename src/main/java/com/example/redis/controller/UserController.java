package com.example.redis.controller;

import java.util.List;
import java.util.Set;

import com.example.redis.dto.UserDTO;

public interface UserController {

	UserDTO findUser(String userId);

	Set<UserDTO> findByIds(List<String> userIds);

	List<UserDTO> findByIdsOrdered(List<String> userIds);

	void deleteUser(String userId);

	UserDTO updateUser(UserDTO user);
}
