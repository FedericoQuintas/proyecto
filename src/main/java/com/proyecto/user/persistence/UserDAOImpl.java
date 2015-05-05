package com.proyecto.user.persistence;

import org.springframework.stereotype.Repository;

import com.proyecto.user.domain.InvertarUser;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	public InvertarUser store(InvertarUser user) {
		return new InvertarUser();
	}

}
