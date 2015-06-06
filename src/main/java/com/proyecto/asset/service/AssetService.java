package com.proyecto.asset.service;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

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

}
