package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Bond;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.BondDTO;

public class BondFactory extends AssetFactory {
	public static Bond create(BondDTO bondDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		Bond bond = new Bond(id, bondDTO.getDescription(),
				bondDTO.getTicker(), bondDTO.getCurrency());
		complete(bond, bondDTO);
		
		bond.setPesified(bondDTO.isPesified());
		return bond;
	}
}
