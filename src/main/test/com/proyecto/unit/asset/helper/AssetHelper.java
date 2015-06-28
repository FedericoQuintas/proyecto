package com.proyecto.unit.asset.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.proyecto.common.currency.InvertarCurrency;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetHelper {

	public static final String DEFAULT_DESCRIPTION = "Tenaris S.A.,fabrica y provee ca�erias de acero y servicios relacionados para el sector energetico y otros usos industriales";
	public static final String DEFAULT_TICKER = "TS.BA";
	public static final String DEFAULT_INDUSTRY = "Metalurgica";

	public static SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

	public static AssetDTO createDefaultAssetDTO() {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setDescription(DEFAULT_DESCRIPTION);
		assetDTO.setTicker(DEFAULT_TICKER);
		assetDTO.setIndustry(DEFAULT_INDUSTRY);
		assetDTO.setCurrency(InvertarCurrency.US);

		return assetDTO;
	}
	
	public static AssetDTO createDefaultAssetDTOWithTradingSessions() throws ParseException{
		
		AssetDTO assetDTO = createDefaultAssetDTO();
		
		
		
		TradingSessionDTO tradingSessionDayOne = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(169.4);
		tradingSessionDayOne.setTradingDate(sf.parse("26/05/2015"));
		
		TradingSessionDTO tradingSessionDayTwo = new TradingSessionDTO();
		tradingSessionDayTwo.setClosingPrice(170.0);
		tradingSessionDayTwo.setTradingDate(sf.parse("27/05/2015"));
		
		TradingSessionDTO tradingSessionDayThree = new TradingSessionDTO();
		tradingSessionDayThree.setClosingPrice(169.0);
		tradingSessionDayThree.setTradingDate(sf.parse("28/05/2015"));
		
		TradingSessionDTO tradingSessionDayFour = new TradingSessionDTO();
		tradingSessionDayFour.setClosingPrice(168.0);
		tradingSessionDayFour.setTradingDate(sf.parse("29/05/2015"));
		
		TradingSessionDTO tradingSessionDayFive = new TradingSessionDTO();
		tradingSessionDayFive.setClosingPrice(166.0);
		tradingSessionDayFive.setTradingDate(sf.parse("01/06/2015"));
		
		TradingSessionDTO tradingSessionDaySix = new TradingSessionDTO();
		tradingSessionDaySix.setClosingPrice(174.5);
		tradingSessionDaySix.setTradingDate(sf.parse("02/06/2015"));
		
		TradingSessionDTO tradingSessionDaySeven = new TradingSessionDTO();
		tradingSessionDaySeven.setClosingPrice(173.9);
		tradingSessionDaySeven.setTradingDate(sf.parse("03/06/2015"));
		
		TradingSessionDTO tradingSessionDayEight = new TradingSessionDTO();
		tradingSessionDayEight.setClosingPrice(168.75);
		tradingSessionDayEight.setTradingDate(sf.parse("04/06/2015"));
		
		TradingSessionDTO tradingSessionDayNine = new TradingSessionDTO();
		tradingSessionDayNine.setClosingPrice(168.0);
		tradingSessionDayNine.setTradingDate(sf.parse("05/06/2015"));
		
		TradingSessionDTO tradingSessionDayTen = new TradingSessionDTO();
		tradingSessionDayTen.setClosingPrice(168.5);
		tradingSessionDayTen.setTradingDate(sf.parse("08/06/2015"));
		
		assetDTO.getTradingSessions().add(tradingSessionDayOne);
		assetDTO.getTradingSessions().add(tradingSessionDayTwo);
		assetDTO.getTradingSessions().add(tradingSessionDayThree);
		assetDTO.getTradingSessions().add(tradingSessionDayFour);
		assetDTO.getTradingSessions().add(tradingSessionDayFive);
		assetDTO.getTradingSessions().add(tradingSessionDaySix);
		assetDTO.getTradingSessions().add(tradingSessionDaySeven);
		assetDTO.getTradingSessions().add(tradingSessionDayEight);
		assetDTO.getTradingSessions().add(tradingSessionDayNine);
		assetDTO.getTradingSessions().add(tradingSessionDayTen);
		
		return assetDTO;
	}

	public static TradingSessionDTO createDefaultTradingSession() throws ParseException {
		return new TradingSessionDTO();
	}

}
