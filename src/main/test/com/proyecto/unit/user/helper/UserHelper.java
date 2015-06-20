package com.proyecto.unit.user.helper;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;

public class UserHelper {

	public static InvertarUserDTO createDefaultUserDTO() {
		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();
		invertarUserDTO.setUsername("Default Name");
		invertarUserDTO.setMail("default@mail.com");
		return invertarUserDTO;
	}

}
