package com.proyecto.user.domain.factory;

import com.proyecto.user.domain.InvertarUser;

public class InvertarUserFactory {

	public static InvertarUser create(Long userId) {

		return new InvertarUser(userId);
	}

}
