package com.proyecto.unit.asset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.proyecto.asset.domain.Asset;
import com.proyecto.asset.domain.factory.AssetFactory;
import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.exception.InvalidTradingSessionArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.rest.resource.asset.dto.TradingSessionDTO;
import com.proyecto.unit.asset.helper.AssetHelper;
import com.proyecto.yahoofinance.service.YahooFinanceInformationService;

public class AssetServiceTest extends SpringBaseTest {

	private AssetDTO assetDTO;

	@Resource
	private AssetService assetService;

	@Resource
	private YahooFinanceInformationService yahooFinanceService;

	@Before
	public void before() throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {
		storeAsset();
	}

	@Test
	public void whenCreatesAssetThenAssetIsCreatedWithMandatoryFields() {
		assertAssetHasMandatoryFields();
	}

	@Test
	public void whenAddsTradingSessionToAssetThenTradingSessionIsAdded()
			throws AssetNotFoundException, InvalidTradingSessionArgumentException, ParseException {

		TradingSessionDTO tradingSessionDTO = AssetHelper
				.createDefaultTradingSession();
		tradingSessionDTO.setTradingDate(AssetHelper.sf.parse("08/06/2015"));

		assetService.addTradingSession(assetDTO.getId(), tradingSessionDTO);

		AssetDTO asset = assetService.findById(assetDTO.getId());

		Assert.assertFalse(asset.getTradingSessions().isEmpty());

	}

	@Test
	public void whenAskForAssetLastTradingPriceThenLastTradingPriceIsRetrieved()
			throws AssetNotFoundException, InvalidAssetArgumentException, InvalidTradingSessionArgumentException {

		yahooFinanceService.update();

		assetDTO = assetService.findById(assetDTO.getId());

		Assert.assertNotNull(assetDTO.getLastTradingPrice());

	}

	@Test
	public void whenCreatesAssetWithNoDescriptionThenExceptionIsThrown() {

		AssetDTO incompleteAssetDTO = AssetHelper.createDefaultAssetDTO();

		incompleteAssetDTO.setDescription(null);

		try {
			assetService.store(incompleteAssetDTO);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.INVALID_ARGUMENT));
		}

	}
	
	@Test(expected=InvalidTradingSessionArgumentException.class)
	public void whenCreatesTradingSessionWithNullOrVoidDateThenExceptionIsThrown() throws ParseException, AssetNotFoundException, InvalidAssetArgumentException, InvalidTradingSessionArgumentException{
		TradingSessionDTO incompleteTradingSessionDTO = AssetHelper.createDefaultTradingSession();
		Asset asset = AssetFactory.create(assetDTO, new Long(1));
		asset.addTradingSession(incompleteTradingSessionDTO);
		
	}

	@Test
	public void whenCreatesAssetWithNoTickerThenExceptionIsThrown() {

		AssetDTO incompleteAssetDTO = AssetHelper.createDefaultAssetDTO();

		incompleteAssetDTO.setTicker(null);

		try {
			assetService.store(incompleteAssetDTO);
		} catch (ApplicationServiceException e) {
			Assert.assertTrue(e.getErrorCode().equals(
					InvertarErrorCode.INVALID_ARGUMENT));
		}

	}

	private void assertAssetHasMandatoryFields() {
		Assert.assertNotNull(assetDTO.getId());
		Assert.assertNotNull(assetDTO.getDescription());
		Assert.assertNotNull(assetDTO.getTicker());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws AssetNotFoundException {

		AssetDTO storedAssetDTO = assetService.findById(assetDTO.getId());

		Assert.assertTrue(assetDTO.getId().equals(storedAssetDTO.getId()));

	}

	@Test
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown() {

		Long NOT_EXISTING_ASSET_ID = new Long(1000);

		try {
			assetService.findById(NOT_EXISTING_ASSET_ID);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("not found"));
		}

	}
	
	@Test 
	public void whenGetsPercentageOfChangesTheRightResultsAreReturned() throws ParseException, InvalidAssetArgumentException, InvalidTradingSessionArgumentException{
		
		AssetDTO assetDTO = AssetHelper.createDefaultAssetDTOWithTradingSessions();
		
		Asset asset = AssetFactory.create(assetDTO, new Long(1));		

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		Map<Long,Double> percentagesOfChanges = asset.getPercentageOfChange(sf.parse("26/05/2015"), sf.parse("08/06/2015"));
		
		Assert.assertEquals(0.0,percentagesOfChanges.get(sf.parse("26/05/2015").getTime()), 2e-2);
		Assert.assertEquals(0.35,percentagesOfChanges.get(sf.parse("27/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.24,percentagesOfChanges.get(sf.parse("28/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.83,percentagesOfChanges.get(sf.parse("29/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-2.01,percentagesOfChanges.get(sf.parse("01/06/2015").getTime()), 2e-2);
		Assert.assertEquals(3.01,percentagesOfChanges.get(sf.parse("02/06/2015").getTime()), 2e-2);
		Assert.assertEquals(2.65,percentagesOfChanges.get(sf.parse("03/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.38,percentagesOfChanges.get(sf.parse("04/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.83,percentagesOfChanges.get(sf.parse("05/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.53,percentagesOfChanges.get(sf.parse("08/06/2015").getTime()), 2e-2);
		
	}
	
	@Test 
	public void whenGetsPercentageOfChangesTheRightResultsAreReturned2() throws ParseException, InvalidAssetArgumentException, InvalidTradingSessionArgumentException{
		
		AssetDTO assetDTO = AssetHelper.createDefaultAssetDTOWithTradingSessions();
		
		Asset asset = AssetFactory.create(assetDTO, new Long(1));		

		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		
		Map<Long,Double> percentagesOfChanges = asset.getPercentageOfChange(sf.parse("27/05/2015"), sf.parse("08/06/2015"));
		
		Assert.assertEquals(0.00,percentagesOfChanges.get(sf.parse("27/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.59,percentagesOfChanges.get(sf.parse("28/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-1.18,percentagesOfChanges.get(sf.parse("29/05/2015").getTime()), 2e-2);
		Assert.assertEquals(-2.35,percentagesOfChanges.get(sf.parse("01/06/2015").getTime()), 2e-2);
		Assert.assertEquals(2.65,percentagesOfChanges.get(sf.parse("02/06/2015").getTime()), 2e-2);
		Assert.assertEquals(2.29,percentagesOfChanges.get(sf.parse("03/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.74,percentagesOfChanges.get(sf.parse("04/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-1.18,percentagesOfChanges.get(sf.parse("05/06/2015").getTime()), 2e-2);
		Assert.assertEquals(-0.88,percentagesOfChanges.get(sf.parse("08/06/2015").getTime()), 2e-2);
		
	}

	@Test
	public void whenAsksForAllTheAssetsThenAllTheAssetsAreRetrieved() {

		Assert.assertNotNull(assetService.getAllAssets());
	}

	private void storeAsset() throws InvalidAssetArgumentException, InvalidTradingSessionArgumentException {

		assetDTO = AssetHelper.createDefaultAssetDTO();

		assetDTO = assetService.store(assetDTO);

	}

}