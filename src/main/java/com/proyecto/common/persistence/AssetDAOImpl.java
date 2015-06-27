package com.proyecto.common.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;

@Repository("AssetDAO")
public class AssetDAOImpl implements AssetDAO{
	
	List<Asset> assets = new ArrayList<Asset>();
	
	Long assetsSequence = new Long(1);
	
	//Long tradingSessionSequence = new Long(1); -> Por ahora no hace falta porque solo usamos la fecha.

	
	
	@Override
	public Long nextID() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void update(Asset asset) {
		int positionToRemove = 0;
		for (int i = 0; i < assets.size(); i++) {
			if (assets.get(i).getId().equals(asset.getId())) {
				positionToRemove = i;
			}
		}
		assets.remove(positionToRemove);
		assets.add(asset);
		
	}

	@Override
	public Long nextAssetID() {
		return assetsSequence++;
	}

}
