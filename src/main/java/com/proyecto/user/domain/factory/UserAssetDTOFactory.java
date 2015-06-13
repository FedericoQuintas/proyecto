package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.usserasset.dto.UserAssetDTO;
import com.proyecto.userasset.domain.UserAsset;

public class UserAssetDTOFactory {

	public static UserAssetDTO create(UserAsset userAsset) {
		return new UserAssetDTO();
	}

}
