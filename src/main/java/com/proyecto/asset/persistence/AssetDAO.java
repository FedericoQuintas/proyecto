package com.proyecto.asset.persistence;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.exception.ObjectNotFoundException;
import com.proyecto.common.persistence.GenericDAO;

public interface AssetDAO extends GenericDAO {

	public Asset store(Asset Asset) throws JsonGenerationException, JsonMappingException, IOException;

	public Asset findById(Long id) throws ObjectNotFoundException;

	public List<Asset> getAll();

	public void udpate(Asset asset);

	public Asset findByTicker(String description) throws ObjectNotFoundException;

}
