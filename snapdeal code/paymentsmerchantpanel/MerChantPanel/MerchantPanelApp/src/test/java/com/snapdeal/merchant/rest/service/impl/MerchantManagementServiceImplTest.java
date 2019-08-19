package com.snapdeal.merchant.rest.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUIData;
import com.snapdeal.merchant.dto.MerchantUploadedDocsDTO;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GetMerchantUIDataRequest;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantUpdateDetailsRequest;
import com.snapdeal.merchant.response.GetMerchantUIDataResponse;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;
import com.snapdeal.merchant.response.MerchantUpdateDetailsResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.util.MOBMapperUtil;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.dto.BankAccountDetailsDTO;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.dto.DocumentDTO;
import com.snapdeal.mob.dto.TDRDetailsDTO;
import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.GetMerchantDetailsByMerchantIdRequest;
import com.snapdeal.mob.request.GetUIDataRequest;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.request.UpdateMerchantDetailsRequest;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.GetUIDataResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.mob.ui.response.UIData;
import com.snapdeal.payments.roleManagementModel.request.GetRolesByRoleNamesRequest;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.Role;
import com.snapdeal.payments.roleManagementModel.response.GetRolesByRoleNamesResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:*/spring/application-context-mvc.xml")
public class MerchantManagementServiceImplTest extends AbstractTestNGSpringContextTests {

	@InjectMocks
	private MerchantManagementServiceImpl mpService;

	@Spy
	private MOBUtil mobUtil;

	@Spy
	private RMSUtil rmsUtil;

	@Mock
	private IMerchantServices merchantService;

	@Mock
	private RoleMgmtService roleMgmtService;

	@Mock
	private IUserService userService;

	@Spy
	private MOBMapperUtil mobMapperUtil;
	
	@Mock
	private MpanelConfig config;

	private List<String> roles = new ArrayList<String>();

	@BeforeClass
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	private MerchantBankInfo createBankInfo() {
		MerchantBankInfo bankInfo = new MerchantBankInfo();

		bankInfo.setBankAccount("accountNumber");
		bankInfo.setBankName("bankName");
		bankInfo.setIfscCode("ifsccode");
		bankInfo.setAccountHolderName("accountHolderName");
		// bankInfo.setDisburseDifferencePeriod(1);
		bankInfo.setBankStatus("bankStatus");
		return bankInfo;

	}

	private MerchantBusinessInfo createBusinessInfo() {
		MerchantBusinessInfo businessInfo = new MerchantBusinessInfo();
		businessInfo.setBusinessName("businessName");
		businessInfo.setSecondaryMobile("secondaryMobile");
		businessInfo.setBusinessCategory("businessCategory");
		businessInfo.setBusinessType("businessType");
		businessInfo.setSubCategory("subCategory");
		businessInfo.setTin("tin");
		businessInfo.setAddress1("address1");
		businessInfo.setAddress2("address2");
		businessInfo.setAppName("appName");
		businessInfo.setCity("city");
		businessInfo.setDateOfFormation("01/01/2015");
		businessInfo.setLandLineNumber("654321");
		businessInfo.setLogoUrl("www.gmail.com");
		businessInfo.setMerchantName("merchantName");
		businessInfo.setMerchantReserves("merchantReserves");
		businessInfo.setPincode("pincode");
		businessInfo.setPrimaryMobile("primaryMobile@gmail.com");
		businessInfo.setSecondaryEmail("secondaryEmail@gmail.com");
		businessInfo.setShopName("shopName");
		businessInfo.setState("state");
		businessInfo.setStdCode("stdCode");
		/*
		 * MerchantTDRDetailsDTO tdrDetailsDTO = new MerchantTDRDetailsDTO();
		 * tdrDetailsDTO.setFixedFeeValue("0");
		 * tdrDetailsDTO.setMerchantFeeValue("1");
		 * tdrDetailsDTO.setValidFrom("01/01/2015");
		 * businessInfo.setTdrDetailsDTO(tdrDetailsDTO);
		 */
		businessInfo.setVelocityLimits("velocityLimits");
		businessInfo.setWebsite("website");
		return businessInfo;

	}

	private MerchantUploadedDocsDTO createUploadDTO() {

		MerchantUploadedDocsDTO uploadDocs = new MerchantUploadedDocsDTO();
		uploadDocs.setApprovalStatus(false);
		uploadDocs.setContentType("contentType");
		uploadDocs.setDocIdentityValue("docIdentityValue");
		uploadDocs.setDocumentCategory("documentCategory");
		uploadDocs.setDocumentSize(10);
		uploadDocs.setDocumentType("documentType");
		uploadDocs.setId("id");
		uploadDocs.setName("name");
		return uploadDocs;
	}

