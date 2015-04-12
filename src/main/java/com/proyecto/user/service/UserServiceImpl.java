package com.proyecto.user.service;

import org.springframework.stereotype.Service;

import com.proyecto.user.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	public User findById(Long id){
		
		return new User();
	}

}
