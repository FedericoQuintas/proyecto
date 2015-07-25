package com.proyecto.yahoofinance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

@Service("yahooFinanceInformationService")
public class YahooFinanceInformationServiceImpl implements
		YahooFinanceInformationService {

	@Resource
	private AssetService assetService;

	@Scheduled(cron = "*/59 * * * * ?")
	@Override
	public void update() throws AssetNotFoundException,
			InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

		List<AssetDTO> assetDTOs = obtainAssetDTOs();

		for (AssetDTO assetDTO : assetDTOs) {

			Stock stock = obtainStockInformation(assetDTO);
			BigDecimal price = stock.getQuote(true).getPrice();
			assetDTO.setLastTradingPrice(price.floatValue());
			assetService.update(assetDTO);
		}

	}

	private List<AssetDTO> obtainAssetDTOs() {
		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();
		for (AssetDTO asset : assetService.getAllAssets()) {
			assetDTOs.add(asset);
		}

		return assetDTOs;
	}

	private Stock obtainStockInformation(AssetDTO assetDTO) {
		Stock stock = YahooFinance.get(assetDTO.getTicker());
		return stock;
	}

}
