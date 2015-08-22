package com.proyecto.asset.domain.factory;

import com.proyecto.asset.domain.Stock;
import com.proyecto.rest.resource.asset.dto.StockDTO;

public class StockDTOFactory extends AssetDTOFactory {
	
	public static StockDTO create(Stock stock) {

		StockDTO stockDTO = new StockDTO();
		complete(stockDTO, stock);
		//TODO: Completar con los campos especificos de stock
		stockDTO.setIndustry(stock.getIndustry());

		return stockDTO;
	}
}
