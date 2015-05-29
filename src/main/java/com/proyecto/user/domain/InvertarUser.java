package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyecto.portfolio.domain.Portfolio;
import com.proyecto.userasset.domain.UserAsset;

public class InvertarUser {

	private Long id;
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();
	private String mail;
	private String name;
	private Date dateOfBirth;


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public InvertarUser(Long userId) {
		this.id = userId;
	}

	public Long getId() {
		return this.id;
	}

	public List<Portfolio> getPortfolios() {
		return this.portfolios;
	}

}
