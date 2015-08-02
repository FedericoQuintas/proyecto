package com.proyecto.asset.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.DBAccessException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.StockDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public interface AssetService {

	/**
	 * Stores the Asset specified and retrieves it with new Id assigned.
	 * 
	 * @param assetDTO
	 * @return AssetDTO
	 * @throws InvalidAssetArgumentException
	 * @throws DBAccessException
	 */
	AssetDTO store(AssetDTO assetDTO) throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException, DBAccessException;

	/**
	 * Retrieves the AssetDTO with specified ID
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws AssetNotFoundException
	 */
	AssetDTO findById(Long assetID) throws AssetNotFoundException;

	/**
	 * Adds a TradingSession to Asset TradingSession collection.
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws AssetNotFoundException
	 */
	void addTradingSession(Long id, TradingSessionDTO tradingSessionDTO)
			throws AssetNotFoundException,
			InvalidTradingSessionArgumentException;

	/**
	 * Adds a TradingSession to Asset TradingSession collection.
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws InvalidAssetArgumentException
	 */
	void update(AssetDTO assetDTO) throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException;

	/**
	 * Returns a map of (date, change %) taking as a reference the start date's 
	 * price
	 * @param assetId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws AssetNotFoundException
	 */
	Map<Long, Double> getPercentageOfChange(long assetId, Date startDate,
			Date endDate) throws AssetNotFoundException;

	/**
	 * Retrieves all the Assets DTOs
	 * 
	 * @return List<AssetDTO>
	 */

	List<AssetDTO> getAllAssets();

	/**
	 * Retrieves the AssetDTO with specified Description
	 * 
	 * @param description
	 * @return AssetDTO
	 * @throws AssetNotFoundException
	 */
	AssetDTO findByTicker(String description)
			throws AssetNotFoundException;

	/**
	 * Retrieves all the Assets DTOs without their trading sessions
	 * @return
	 */
	List<AssetDTO> getAllAssetsWithoutTradingSessions();
	
	
	List<TradingSessionDTO> getAssetTradingSessions(Long assetId, Date startDate,
			Date endDate) throws AssetNotFoundException;

	AssetDTO store(StockDTO assetDTO) throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException, DBAccessException;

}
