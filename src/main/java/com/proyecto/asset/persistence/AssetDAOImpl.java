package com.proyecto.asset.persistence;

import java.util.ArrayList;
import java.util.List;

import com.proyecto.asset.domain.Asset;
import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.common.exception.ObjectNotFoundException;

public class AssetDAOImpl implements AssetDAO {

	List<Asset> assets = new ArrayList<Asset>();

	Long assetsSequence = new Long(1);
	
	public AssetDAOImpl(){
		loadInitialAssets();
	}

	@Override
	public Long nextID() {
		return assetsSequence++;
	}

	@Override
	public void flush() {
		assets.clear();
		assetsSequence = new Long(1);

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
	public List<Asset> getAll() {
		return assets;
	}

	public void remove(Asset asset) {

		Integer positionToRemove = null;

		for (int i = 0; i < assets.size(); i++) {

			if (assets.get(i).getId().equals(asset.getId())) {
				positionToRemove = i;
			}
		}
		assets.remove(positionToRemove.intValue());

	}

	@Override
	public void udpate(Asset asset) {
		remove(asset);
		store(asset);
	}
	
	private void loadInitialAssets(){
		store(createAsset(nextID(),"Andes Energia PLC","AEN"));
		store(createAsset(nextID(),"Agrometal S.A.I.","AGRO"));
		store(createAsset(nextID(),"Petróleo Brasileiro S.A.","APBRA"));
		store(createAsset(nextID(),"APSA","Alto Palermo S.A."));
		store(createAsset(nextID(),"Autopistas del Sol S.A.","AUSO"));
		store(createAsset(nextID(),"Banco Hipotecario S.A.","BHIP"));
		store(createAsset(nextID(),"Boldt S.A.","BOLT"));
		store(createAsset(nextID(),"Banco Patagonia S.A.","BPAT"));
		store(createAsset(nextID(),"Banco Santander Río S.A.","BRIO"));
		store(createAsset(nextID(),"Carlos Casado S.A.","CADO"));
		store(createAsset(nextID(),"Caputo S.A.","CAPU"));
		store(createAsset(nextID(),"Capex S.A.","CAPX"));
		store(createAsset(nextID(),"Carboclor S.A.","CARC"));
		store(createAsset(nextID(),"Endesa Costanera S.A.","CECO2"));
		store(createAsset(nextID(),"Celulosa Argentina S.A.","CELU"));
		store(createAsset(nextID(),"Central Puerto S.A.","CEPU2"));
		store(createAsset(nextID(),"Camuzzi Gas Pampeana S.A.","CGPA2"));
		store(createAsset(nextID(),"Colorin S.A.","COLO"));
		store(createAsset(nextID(),"Continental Urbana S.A.I.","COUR"));
		store(createAsset(nextID(),"Cresud S.A.","CRES"));
		store(createAsset(nextID(),"Consultatio S.A.","CTIO"));
		store(createAsset(nextID(),"Domec S.A.","DOME"));
		store(createAsset(nextID(),"Dycasa S.A.","DYCA"));
		store(createAsset(nextID(),"Bodegas Esmeralda S.A.","ESME"));
		store(createAsset(nextID(),"Angel Estrada y Cia. S.A.","ESTR"));
		store(createAsset(nextID(),"Ferrum S.A.","FERR"));
		store(createAsset(nextID(),"Fiplasto S.A.","FIPL"));
		store(createAsset(nextID(),"Garovaglio y Zorraquin S.A.","GARO"));
	}
	
	private Asset createAsset(Long id, String description, String ticker){
		InvertarCurrency currency = InvertarCurrency.ARS;
		return new Asset(id, description, ticker, currency);
	}

}
