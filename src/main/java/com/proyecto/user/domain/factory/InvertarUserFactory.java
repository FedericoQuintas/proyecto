package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.UserDTO;
import com.proyecto.user.domain.InvertarUser;

public class InvertarUserFactory {

	public static InvertarUser create(UserDTO userDTO) {
		
		
		
		return new InvertarUser();
	}

}
