package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.MutualFund;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.MutualFundDTO;

public class MutualFundFactory extends AssetFactory {
	public static MutualFund create(MutualFundDTO mutualFundDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		MutualFund mutualFund = new MutualFund(id, mutualFundDTO.getDescription(),
				mutualFundDTO.getTicker(), mutualFundDTO.getCurrency(), mutualFundDTO.getName());
		complete(mutualFund, mutualFundDTO);
		
		mutualFund.setFundType(mutualFundDTO.getFundType());
		return mutualFund;
	}
}
