package com.proyecto.asset.persistence;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.common.persistence.GenericDAO;

public interface AssetDAO extends GenericDAO {

	public Asset store(Asset Asset);

	public Asset findById(Long id) throws ObjectNotFoundException;

}
