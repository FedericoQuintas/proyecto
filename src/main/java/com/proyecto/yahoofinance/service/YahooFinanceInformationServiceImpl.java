package com.proyecto.yahoofinance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

@Service("yahooFinance")
public class YahooFinanceInformationServiceImpl implements
		YahooFinanceInformationService {

	@Resource
	private AssetService assetService;

	@Override
	public void update() throws AssetNotFoundException,
			InvalidAssetArgumentException {

		List<AssetDTO> allAssets = assetService.getAllAssets();

		for (AssetDTO assetDTO : allAssets) {

			Stock stock = YahooFinance.get("INTC");
			BigDecimal price = stock.getQuote(true).getPrice();
			assetDTO.setLastTradingPrice(price.longValue());
			assetService.update(assetDTO);

		}

	}

}