package com.proyecto.rest.resource.asset;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<AssetDTO> getAllAssetsWithoutTradingSessions() {
		return assetService.getAllAssetsWithoutTradingSessions();
	}

	@RequestMapping(value = "/{assetId}", method = RequestMethod.GET)
	@ResponseBody
	public AssetDTO findAssetById(@PathVariable("assetId") Long assetId)
			throws ApplicationServiceException {
		return assetService.findById(assetId);
	}
	
	@RequestMapping(value = "/{assetId}", method = RequestMethod.GET)
	@ResponseBody
	public List<TradingSessionDTO> getTradingSessions(
			@PathVariable("assetId") Long assetId, 
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, 
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) 
					throws AssetNotFoundException{
		return assetService.getAssetTradingSessions(assetId, startDate, endDate);
		
	}

	@RequestMapping(params = { "ticker" }, method = RequestMethod.GET)
	@ResponseBody
	public AssetDTO findAssetByTicker(
			@RequestParam(value = "ticker") String ticker)
			throws ApplicationServiceException {
		return assetService.findByTicker(ticker);
	}
}
