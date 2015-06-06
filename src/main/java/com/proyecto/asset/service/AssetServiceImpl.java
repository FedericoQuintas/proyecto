package com.proyecto.asset.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.factory.AssetFactory;
import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.factory.AssetDTOFactory;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

	@Resource
	private AssetDAO assetDAO;

	@Override
	public AssetDTO store(AssetDTO assetDTO) {

		Asset asset = AssetFactory.create(assetDTO, assetDAO.nextID());

		Asset storedAsset = assetDAO.store(asset);

		return AssetDTOFactory.create(storedAsset);
	}

	@Override
	public AssetDTO findById(Long id) throws AssetNotFoundException {
		try {
			Asset asset = assetDAO.findById(id);

			return AssetDTOFactory.create(asset);
		} catch (ObjectNotFoundException e) {
			throw new AssetNotFoundException(e);
		}
	}
}
