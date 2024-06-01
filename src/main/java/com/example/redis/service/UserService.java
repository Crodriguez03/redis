package com.example.redis.service;

import java.util.List;
import java.util.Set;

import com.example.redis.dto.UserDTO;

public interface UserService {

	UserDTO findUser(String userId);

	Set<UserDTO> findByIds(List<String> userIds);

	List<UserDTO> findByIdsOrdered(List<String> usersIds);

	UserDTO updateUser(UserDTO user);

	void deleteUser(String userId);
}
