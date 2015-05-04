package com.proyecto.user.service;

import org.springframework.stereotype.Service;

import com.proyecto.user.domain.InvertarUser;

@Service()
public class UserServiceImpl implements UserService {

	
	@Override
	public InvertarUser store() {
		
		return new InvertarUser();
	}

}
