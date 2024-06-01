package com.example.redis.cache;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.redis.dto.UserDTO;

@Component
public class CacheUserServiceImpl implements CacheUserService {

	private static final String PREFIX_USER = "user:";

	private RedisTemplate<String, UserDTO> redisTemplate;
	
	Random ran = new Random();
	
	public CacheUserServiceImpl(RedisTemplate<String, UserDTO> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public UserDTO findUserFromCache(String id) {
		return redisTemplate.opsForValue().get(PREFIX_USER + id);
	}

	@Override
	public List<UserDTO> findUsersFromCacheTemplate(Collection<String> ids) {
		List<String> keys = ids.stream().map(id -> PREFIX_USER + id).toList();
		return redisTemplate.opsForValue().multiGet(keys);
	}

	@Async
	@Override
	public void updateUserFromCache(UserDTO user) {
		// Seteamos una duración de la cache aleatoria para pruebas
		redisTemplate.opsForValue().set(PREFIX_USER + user.getId(), user, 
				Duration.of(ran.nextInt(5, 50), ChronoUnit.SECONDS));
	}

	@Async
	@Override
	public void updateUsersFromCache(Collection<UserDTO> users) {
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public List<Object> execute(RedisOperations operations) throws DataAccessException {
				operations.multi();
				// Seteamos una duración de la cache aleatoria para pruebas
				users.forEach(user -> operations.opsForValue().set(PREFIX_USER + user.getId(), user, 
						Duration.of(ran.nextInt(5, 50), ChronoUnit.SECONDS)));
				return operations.exec();
			}
		});
	}
	
	@Async
	@Override
	public void invalidateUserFromCache(String id) {
		redisTemplate.delete(PREFIX_USER + id);
	}
}
