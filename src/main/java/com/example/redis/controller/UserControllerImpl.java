package com.example.redis.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.redis.dto.UserDTO;
import com.example.redis.service.UserService;

@RestController
@RequestMapping("user")
public class UserControllerImpl implements UserController {

	private UserService userService;
	
	
	public UserControllerImpl(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	@GetMapping("{userId}")
	public UserDTO findUser(@PathVariable String userId) {
		return userService.findUser(userId);
	}
	
	@Override
	@PostMapping("findByIds")
	public Set<UserDTO> findByIds(@RequestBody List<String> userIds) {
		return userService.findByIds(userIds);
	}
	
	@Override
	@PostMapping("findByIdsOrdered")
	public List<UserDTO> findByIdsOrdered(@RequestBody List<String> userIds) {
		return userService.findByIdsOrdered(userIds);
	}
	
	@Override
	@PutMapping
	public UserDTO updateUser(@RequestBody UserDTO user) {
		return userService.updateUser(user);
	}
	
	@Override
	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
	}
}
