package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Bond;
import com.proyecto.rest.resource.asset.dto.BondDTO;

public class BondDTOFactory extends AssetDTOFactory {
	public static BondDTO create(Bond bond) {

		BondDTO bondDTO = new BondDTO();
		complete(bondDTO, bond);
		
		bondDTO.setDollarLinked(bond.isDollarLinked());

		return bondDTO;
	}
}
