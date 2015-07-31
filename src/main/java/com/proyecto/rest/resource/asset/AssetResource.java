package com.proyecto.rest.resource.asset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.AccessDeniedException;
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
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private AssetService assetService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public AssetDTO store(HttpSession session, @RequestBody AssetDTO assetDTO)
			throws ApplicationServiceException {
		if (isAdmin(session)) {
			return assetService.store(assetDTO);
		} else {
			throw new AccessDeniedException("Invalid Role");
		}
	}

	private boolean isAdmin(HttpSession session) {
		return session.getAttribute("ROLE") != null
				&& session.getAttribute("ROLE").equals("admin");
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

	@RequestMapping(value = "/{assetId}/tradingSessions", method = RequestMethod.GET)
	@ResponseBody
	public List<TradingSessionDTO> getTradingSessions(
			@PathVariable("assetId") Long assetId,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate)
			throws AssetNotFoundException, ParseException {

		System.out.print(String.format("StartDate: %s  EndDate: %s",
				sf.parse(startDate).getTime(), sf.parse(endDate).getTime()));
		return assetService.getAssetTradingSessions(assetId,
				sf.parse(startDate), sf.parse(endDate));

	}

	@RequestMapping(value = "/{assetId}/tradingSessions/changePercentage", method = RequestMethod.GET)
	@ResponseBody
	public Map<Long, Double> getTradingSessionsChangePercentage(
			@PathVariable("assetId") Long assetId,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate)
			throws AssetNotFoundException, ParseException {

		return assetService.getPercentageOfChange(assetId, sf.parse(startDate),
				sf.parse(endDate));

	}

	@RequestMapping(params = { "ticker" }, method = RequestMethod.GET)
	@ResponseBody
	public AssetDTO findAssetByTicker(
			@RequestParam(value = "ticker") String ticker)
			throws ApplicationServiceException {
		return assetService.findByTicker(ticker);
	}
}