	private MerchantRoleDTO createMerchantRoleDTO(String name, int id, String perm, boolean enable) {
		MerchantRoleDTO role1 = new MerchantRoleDTO();
		role1.setId(id);
		role1.setName(name);
		List<MerchantPermissionDTO> permissionList = new ArrayList<MerchantPermissionDTO>();
		MerchantPermissionDTO perm1 = new MerchantPermissionDTO();
		perm1.setDisplayName(perm);
		perm1.setEnabled(enable);
		perm1.setId(id);
		perm1.setName(perm);
		if (!perm.equals(""))
			permissionList.add(perm1);
		role1.setPermissions(permissionList);

		return role1;
	}

	private Role createRoleDTO(String name, int id, String perm) {
		Role role1 = new Role();
		role1.setId(id);
		role1.setName(name);
		List<Permission> permissionList = new ArrayList<Permission>();
		Permission perm1 = new Permission();
		perm1.setDisplayName(perm);
		perm1.setId(id);
		perm1.setName(perm);
		if (!perm.equals(""))
			permissionList.add(perm1);
		role1.setPermissions(permissionList);

		return role1;
	}

	private MerchantDetails createMerchantDetails() {
		MerchantDetails merchantDetails = new MerchantDetails();

		merchantDetails.setIntegrationMode(IntegrationMode.OFFLINE.getIntegrationMode());
		merchantDetails.setAddress1("address1");

		return merchantDetails;
	}

	@Test
	public void getMerchantRolesTest() throws MerchantException {

		MerchantRoleResponse exceptedResponse = new MerchantRoleResponse();
		List<MerchantRoleDTO> rolelist = new ArrayList<MerchantRoleDTO>();
		rolelist.add(createMerchantRoleDTO("Payments", 1, "", false));
		rolelist.add(createMerchantRoleDTO("Account", 2, "VIEW_PROFILE", false));
		exceptedResponse.setRoles(rolelist);

		MerchantRoleResponse actualResposne = new MerchantRoleResponse();

		GetRolesByRoleNamesResponse rmsResponse = new GetRolesByRoleNamesResponse();
		List<Role> rmsRolelist = new ArrayList<Role>();

		rmsRolelist.add(createRoleDTO("Payments", 1, "MCNT_INIT_REFUND"));
		rmsRolelist.add(createRoleDTO("Account", 2, "VIEW_PROFILE"));
		rmsRolelist.add(createRoleDTO("Integration", 3, "VIEW_SAND_KEY"));

		rmsResponse.setRoles(rmsRolelist);

		GetRolesByRoleNamesRequest rmsRequest = new GetRolesByRoleNamesRequest();
		List<String> listRoleNames = new ArrayList<String>();
		listRoleNames.add("Integration");
		listRoleNames.add("Payments");
		listRoleNames.add("Account");

		rmsRequest.setListRoleNames(listRoleNames);

		Mockito.when(roleMgmtService.getRolesByRoleNames(rmsRequest)).thenReturn(rmsResponse);

		GetUserMerchantResponse getUserMerchantResponse = new GetUserMerchantResponse();
		MerchantDetails merchantDetails = new MerchantDetails();
		getUserMerchantResponse.setMerchantDetails(createMerchantDetails());

		try {
			Mockito.when(userService.getUserMerchant(any(GetUserMerchantRequest.class)))
					.thenReturn(getUserMerchantResponse);
		} catch (ServiceException e) {
			throw new MerchantException(e.getMessage());
		}

		roles.add("Integration");
		roles.add("Payments");
		roles.add("Account");

		MerchantRoleRequest request = new MerchantRoleRequest();
		request.setLoggedLoginName("loggedLoginName");
		request.setLoggedUserId("loggedUserId");
		request.setMerchantId("merchantId");
		request.setToken("token");

		/*mpService.setRoles(roles);*/
		rmsUtil.setRoleMgmtService(roleMgmtService);
		mobUtil.setUserService(userService);
		actualResposne = mpService.getMerchantRoles(request);

		Assert.assertEquals(exceptedResponse, actualResposne);
	}

