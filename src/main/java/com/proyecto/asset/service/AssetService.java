package com.proyecto.asset.service;

import java.util.List;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public interface AssetService {

	/**
	 * Stores the Asset specified and retrieves it with new Id assigned.
	 * 
	 * @param assetDTO
	 * @return AssetDTO
	 * @throws InvalidAssetArgumentException
	 */
	AssetDTO store(AssetDTO assetDTO) throws InvalidAssetArgumentException;

	/**
	 * Retrieves the AssetDTO with specified ID
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws AssetNotFoundException
	 */
	AssetDTO findById(Long nOT_EXISTING_USER_ID) throws AssetNotFoundException;

	/**
	 * Adds a TradingSession to Asset TradingSession collection.
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws AssetNotFoundException
	 */
	void addTradingSession(Long id, TradingSessionDTO tradingSessionDTO)
			throws AssetNotFoundException;

	/**
	 * Adds a TradingSession to Asset TradingSession collection.
	 * 
	 * @param id
	 * @return AssetDTO
	 * @throws InvalidAssetArgumentException
	 */
	void update(AssetDTO assetDTO) throws InvalidAssetArgumentException;

	/**
	 * Retrieves all de AssetDTOs
	 * 
	 * @param id
	 * @return List<AssetDTO>
	 */
	List<AssetDTO> getAllAssets();

}