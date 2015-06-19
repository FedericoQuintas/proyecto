package com.proyecto.rest.resource.user.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class InvertarUserDTO {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("portfolios")
	private List<PortfolioDTO> portfolios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PortfolioDTO> getPortfolios() {
		return this.portfolios;
	}

	public void setPortfolios(List<PortfolioDTO> portfoliosDTO) {
		this.portfolios = portfoliosDTO;
	}
}
