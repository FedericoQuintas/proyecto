package com.proyecto.user.domain.factory;

import com.proyecto.user.domain.InvertarUser;

public class InvertarUserFactory {

	public static InvertarUser create(Long userId, String username,
			String mail, String password) {

		InvertarUser invertarUser = new InvertarUser(userId);
		invertarUser.setUsername(username);
		invertarUser.setMail(mail);
		invertarUser.setPassword(password);
		return invertarUser;
	}
}
