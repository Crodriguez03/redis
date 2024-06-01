package com.example.redis.cache;

import java.util.Collection;
import java.util.List;

import com.example.redis.dto.UserDTO;

public interface CacheUserService {

	UserDTO findUserFromCache(String id);
	
	List<UserDTO> findUsersFromCacheTemplate(Collection<String> ids);
	
	void invalidateUserFromCache(String id);
	
	void updateUserFromCache(UserDTO user);
	
	void updateUsersFromCache(Collection<UserDTO> users);
}
