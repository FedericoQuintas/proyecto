package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Stock;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.rest.resource.asset.dto.StockDTO;

public class StockFactory extends AssetFactory {
	public static Stock create(StockDTO stockDTO, Long id) 
			throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		Stock stock = new Stock(id, stockDTO.getDescription(),
				stockDTO.getTicker(), stockDTO.getCurrency());
		complete(stock, stockDTO);
		//TODO: completar los campos especificos de stock
		stock.setIndustry(stockDTO.getIndustry());
		return stock;
	}
}