	@Test
	public void getMerchantDetailsTest() throws ServiceException, MerchantException {

		MerchantProfileRequest request = new MerchantProfileRequest();
		request.setMerchantId("merchantId1");
		request.setToken("token1");

		MerchantDetailResponse actualResponse = new MerchantDetailResponse();

		MerchantDetailResponse expectedResponse = new MerchantDetailResponse();

		expectedResponse.setBankInfo(createBankInfo());
		expectedResponse.setBusinessInfo(createBusinessInfo());
		List<MerchantUploadedDocsDTO> uploadedDocDTOList = new ArrayList<MerchantUploadedDocsDTO>();
		uploadedDocDTOList.add(createUploadDTO());

		expectedResponse.setUploadedDocDTO(uploadedDocDTOList);

		GetMerchantDetailsByMerchantIdRequest mobRequest = new GetMerchantDetailsByMerchantIdRequest();
		mobRequest.setAppName("appName1");
		mobRequest.setMerchantId("merchantId1");
		mobRequest.setToken("token1");

		GetMerchantDetails merchantDetails = new GetMerchantDetails();

		merchantDetails.setMerchantId("merchantId1");
		BankAccountDetailsDTO bankInfo = new BankAccountDetailsDTO();
		bankInfo.setAccountNumber("accountNumber");
		bankInfo.setBankName("bankName");
		bankInfo.setIfsccode("ifsccode");
		bankInfo.setAccountHolderName("accountHolderName");
		bankInfo.setDisburseDifferencePeriod(1);
		bankInfo.setBankStatus("bankStatus");
		merchantDetails.setBankAccountDetailsDTO(bankInfo);

		BusinessInformationDTO businessInfo = new BusinessInformationDTO();
		businessInfo.setBusinessName("businessName");
		businessInfo.setSecondaryMobile("secondaryMobile");
		businessInfo.setBusinessCategory("businessCategory");
		businessInfo.setBusinessType("businessType");
		businessInfo.setSubCategory("subCategory");
		businessInfo.setTIN("tin");
		businessInfo.setAddress1("address1");
		businessInfo.setAddress2("address2");
		businessInfo.setAppName("appName");
		businessInfo.setCity("city");
		businessInfo.setDateOfFormation("01/01/2015");
		businessInfo.setLandLineNumber("654321");
		businessInfo.setLogoUrl("www.gmail.com");
		businessInfo.setMerchantName("merchantName");
		businessInfo.setMerchantReserves("merchantReserves");
		businessInfo.setPincode("pincode");
		businessInfo.setPrimaryMobile("primaryMobile@gmail.com");
		businessInfo.setSecondaryEmail("secondaryEmail@gmail.com");
		businessInfo.setShopName("shopName");
		businessInfo.setState("state");
		businessInfo.setStdCode("stdCode");
		TDRDetailsDTO tdrDetailsDTO = new TDRDetailsDTO();
		tdrDetailsDTO.setFixedFeeValue("0");
		tdrDetailsDTO.setMerchantFeeValue("1");
		tdrDetailsDTO.setValidFrom("01/01/2015");
		businessInfo.setTdrDetailsDTO(tdrDetailsDTO);
		businessInfo.setVelocityLimits("velocityLimits");
		businessInfo.setWebsite("website");

		merchantDetails.setBusinessInformationDTO(businessInfo);

		List<DocumentDTO> documentDTOList = new ArrayList<DocumentDTO>();

		DocumentDTO uploadDocs = new DocumentDTO();
		uploadDocs.setApprovalStatus(false);
		uploadDocs.setContentType("contentType");
		uploadDocs.setDocIdentityValue("docIdentityValue");
		uploadDocs.setDocumentCategory("documentCategory");
		uploadDocs.setDocumentSize(10);
		uploadDocs.setDocumentType("documentType");
		uploadDocs.setId("id");
		uploadDocs.setName("name");
		documentDTOList.add(uploadDocs);

		merchantDetails.setDocumentDTO(documentDTOList);

		mobUtil.setMerchantService(merchantService);

		Mockito.when(merchantService.getMerchantDetailsByMerchantId(mobRequest)).thenReturn(merchantDetails);

		actualResponse = mpService.getMerchantDetails(request);

		AssertJUnit.assertEquals(expectedResponse, actualResponse);

	}

	public MerchantUIData createUIData() {
		MerchantUIData uiData = new MerchantUIData();
		uiData.setDisplayValue("displayValue");
		uiData.setParent("parent");
		uiData.setType("type");
		uiData.setValue("value");
		return uiData;
	}

