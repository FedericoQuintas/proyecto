package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.MutualFund;
import com.proyecto.rest.resource.asset.dto.MutualFundDTO;

public class MutualFundDTOFactory extends AssetDTOFactory {
	public static MutualFundDTO create(MutualFund mutualFund) {

		MutualFundDTO mutualFundDTO = new MutualFundDTO();
		complete(mutualFundDTO, mutualFund);
		
		mutualFundDTO.setFundType(mutualFund.getFundType());

		return mutualFundDTO;
	}

}
