package com.example.redis.repository;

import java.util.List;
import java.util.Set;

import com.example.redis.dto.UserDTO;

public interface UserRepository {

	UserDTO findById(String userId);

	List<UserDTO> findByIds(Set<String> notfounds);

	void delete(String userId);

	void updateUser(UserDTO user);

}
