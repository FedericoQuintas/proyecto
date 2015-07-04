package com.proyecto.asset.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.factory.AssetDTOFactory;
import com.proyecto.asset.domain.factory.AssetFactory;
import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.asset.persistence.AssetDAO;
import com.proyecto.asset.persistence.AssetDAOImpl;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

@Service("assetService")
public class AssetServiceImpl implements AssetService {

	private AssetDAO assetDAO;

	@PostConstruct
	public void init() {
		assetDAO = AssetDAOImpl.getInstance();
	}

	@Override
	public AssetDTO store(AssetDTO assetDTO)
			throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

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

	@Override
	public void addTradingSession(Long id, TradingSessionDTO tradingSessionDTO)
			throws AssetNotFoundException,
			InvalidTradingSessionArgumentException {

		Asset asset;
		try {
			asset = assetDAO.findById(id);
		} catch (ObjectNotFoundException e) {
			throw new AssetNotFoundException(e);
		}
		asset.addTradingSession(tradingSessionDTO);

		update(asset);
	}

	private void update(Asset asset) {
		assetDAO.udpate(asset);

	}

	@Override
	public void update(AssetDTO assetDTO) throws InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

		Asset asset = AssetFactory.create(assetDTO, assetDTO.getId());
		update(asset);

	}

	@Override
	public List<AssetDTO> getAllAssets() {

		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();

		for (Asset asset : assetDAO.getAll()) {
			assetDTOs.add(AssetDTOFactory.create(asset));
		}

		return assetDTOs;

	}

	@Override
	public Map<Long, Double> getPercentageOfChange(long assetId,
			Date startDate, Date endDate) throws AssetNotFoundException {

		try {
			Asset asset = assetDAO.findById(assetId);
			return asset.getPercentageOfChange(startDate, endDate);
		} catch (ObjectNotFoundException e) {
			throw new AssetNotFoundException(e);
		}

	}

	@Override
	public AssetDTO findByTicker(String ticker) throws AssetNotFoundException {
		try {
			Asset asset = assetDAO.findByTicker(ticker);

			return AssetDTOFactory.create(asset);
		} catch (ObjectNotFoundException e) {
			throw new AssetNotFoundException(e);
		}
	}
}
