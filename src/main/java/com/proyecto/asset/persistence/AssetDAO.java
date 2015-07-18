package com.proyecto.asset.persistence;

import java.util.List;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.common.persistence.GenericDAO;

public interface AssetDAO extends GenericDAO {

	public Asset store(Asset Asset);

	public Asset findById(Long id) throws ObjectNotFoundException;

	public List<Asset> getAll();

	public void update(Asset asset);

	public Asset findByTicker(String description) throws ObjectNotFoundException;

}
