package com.proyecto.user.service;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;

public interface UserService {

	InvertarUser store(UserDTO userDTO);

}
