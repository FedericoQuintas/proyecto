package com.proyecto.common.persistence;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;

public interface AssetDAO extends GenericDAO{

		public Asset store(Asset asset);

		public Asset findById(Long id) throws ObjectNotFoundException;

		public void update(Asset asset);

		public Long nextAssetID();
	
}
