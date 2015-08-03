package com.proyecto.user.persistence;

import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.common.persistence.GenericDAO;
import com.proyecto.user.domain.InvertarUser;

public interface UserDAO extends GenericDAO {

	public InvertarUser store(InvertarUser user);

	public InvertarUser findById(Long id) throws ObjectNotFoundException;

	public void update(InvertarUser invertarUser);

	public Long nextPortfolioID();

	public InvertarUser findByMail(String mail) throws ObjectNotFoundException;

	public InvertarUser findByUsername(String username)
			throws ObjectNotFoundException;

	public Boolean existsUserWithMail(String mail);

	public Boolean existsUserWithUsername(String username);

}
