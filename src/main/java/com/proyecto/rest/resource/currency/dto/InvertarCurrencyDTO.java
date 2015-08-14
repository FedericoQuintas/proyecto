package com.proyecto.rest.resource.currency.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.currency.domain.ExchangeSession;

public class InvertarCurrencyDTO {

	@JsonProperty("code")
	private InvertarCurrencyCode code;
	@JsonProperty("exchange_sessions")
	private List<ExchangeSessionDTO> exchangeSessions;

	public InvertarCurrencyCode getCode() {
		return code;
	}

	public void setCode(InvertarCurrencyCode code) {
		this.code = code;
	}

	public List<ExchangeSessionDTO> getExchangeSessions() {
		return exchangeSessions;
	}

	public void setExchangeSessions(List<ExchangeSessionDTO> exchangeSessions) {
		this.exchangeSessions = exchangeSessions;
	}

}
