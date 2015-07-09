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

	Long portfoliosSequence = new Long(1);

	@Override
	public InvertarUser store(InvertarUser user) {

		users.add(user);

		return user;
	}

	@Override
	public InvertarUser findById(Long id) throws ObjectNotFoundException {

		for (InvertarUser user : users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		throw new ObjectNotFoundException("User " + id + "not found.");

	}

	@Override
	public Long nextID() {
		return usersSequence++;
	}

	@Override
	public void flush() {
		users.clear();
		usersSequence = new Long(1);

	}

	@Override
	public void update(InvertarUser invertarUser) {
		int positionToRemove = 0;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId().equals(invertarUser.getId())) {
				positionToRemove = i;
			}
		}
		users.remove(positionToRemove);
		users.add(invertarUser);
	}

	@Override
	public Long nextPortfolioID() {
		return portfoliosSequence++;
	}

	@Override
	public void decrementPortfolioID() {
		portfoliosSequence--;

	}

	@Override
	public InvertarUser findByMail(String mail) throws ObjectNotFoundException {

		for (InvertarUser user : users) {
			if (user.getMail().equals(mail)) {
				return user;
			}
		}
		throw new ObjectNotFoundException("User " + mail + "not found.");
	}
}
