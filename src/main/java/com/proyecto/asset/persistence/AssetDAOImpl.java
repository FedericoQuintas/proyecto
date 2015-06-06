package com.proyecto.asset.persistence;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;

public class AssetDAOImpl implements AssetDAO {

	List<Asset> assets = new ArrayList<Asset>();

	Long assetsSequence = new Long(1);

	@Override
	public Long nextID() {
		return assetsSequence++;
	}

	@Override
	public void flush() {
		assets.clear();

	}

	@Override
	public Asset store(Asset asset) {
		assets.add(asset);

		return asset;
	}

	@Override
	public Asset findById(Long id) throws ObjectNotFoundException {
		for (Asset asset : assets) {
			if (asset.getId().equals(id)) {
				return asset;
			}
		}
		throw new ObjectNotFoundException("Asset " + id + "not found.");
	}

}
