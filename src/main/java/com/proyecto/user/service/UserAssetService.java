package com.proyecto.user.service;

import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

public interface UserAssetService {

	Transaction obtainLastTransaction(UserAsset userAsset);

}
