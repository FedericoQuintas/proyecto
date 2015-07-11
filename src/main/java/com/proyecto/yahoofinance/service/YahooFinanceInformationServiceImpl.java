package com.proyecto.yahoofinance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.factory.AssetDTOFactory;
import com.proyecto.asset.domain.factory.AssetFactory;
import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.asset.persistence.AssetDAOImpl;
import com.proyecto.asset.persistence.AssetMongoDAOImpl;
import com.proyecto.rest.resource.asset.dto.AssetDTO;

@Service("yahooFinanceInformationService")
public class YahooFinanceInformationServiceImpl extends QuartzJobBean implements
		YahooFinanceInformationService {

	@Override
	public void update() throws AssetNotFoundException,
			InvalidAssetArgumentException,
			InvalidTradingSessionArgumentException {

		List<AssetDTO> assetDTOs = obtainAssetDTOs();

		for (AssetDTO assetDTO : assetDTOs) {

			Stock stock = obtainStockInformation(assetDTO);
			BigDecimal price = stock.getQuote(true).getPrice();
			assetDTO.setLastTradingPrice(price.floatValue());
			Asset asset = AssetFactory.create(assetDTO, assetDTO.getId());
			AssetDAOImpl.getInstance().udpate(asset);
		}

	}

	private List<AssetDTO> obtainAssetDTOs() {
		List<AssetDTO> assetDTOs = new ArrayList<AssetDTO>();

		for (Asset asset : AssetMongoDAOImpl.getInstance().getAll()) {
			assetDTOs.add(AssetDTOFactory.create(asset));
		}

		return assetDTOs;
	}

	private Stock obtainStockInformation(AssetDTO assetDTO) {
		Stock stock = YahooFinance.get(assetDTO.getTicker());
		return stock;
	}

	@Override
	public void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			update();
		} catch (AssetNotFoundException | InvalidAssetArgumentException
				| InvalidTradingSessionArgumentException e) {
			throw new JobExecutionException();
		}

	}

}
