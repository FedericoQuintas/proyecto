package com.proyecto.yahoofinance.service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;

public interface YahooFinanceInformationService {

	/**
	 * Updates Dailly Information about Assets, retrieving information from
	 * YAHOO FINANCE API
	 * 
	 * @throws InvalidAssetArgumentException
	 * @throws AssetNotFoundException
	 * 
	 */
	void update() throws AssetNotFoundException, InvalidAssetArgumentException;

}
