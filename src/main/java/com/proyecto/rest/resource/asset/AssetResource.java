package com.proyecto.rest.resource.asset;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.asset.service.AssetService;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

@Controller("assetResource")
@RequestMapping("/assets")
public class AssetResource {

	@Resource
	private AssetService assetService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public AssetDTO store(@RequestBody AssetDTO assetDTO)
			throws ApplicationServiceException {
		return assetService.store(assetDTO);
	}

	@RequestMapping(value = "/{assetId}", method = RequestMethod.GET)
	@ResponseBody
	public AssetDTO findAssetById(@PathVariable("assetId") Long userId)
			throws ApplicationServiceException {
		return assetService.findById(userId);
	}
}
