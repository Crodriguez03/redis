package com.example.redis.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.redis.dto.UserDTO;

@Repository
public class UserRepositoryImpl implements UserRepository {

	
	// dummy class
	@Override
	public UserDTO findById(String userId) {
		return createUser(userId);
	}

	@Override
	public List<UserDTO> findByIds(Set<String> notfounds) {
		return notfounds.parallelStream().map(this::createUser).toList();
	}
	
	private UserDTO createUser(String userId) {
		return new UserDTO(userId, "name_" + userId, "surname_" + userId);
	}

	@Override
	public void delete(String userId) {		
	}

	@Override
	public void updateUser(UserDTO user) {
	}
}
