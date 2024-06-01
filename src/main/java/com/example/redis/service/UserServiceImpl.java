package com.example.redis.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.redis.cache.CacheUserService;
import com.example.redis.dto.UserDTO;
import com.example.redis.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private final CacheUserService cacheUserService;
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(CacheUserService cacheUserService, UserRepository userRepository) {
		this.cacheUserService = cacheUserService;
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDTO findUser(String userId) {
		UserDTO user = cacheUserService.findUserFromCache(userId);
		if (user == null) {
			log.info("No encontrado user con id {} en cache", userId);
			user = userRepository.findById(userId);
			if (user != null) {
				cacheUserService.updateUserFromCache(user);
			}
		}
		return user;
	}

	@Override
	public Set<UserDTO> findByIds(List<String> userIds) {
		Set<UserDTO> result = ConcurrentHashMap.newKeySet();
		Set<String> notfounds = new HashSet<>();

        List<UserDTO> items = cacheUserService.findUsersFromCacheTemplate(userIds);
        for (int i = 0; i < userIds.size(); i++) {
        	UserDTO item = items.get(i);
        	if(item != null){
                result.add(item);
                log.info("Encontrado user con id {} en cache", item.getId());
            } else {
                notfounds.add(userIds.get(i));
            }
		}

        //los que no encuentra en cache los busca en el repositorio, los añade al resultado y los cachea
        if (!CollectionUtils.isEmpty(notfounds)) {
        	List<UserDTO> notfoundsUsers = userRepository.findByIds(notfounds);
        	if (!CollectionUtils.isEmpty(notfoundsUsers)) {
        		result.addAll(notfoundsUsers);
        		cacheUserService.updateUsersFromCache(notfoundsUsers);
        	}
        }
        return result;
	}
	
	@Override
	public List<UserDTO> findByIdsOrdered(List<String> usersIds) {
		
		Set<UserDTO> users = findByIds(usersIds);
		Map<String, UserDTO> map = users.stream().collect(Collectors.toMap(UserDTO::getId, Function.identity()));
		
		return usersIds.stream().map(map::get).filter(Objects::nonNull).toList();
	}

	@Override
	public UserDTO updateUser(UserDTO user) {
		userRepository.updateUser(user);
		cacheUserService.invalidateUserFromCache(user.getId());
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.delete(userId);
		cacheUserService.invalidateUserFromCache(userId);
	}

}
