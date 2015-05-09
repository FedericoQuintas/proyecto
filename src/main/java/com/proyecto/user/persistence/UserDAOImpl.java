package com.proyecto.user.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.user.domain.InvertarUser;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	List<InvertarUser> users = new ArrayList<InvertarUser>();

	Long usersSequence = new Long(1);

	public InvertarUser store(InvertarUser user) {

		users.add(user);

		return user;
	}

	public InvertarUser findById(Long id) throws ObjectNotFoundException {

		for (InvertarUser user : users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		throw new ObjectNotFoundException("User" + id + "not found.");

	}

	@Override
	public Long nextID() {
		return usersSequence++;
	}

	@Override
	public void flush() {
		users.clear();

	}
}
