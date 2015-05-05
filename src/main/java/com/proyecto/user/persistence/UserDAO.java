package com.proyecto.user.persistence;

import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.user.domain.InvertarUser;

public interface UserDAO {

	public InvertarUser store(InvertarUser user);

	public InvertarUser findById(Long id) throws ObjectNotFoundException;

	public Long nextID();

}
