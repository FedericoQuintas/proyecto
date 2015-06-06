package com.proyecto.unit.asset;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.proyecto.asset.exception.AssetNotFoundException;
import com.proyecto.asset.exception.InvalidAssetArgumentException;
import com.proyecto.asset.service.AssetService;
import com.proyecto.common.SpringBaseTest;
import com.proyecto.common.error.InvertarErrorCode;
import com.proyecto.common.exception.ApplicationServiceException;
import com.proyecto.rest.resource.asset.dto.AssetDTO;
import com.proyecto.unit.asset.helper.AssetHelper;
import com.proyecto.user.exception.UserNotFoundException;

public class AssetServiceTest extends SpringBaseTest {

	private AssetDTO assetDTO;

	@Resource
	private AssetService assetService;

	@Before
	public void before() throws InvalidAssetArgumentException {
		storeAsset();
	}

	@Test
	public void whenCreatesAssetThenAssetIsCreatedWithMandatoryFields() {

		assertAssetHasMandatoryFields();
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

	private void assertAssetHasMandatoryFields() {
		Assert.assertNotNull(assetDTO.getId());
		Assert.assertNotNull(assetDTO.getDescription());
	}

	@Test
	public void whenSearchAnUserByIdThenUserIsRetrieved()
			throws AssetNotFoundException {

		AssetDTO storedAssetDTO = assetService.findById(assetDTO.getId());

		Assert.assertTrue(assetDTO.getId().equals(storedAssetDTO.getId()));

	}

	@Test
	public void whenSearchAnUserByIdAndUserDoesNotExistThenUserExceptionIsThrown()
			throws UserNotFoundException {

		Long NOT_EXISTING_ASSET_ID = new Long(1000);

		try {
			assetService.findById(NOT_EXISTING_ASSET_ID);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("not found"));
		}

	}

	private void storeAsset() throws InvalidAssetArgumentException {

		assetDTO = AssetHelper.createDefaultAssetDTO();

		assetDTO = assetService.store(assetDTO);

	}

}