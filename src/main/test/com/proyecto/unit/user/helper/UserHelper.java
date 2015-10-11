package com.proyecto.unit.user.helper;

import com.proyecto.rest.resource.user.dto.InvertarUserDTO;
import com.proyecto.rest.resource.user.dto.InvertarUserLoginDTO;

public class UserHelper {

	public static InvertarUserDTO createAdminLoginDTO() {

		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();
		invertarUserDTO.setMail("admin@invertar.com");
		invertarUserDTO.setPassword("admin");
		invertarUserDTO.setUsername("Admin");
		
		return invertarUserDTO;
	}
	
	public static InvertarUserDTO createDefaultUserDTO() {
		InvertarUserDTO invertarUserDTO = new InvertarUserDTO();
		invertarUserDTO.setUsername("Default Name");
		invertarUserDTO.setMail("default@mail.com");
		invertarUserDTO.setPassword("defaultpassword");
		return invertarUserDTO;
	}

	public static InvertarUserLoginDTO createDefaultLoginDTO() {

		InvertarUserLoginDTO invertarUserLoginDTO = new InvertarUserLoginDTO();
		invertarUserLoginDTO.setMail("default@mail.com");
		invertarUserLoginDTO.setPassword("defaultpassword");

		return invertarUserLoginDTO;
	}

}
