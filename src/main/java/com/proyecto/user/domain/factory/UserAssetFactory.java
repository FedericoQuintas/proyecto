package com.proyecto.user.domain.factory;

import com.proyecto.rest.resource.user.dto.UserAssetDTO;
import com.proyecto.user.domain.UserAsset;

public class UserAssetFactory {

	public static UserAsset create(UserAssetDTO userAssetDTO) {
		UserAsset userAsset = new UserAsset();

		userAsset.setAssetId(userAssetDTO.getAssetId());
		return userAsset;
	}

}
