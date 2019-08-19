

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.snapdeal.admin.controller.ClientManagementController;
import com.snapdeal.admin.dao.entity.Client.ClientStatus;
import com.snapdeal.admin.dao.entity.Client.ClientType;
import com.snapdeal.admin.request.ActivateClientRequest;
import com.snapdeal.admin.request.CreateClientRequest;
import com.snapdeal.admin.request.DeactivateClientRequest;
import com.snapdeal.admin.request.RegenerateClientKeyRequest;
import com.snapdeal.admin.service.IClientService;
import com.snapdeal.ims.enums.Merchant;

@RunWith(MockitoJUnitRunner.class)
public class ClientManagementControllerTest {
	@InjectMocks
	private ClientManagementController clientManagementController;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private IClientService clientService;

	private MockMvc mockMvc;

	private ClientManagementControllerTestData clientManagementControllerTestData;

	HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(clientManagementController)
				.build();

		clientManagementControllerTestData = new ClientManagementControllerTestData();
	}

	@Ignore
	@Test
	public void testCreateClientSuccess() throws Exception {
		CreateClientRequest request = clientManagementControllerTestData
				.getCreateClientRequest();

		when(clientService.createClient(request)).thenReturn(
				clientManagementControllerTestData.getCreateClientResponse());

		MvcResult result = mockMvc
				.perform(
						post("/api/v1/identity/admin/clients")
								.param("clientName", request.getClientName())
						//		.param("merchant", request.getMerchant())
								.param("clientType", request.getClientType())
								.param("clientPlatform", request.getClientPlatform()))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
/*
	@Test
	public void testGetAllClientSuccess() throws Exception {
		when(clientManagementController.getAllClient(mockedRequest))
				.thenReturn(
						clientManagementControllerTestData
								.getGetAllClientResponse());
		MvcResult result = mockMvc
				.perform(get("/api/v1/identity/admin/clients")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
*/
	@Ignore
	@Test
	public void testgetClientByIdSuccess() throws Exception {
		String clientId = clientManagementControllerTestData.getClientDetails()
				.getClientId();

		when(clientService.getClientById(clientId)).thenReturn(
				clientManagementControllerTestData.getGetClientResponse());

		performTestForGetterMethod("{clientId}", clientId);

	}

	@Ignore
	@Test
	public void testGetClientByNameSuccess() throws Exception {

		String clientName = clientManagementControllerTestData
				.getClientDetails().getClientName();

		when(clientService.getClientByName(clientName))
				.thenReturn(
						clientManagementControllerTestData
								.getGetClientByNameResponse());

		performTestForGetterMethod("name/{clientName}", clientName);
	}

	@Ignore
	@Test
	public void testGetClientByTypeSuccess() throws Exception {

		ClientType clientType = clientManagementControllerTestData
				.getClientDetails().getClientType();

		when(
				clientManagementController.getClientByType(clientType.name(),
						mockedRequest))
				.thenReturn(
						clientManagementControllerTestData
								.getGetClientByTypeResponse());

		performTestForGetterMethod("type/{clientType}", clientType.name());
	}

	@Ignore
	@Test
	public void testGetClientByStatusSuccess() throws Exception {

		ClientStatus clientStatus = clientManagementControllerTestData
				.getClientDetails().getClientStatus();
		when(
				clientManagementController.getClientByStatus(
						clientStatus.name(), mockedRequest)).thenReturn(
				clientManagementControllerTestData
						.getGetClientByStatusResponse());

		performTestForGetterMethod("status/{clientStatus}", clientStatus.name());
	}

	@Ignore
	@Test
	public void testGetClientByMerchantSuccess() throws Exception {

		Merchant merchant = clientManagementControllerTestData.getClientDetails()
				.getMerchant();
		when(
				clientManagementController.getClientByMerchant(merchant.name(),
						mockedRequest)).thenReturn(
				clientManagementControllerTestData
						.getGetClientByClientResponse());

		performTestForGetterMethod("merchant/{merchant}", merchant.name());

	}

	@Ignore
	@Test
	public void testDeactivateClientSuccess() throws Exception {

		DeactivateClientRequest request = clientManagementControllerTestData
				.getDeactivateClientRequest();

		when(
				clientService.deactivateClient(Mockito
						.any(DeactivateClientRequest.class))).thenReturn(
				clientManagementControllerTestData
						.getDeactivateClientResponse());

		MvcResult result = performTestForPutterMethod(
				"{clientId}/status/inactive", request.getClientId());
	}
	
	@Ignore
	@Test
	public void testActivateClientSuccess() throws Exception {

		ActivateClientRequest request = clientManagementControllerTestData
				.getActivateClientRequest();

		when(
				clientService.activateClient(Mockito
						.any(ActivateClientRequest.class))).thenReturn(
				clientManagementControllerTestData.getActivateClientResponse());

		MvcResult result = performTestForPutterMethod(
				"{clientId}/status/active", request.getClientId());

	}

	@Ignore
	@Test
	public void testRegenerateClientKeySuccess() throws Exception {

		RegenerateClientKeyRequest request = clientManagementControllerTestData
				.getRegenerateClientKeyRequest();

		when(
				clientService.regenerateClientKey(Mockito
						.any(RegenerateClientKeyRequest.class))).thenReturn(
				clientManagementControllerTestData
						.getRegenerateClientKeyResponse());

		MvcResult result = performTestForPutterMethod("{clientId}/clientkey",
				request.getClientId());
	}

	private MvcResult performTestForPutterMethod(String key, String value)
			throws Exception {

		MvcResult result = this.mockMvc
				.perform(
						put("/api/v1/identity/admin/clients/" + key, value)
								.contentType(APPLICATION_JSON_UTF8).param(
										"clientId", value))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		return result;
	}

	private void performTestForGetterMethod(String key, String value)
			throws Exception {

		MvcResult result = mockMvc
				.perform(get("/api/v1/identity/admin/clients/" + key, value))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();
	}
}
