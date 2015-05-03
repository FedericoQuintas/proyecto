package com.proyecto.user.persistence;

import org.springframework.stereotype.Repository;

import com.proyecto.user.domain.InvertarUser;

@Repository("userMemoryRepository")
public class UserMemoryRepository {

	public InvertarUser store() {
		return new InvertarUser();
	}

}
