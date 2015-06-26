package com.proyecto.unit.asset.helper;

import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AssetHelper {

	private static final String DEFAULT_DESCRIPTION = "Tenaris S.A.,fabrica y provee cañerias de acero y servicios relacionados para el sector energetico y otros usos industriales";
	private static final String DEFAULT_TICKER = "TS.BA";

	public static AssetDTO createDefaultAssetDTO() {

		AssetDTO assetDTO = new AssetDTO();

		assetDTO.setDescription(DEFAULT_DESCRIPTION);
		assetDTO.setTicker(DEFAULT_TICKER);

		return assetDTO;
	}
	
	public static AssetDTO createDefaultAssetDTOWithTradingSessions() throws ParseException{
		
		AssetDTO assetDTO = createDefaultAssetDTO();
		
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<TradingSessionDTO> tradingSessions = new ArrayList<TradingSessionDTO>();
				
		TradingSessionDTO tradingSessionDayOne = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(169.4);
		tradingSessionDayOne.setTradingDate(sf.parse("26/05/2015"));
		
		TradingSessionDTO tradingSessionDayTwo = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(170.0);
		tradingSessionDayOne.setTradingDate(sf.parse("27/05/2015"));
		
		TradingSessionDTO tradingSessionDayThree = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(169.0);
		tradingSessionDayOne.setTradingDate(sf.parse("28/05/2015"));
		
		TradingSessionDTO tradingSessionDayFour = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(168.0);
		tradingSessionDayOne.setTradingDate(sf.parse("29/05/2015"));
		
		TradingSessionDTO tradingSessionDayFive = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(166.0);
		tradingSessionDayOne.setTradingDate(sf.parse("01/06/2015"));
		
		TradingSessionDTO tradingSessionDaySix = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(174.5);
		tradingSessionDayOne.setTradingDate(sf.parse("02/06/2015"));
		
		TradingSessionDTO tradingSessionDaySeven = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(173.9);
		tradingSessionDayOne.setTradingDate(sf.parse("03/06/2015"));
		
		TradingSessionDTO tradingSessionDayEight = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(168.75);
		tradingSessionDayOne.setTradingDate(sf.parse("04/06/2015"));
		
		TradingSessionDTO tradingSessionDayNine = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(168.0);
		tradingSessionDayOne.setTradingDate(sf.parse("05/06/2015"));
		
		TradingSessionDTO tradingSessionDayTen = new TradingSessionDTO();
		tradingSessionDayOne.setClosingPrice(168.5);
		tradingSessionDayOne.setTradingDate(sf.parse("08/06/2015"));
		
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

	public static TradingSessionDTO createDefaultTradingSession() {
		return new TradingSessionDTO();
	}

}