	@Test
	public void getUIDataTest() throws ServiceException, MerchantException {

		GetMerchantUIDataResponse expectedResponse = new GetMerchantUIDataResponse();
		GetMerchantUIDataResponse actualResponse = null;

		List<MerchantUIData> merchantUIDataList = new ArrayList<MerchantUIData>();
		merchantUIDataList.add(createUIData());
		expectedResponse.setMerchantUIData(merchantUIDataList);

		GetUIDataRequest mobRequest = new GetUIDataRequest();
		mobRequest.setIntegrationMode("integrationMode");
		mobRequest.setParent("parent");
		mobRequest.setType("type");

		GetUIDataResponse mobResponse = new GetUIDataResponse();
		List<UIData> uiDataList = new ArrayList<UIData>();

		UIData mobUiData1 = new UIData();
		mobUiData1.setDisplayValue("displayValue");
		mobUiData1.setParent("parent");
		mobUiData1.setType("type");
		mobUiData1.setValue("value");
		uiDataList.add(mobUiData1);
		mobResponse.setUiData(uiDataList);
		mobUtil.setMerchantService(merchantService);
		Mockito.when(merchantService.getAllUIData(mobRequest)).thenReturn(mobResponse);

		GetMerchantUIDataRequest request = new GetMerchantUIDataRequest();
		request.setIntegrationMode("integrationMode");
		request.setParent("parent");
		request.setType("type");
		actualResponse = mpService.getUIData(request);

		AssertJUnit.assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void updateMerchantDetails() throws ServiceException, MerchantException {
		
		MerchantUpdateDetailsResponse actualResponse = null;
		MerchantUpdateDetailsResponse expectedResponse = new MerchantUpdateDetailsResponse();
		expectedResponse.setSuccess(true);

		UpdateMerchantDetailsRequest mobRequest = new UpdateMerchantDetailsRequest();
		mobRequest.setMerchantId("merchantId");
		mobRequest.setToken("token");

		UpdateMerchantDetailsResponse mobResponse = new UpdateMerchantDetailsResponse();

		mobUtil.setMerchantService(merchantService);
		mobMapperUtil.setConfig(config);
		mobUtil.setMobMapperUtil(mobMapperUtil);
		Mockito.when(merchantService.updateMerchantDetails(any(UpdateMerchantDetailsRequest.class)))
				.thenReturn(mobResponse);
		
		MerchantUpdateDetailsRequest request =  new MerchantUpdateDetailsRequest();
		request.setBankAccountDetailsDTO(createBankInfo());
		request.setBusinessInformationDTO(createBusinessInfo());
		request.setMerchantId("merchantId");
		request.setToken("token");
		
		Mockito.when(config.getFixedFee()).thenReturn("10");
		Mockito.when(config.getMerchantFee()).thenReturn("10");
		actualResponse = mpService.updateMerchantDetails(request);
		
		Assert.assertEquals(expectedResponse, actualResponse);
	}

	@Test(expectedExceptions = MerchantException.class)
	public void getMerchantDetailHTTPTransportFailTest() throws ServiceException, MerchantException {
		MerchantProfileRequest request = new MerchantProfileRequest();
		request.setMerchantId("merchantId2");
		request.setToken("token2");

		GetMerchantDetailsByMerchantIdRequest mobRequest = new GetMerchantDetailsByMerchantIdRequest();
		mobRequest.setAppName("appName2");
		mobRequest.setMerchantId("merchantId2");
		mobRequest.setToken("token2");

		mobUtil.setMerchantService(merchantService);

		HttpTransportException httpTransportException = new HttpTransportException("HttpTransportException",
				"Httpcode");

		Mockito.when(merchantService.getMerchantDetailsByMerchantId(mobRequest)).thenThrow(httpTransportException);

		mpService.getMerchantDetails(request);

	}

	@Test(expectedExceptions = MerchantException.class)
	public void getMerchantDetailServiceExceptionFailTest() throws ServiceException, MerchantException {
		MerchantProfileRequest request = new MerchantProfileRequest();
		request.setMerchantId("merchantId");
		request.setToken("token");

		GetMerchantDetailsByMerchantIdRequest mobRequest = new GetMerchantDetailsByMerchantIdRequest();
		mobRequest.setAppName("appName");
		mobRequest.setMerchantId("merchantId");
		mobRequest.setToken("token");

		mobUtil.setMerchantService(merchantService);

		ServiceException serviceException = new ServiceException("Service Exception", "serviceCode");

		Mockito.when(merchantService.getMerchantDetailsByMerchantId(mobRequest)).thenThrow(serviceException);

		mpService.getMerchantDetails(request);

	}

}
