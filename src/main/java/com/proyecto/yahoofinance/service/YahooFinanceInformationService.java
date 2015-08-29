package com.proyecto.yahoofinance.service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidAssetTypeException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;

public interface YahooFinanceInformationService {

	/**
	 * Updates Dailly Information about Assets, retrieving information from
	 * YAHOO FINANCE API
	 * 
	 * @throws InvalidAssetArgumentException
	 * @throws AssetNotFoundException
	 * @throws InvalidTradingSessionArgumentException 
	 * @throws InvalidAssetTypeException 
	 * 
	 */
	void update() throws AssetNotFoundException, InvalidAssetArgumentException, InvalidTradingSessionArgumentException, InvalidAssetTypeException;

}
