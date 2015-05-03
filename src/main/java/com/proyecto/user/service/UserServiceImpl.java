package com.proyecto.user.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.user.domain.InvertarUser;
import com.proyecto.user.persistence.UserMemoryRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMemoryRepository")
	UserMemoryRepository userRepository;
	
	@Override
	public InvertarUser store() {
		
		return userRepository.store();
	}

}
