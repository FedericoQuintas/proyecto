package com.proyecto.rest.resource.user.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class InvertarUserLoginDTO {

	@JsonProperty("mail")
	private String mail;

	@JsonProperty("password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
