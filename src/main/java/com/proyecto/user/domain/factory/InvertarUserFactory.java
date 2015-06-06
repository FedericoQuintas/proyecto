package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.user.domain.InvertarUser;

public class InvertarUserFactory {

	public static InvertarUser create(InvertarUserDTO userDTO, Long userId) {
		
		
		return new InvertarUser(userId);
	}

}
