package com.proyecto.unit.asset.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.proyecto.common.currency.InvertarCurrencyCode;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.BondDTO;
import com.proyecto.rest.resource.asset.dto.StockDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;

public class AssetHelper {

	public static final String DEFAULT_STOCK_DESCRIPTION = "Tenaris S.A.,fabrica y provee ca�erias de acero y servicios relacionados para el sector energetico y otros usos industriales";
	public static final String DEFAULT_STOCK_TICKER = "TS.BA";
	public static final String DEFAULT_INDUSTRY = "Metalurgica";

	public static final String DEFAULT_BOND_DESCRIPTION = "Bonar 2024";
	public static final String DEFAULT_BOND_TICKER = "AY24";
	
	public static SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

	public static StockDTO createDefaultStockDTO() {

		StockDTO stockDTO = new StockDTO();

		stockDTO.setDescription(DEFAULT_STOCK_DESCRIPTION);
		stockDTO.setTicker(DEFAULT_STOCK_TICKER);
		stockDTO.setIndustry(DEFAULT_INDUSTRY);
		stockDTO.setCurrency(InvertarCurrencyCode.US);

		return stockDTO;
	}
	
	public static BondDTO createDefaultBondDTO() {

		BondDTO bondDTO = new BondDTO();

		bondDTO.setDescription(DEFAULT_BOND_DESCRIPTION);
		bondDTO.setTicker(DEFAULT_BOND_TICKER);
		bondDTO.setCurrency(InvertarCurrencyCode.US);

		return bondDTO;
	}

	public static AssetDTO createDefaultStockDTOWithTradingSessions()
			throws ParseException {

		AssetDTO stockDTO = createDefaultStockDTO();
		addDefaultTradingSessions(stockDTO);
		
		return stockDTO;
	}
	
	public static AssetDTO createDefaultBondDTOWithTradingSessions()
			throws ParseException {

		AssetDTO bondDTO = createDefaultBondDTO();
		addDefaultTradingSessions(bondDTO);
		
		return bondDTO;
	}
	
	public static void addDefaultTradingSessions(AssetDTO assetDTO) throws ParseException{
		
		TradingSessionDTO tradingSessionDayOne = new TradingSessionDTO();
		tradingSessionDayOne.setOpeningPrice(160.4);
		tradingSessionDayOne.setClosingPrice(169.4);
		tradingSessionDayOne.setTradingDate(sf.parse("26/05/2015").getTime());

		TradingSessionDTO tradingSessionDayTwo = new TradingSessionDTO();
		tradingSessionDayTwo.setOpeningPrice(171.0);
		tradingSessionDayTwo.setClosingPrice(170.0);
		tradingSessionDayTwo.setTradingDate(sf.parse("27/05/2015").getTime());

		TradingSessionDTO tradingSessionDayThree = new TradingSessionDTO();
		tradingSessionDayThree.setOpeningPrice(169.1);
		tradingSessionDayThree.setClosingPrice(169.0);
		tradingSessionDayThree.setTradingDate(sf.parse("28/05/2015").getTime());

		TradingSessionDTO tradingSessionDayFour = new TradingSessionDTO();
		tradingSessionDayFour.setOpeningPrice(161.0);
		tradingSessionDayFour.setClosingPrice(168.0);
		tradingSessionDayFour.setTradingDate(sf.parse("29/05/2015").getTime());

		TradingSessionDTO tradingSessionDayFive = new TradingSessionDTO();
		tradingSessionDayFive.setOpeningPrice(166.0);
		tradingSessionDayFive.setClosingPrice(166.0);
		tradingSessionDayFive.setTradingDate(sf.parse("01/06/2015").getTime());

		TradingSessionDTO tradingSessionDaySix = new TradingSessionDTO();
		tradingSessionDaySix.setOpeningPrice(174.1);
		tradingSessionDaySix.setClosingPrice(174.5);
		tradingSessionDaySix.setTradingDate(sf.parse("02/06/2015").getTime());

		TradingSessionDTO tradingSessionDaySeven = new TradingSessionDTO();
		tradingSessionDaySeven.setOpeningPrice(172.1);
		tradingSessionDaySeven.setClosingPrice(173.9);
		tradingSessionDaySeven.setTradingDate(sf.parse("03/06/2015").getTime());

		TradingSessionDTO tradingSessionDayEight = new TradingSessionDTO();
		tradingSessionDayEight.setOpeningPrice(148.75);
		tradingSessionDayEight.setClosingPrice(168.75);
		tradingSessionDayEight.setTradingDate(sf.parse("04/06/2015").getTime());

		TradingSessionDTO tradingSessionDayNine = new TradingSessionDTO();
		tradingSessionDayNine.setOpeningPrice(163.1);
		tradingSessionDayNine.setClosingPrice(168.0);
		tradingSessionDayNine.setTradingDate(sf.parse("05/06/2015").getTime());

		TradingSessionDTO tradingSessionDayTen = new TradingSessionDTO();
		tradingSessionDayTen.setOpeningPrice(163.1);
		tradingSessionDayTen.setClosingPrice(168.5);
		tradingSessionDayTen.setTradingDate(sf.parse("08/06/2015").getTime());

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
		
	}

	public static TradingSessionDTO createDefaultTradingSession()
			throws ParseException {
		return new TradingSessionDTO();
	}

}
