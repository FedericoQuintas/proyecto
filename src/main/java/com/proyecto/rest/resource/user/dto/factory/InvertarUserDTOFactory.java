package com.proyecto.rest.resource.user.dto.factory;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.user.domain.InvertarUser;

public class InvertarUserDTOFactory {

	public static InvertarUserDTO create(InvertarUser storedUser) {
		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();

		invertarUserDTO.setId(storedUser.getId());

		return invertarUserDTO;
	}

}
