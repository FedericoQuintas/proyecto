package com.proyecto.user.domain.factory;

import com.proyecto.user.domain.InvertarUser;

public class InvertarUserFactory {

	public static InvertarUser create(Long userId, String username, String mail) {

		InvertarUser invertarUser = new InvertarUser(userId);
		invertarUser.setUsername(username);
		invertarUser.setMail(mail);
		return invertarUser;
	}
}
