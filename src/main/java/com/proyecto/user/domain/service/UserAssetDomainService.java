package com.proyecto.user.domain.service;

import com.proyecto.user.domain.Transaction;
import com.proyecto.user.domain.UserAsset;

public interface UserAssetDomainService {

	Transaction obtainLastTransaction(UserAsset userAsset);

}
