package com.snapdeal.opspanel.promotion.clientInit;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.snapdeal.payments.communicator.client.IGetAllRulesByCriteriaClient;
import com.snapdeal.payments.communicator.client.impl.GetAllRulesByCriteriaClientImpl;
import com.snapdeal.payments.dashboard.client.RuleDashboardClient;
import com.snapdeal.payments.disbursement.client.DisbursementClient;
import com.snapdeal.payments.feeengine.client.FeeEngineClient;
import com.snapdeal.payments.fps.client.FPSClient;
import com.snapdeal.payments.p2pengine.external.client.impl.EscrowEngineClient;
import com.snapdeal.payments.p2pengine.external.client.impl.P2PEngineClient;
import com.snapdeal.payments.payables.client.PayablesClient;
import com.snapdeal.payments.pms.client.ProfileManagementClient;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.sdmoney.admin.client.SDMoneyAdminClient;
import com.snapdeal.payments.sdmoney.admin.client.utils.ClientAuthDetails;
import com.snapdeal.payments.sdmoney.client.BankDetailsStoreClient;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.client.utils.ClientDetails;
import com.snapdeal.payments.sdmoneyreport.client.PaymentsReportClient;
import com.snapdeal.payments.settlement.report.client.SettlementReportClient;
import com.snapdeal.payments.tsm.client.ITxnServiceClient;
import com.snapdeal.payments.tsm.client.impl.AbandonTxnServiceClientImpl;
import com.snapdeal.payments.tsm.client.impl.AdminServiceClientImpl;
import com.snapdeal.payments.tsm.client.impl.RedriveTxnServiceClientImpl;
import com.snapdeal.payments.tsm.client.impl.TxnServiceClientImpl;
import com.freecharge.klickpay.client.KlickpayClient;
import com.github.ziplet.filter.compression.CompressingFilter;

@Configuration
public class ClientInitialization<Context extends Enum<Context>> {

	ClientDetails clientDetails = null;

	@Value("${settlementReports.protocol}")
	private String settlementReportsProtocol;

	@Value("${settlementReports.host}")
	private String settlementReportsHost;

	@Value("${settlementReports.clientKey}")
	private String settlementReportsClientKey;

	@Value("${settlementReports.clientName}")
	private String settlementReportsClientName;

	@Value("${reports.protocol}")
	private String reportsProtocol;

	@Value("${reports.host}")
	private String reportsHost;

	@Value("${reports.clientKey}")
	private String reportsClientKey;

	@Value("${reports.clientName}")
	private String reportsClientName;

	// ------------------

	@Value("${wallet.protocol}")
	private String protocol;

	@Value("${wallet.host}")
	private String host;

	@Value("${wallet.clientKey}")
	private String clientKey;

	@Value("${rms.host}")
	private String rmsHost;

	@Value("${rms.port}")
	private String rmsPort;

	@Value("${wallet.clientName}")
	private String clientName;

	@Value("${wallet.xAuthToken}")
	private String xAuthToken;// ="c9762c93a50b42379e2cb3d83f99b7ba";

	@Value("${admin.clientKey}")
	private String admin_clientKey;

	@Value("${admin.clientName}")
	private String admin_clientName;

	@Value("${admin.url}")
	private String admin_url;

	@Value("${admin.connectionRequestTimeout}")
	private String admin_connectionRequest;

	@Value("${admin.connectionTimeout}")
	private String admin_connectionTimeout;

	@Value("${disbursement.host}")
	private String disbursement_host;

	@Value("${disbursement.clientName}")
	private String disbursement_clientName;

	@Value("${disbursement.clientKey}")
	private String disbursement_clientKey;

	@Value("${tsm.ip}")
	private String tsm_ip;

	@Value("${tsm.port}")
	private String tsm_port;

	@Value("${tsm.clientkey}")
	private String tsm_clientKey;

	@Value("${tsm.clientid}")
	private String tsm_clientId;

	@Value( "${payables.clientKey}" )
	private String payables_clientKey;

	@Value( "${payables.clientName}" )
	private String payables_clientName;

	@Value( "${payables.url}" )
	private String payables_url;

	 @Value("${tsm.admin.clientkey}")
	private String tsm_admin_clientKey;

	 @Value("${tsm.admin.clientid}")
	private String tsm_admin_clientId;

	 @Value("${feeengine.clientname}")
	private String fee_engine_clientName;

     @Value("${feeengine.clientkey}")
	private String fee_engine_clientKey;

	@Value("${feeengine.url}")
	private String fee_engine_url;

	@Value( "${p2p.ip}" )
	private String p2p_ip;

	@Value( "${p2p.clientKey}" )
	private String p2p_clientKey;

	@Value( "${p2p.port}" )
	private String p2p_port;

	@Value( "${p2p.clientId}" )
	private String p2p_clientId;
	
	@Value("${pms.client.name}")
	private String pms_client_name = "opsClientName";
	
	@Value("${pms.client.key}")
	private String pms_client_key = "opsClientKey";
	
	@Value("${pms.client.url}")
	private String pms_client_url = "http://10.245.33.27:8080";
	
	@Value("${pms.client.xauthtoken}")
	private String pms_client_xauthtoken = "";

	
	@Value("${rdc.url}")
	private String rdc_url;
	
	
	@Value("${fps.client.id}")
	private String fps_client_id;
	
	@Value("${fps.client.key}")
	private String fps_client_key;
	
	@Value("${fps.client.url}")
	private String fps_client_url;
	
	@Value("${communicator.clientKey}")
	private String communicatorClientKey;

	@Value("${communicator.clientKeyString}")
	private String communicatorClientKeyString;

	@Value("${communicator.url}")
	private String communicatorIp;
	
	@Value("${communicator.port}")
	private String communicatorPort;

	
	@Bean
	@Scope
	public ITxnServiceClient initTSMClient() throws Exception {

		com.snapdeal.payments.tsm.utils.ClientDetails.init(tsm_ip, tsm_port, tsm_clientKey, tsm_clientId);

		return new TxnServiceClientImpl();
	}

	@Bean
	@Scope
	public SDMoneyClient initWallet() throws Exception {
		ClientDetails clientDetails = new ClientDetails();
		StringBuilder url = new StringBuilder();
		url.append(protocol).append(host);
		clientDetails.setUrl(url.toString());
		clientDetails.setClientKey(clientKey);
		clientDetails.setClientName(clientName);
		clientDetails.setXAuthToken(xAuthToken);
		return new SDMoneyClient(clientDetails);
	}

	@Bean
	@Scope
	public SDMoneyAdminClient initAdminClient() throws Exception {
		ClientAuthDetails clientDetails = new ClientAuthDetails();
		clientDetails.setClientKey(admin_clientKey);
		clientDetails.setClientName(admin_clientName);
		clientDetails.setUrl(admin_url);
		clientDetails.setConnectTimeout(Integer.parseInt(admin_connectionTimeout));
		clientDetails.setConnectionRequestTimeout(Integer.parseInt(admin_connectionRequest));
		SDMoneyAdminClient client = new SDMoneyAdminClient(clientDetails);
		return client;

	}

	@Bean
	@Scope
	public PaymentsReportClient initPaymentReportClient() throws Exception {

		com.snapdeal.payments.sdmoneyreport.utils.ClientDetails clientDetails = new com.snapdeal.payments.sdmoneyreport.utils.ClientDetails();
		StringBuilder url = new StringBuilder();
		url.append(reportsProtocol).append(reportsHost);
		clientDetails.setUrl(url.toString());
		clientDetails.setClientKey(reportsClientKey);
		clientDetails.setClientName(reportsClientName);

		return new PaymentsReportClient(clientDetails);
	}

	@Bean
	@Scope
	public RoleMgmtClient initiRoleMgmtClient() throws Exception {
		// new RoleMgmtClient("52.76.171.190","8080",5000);
		return new RoleMgmtClient(rmsHost, rmsPort, 500000);
	}

	@Bean
	@Scope
	public BankDetailsStoreClient initBankStore() throws Exception {
		ClientDetails clientDetails = new ClientDetails();
		StringBuilder url = new StringBuilder();
		url.append(protocol).append(host);
		clientDetails.setUrl(url.toString());
		clientDetails.setClientKey(clientKey);
		clientDetails.setClientName(clientName);
		return new BankDetailsStoreClient(clientDetails);
	}

	@Bean
	@Scope
	public DisbursementClient initDisbursementClient() throws Exception {
		com.snapdeal.payments.disbursement.client.utils.ClientDetails clientDetails = new com.snapdeal.payments.disbursement.client.utils.ClientDetails();
		clientDetails.setUrl(disbursement_host);
		clientDetails.setClientKey(disbursement_clientKey);
		clientDetails.setClientName(disbursement_clientName);
		return new DisbursementClient(clientDetails);
	}

	@Bean
	@Scope
	public SettlementReportClient initSettlementReportsClient() throws Exception {

		com.snapdeal.payments.settlement.report.utils.ClientDetails clientDetails = new com.snapdeal.payments.settlement.report.utils.ClientDetails();
		StringBuilder url = new StringBuilder();
		url.append(settlementReportsProtocol).append(settlementReportsHost);
		clientDetails.setUrl(url.toString());
		clientDetails.setClientKey(settlementReportsClientKey);
		clientDetails.setClientName(settlementReportsClientName);
		return new SettlementReportClient(clientDetails);
	}

	@Bean
	@Scope
	public AbandonTxnServiceClientImpl initAbandonTxnServiceClient() throws Exception {
	   
	   com.snapdeal.payments.tsm.utils.ClientDetails.init(tsm_ip, tsm_port, tsm_admin_clientKey, tsm_admin_clientId);
	   
	   return new AbandonTxnServiceClientImpl();
	   
	}

	@Bean
   @Scope
   public AdminServiceClientImpl initAdminServiceClient() throws Exception {
      
      com.snapdeal.payments.tsm.utils.ClientDetails.init(tsm_ip, tsm_port, tsm_admin_clientKey, tsm_admin_clientId);
      
      return new AdminServiceClientImpl();
      
   }
	
	@Bean
   @Scope
   public RedriveTxnServiceClientImpl initRedriveTxnServiceClient() throws Exception {
      
      com.snapdeal.payments.tsm.utils.ClientDetails.init(tsm_ip, tsm_port, tsm_admin_clientKey, tsm_admin_clientId);
      
      return new RedriveTxnServiceClientImpl();
      
   }

	@Bean
	@Scope
	public PayablesClient initPayablesClient() throws Exception {
		com.snapdeal.payments.payables.client.utils.ClientDetails clientDetails = new com.snapdeal.payments.payables.client.utils.ClientDetails();
		clientDetails.setClientKey( payables_clientKey );
		clientDetails.setClientName( payables_clientName );
		clientDetails.setUrl( payables_url );
		return new PayablesClient( clientDetails );
	}

	@Bean
	@Scope
	public EscrowEngineClient initEscrowEngineClient() throws Exception {
		com.snapdeal.payments.p2pengine.external.utils.ClientDetails clientDetails = com.snapdeal.payments.p2pengine.external.utils.ClientDetails.getInstance();
		clientDetails.init( p2p_ip, p2p_port, p2p_clientId, p2p_clientKey);
		return new EscrowEngineClient();
	}

    @Bean
	@Scope
	public FeeEngineClient initFeeEngineClient() throws Exception {

		FeeEngineClient feeEngineClient = new FeeEngineClient(fee_engine_url, fee_engine_clientName,
				fee_engine_clientKey);
		return feeEngineClient;
	}
    
    @Bean
    @Scope
    public ProfileManagementClient initProfileManagementClient() throws Exception {
    	com.snapdeal.payments.pms.client.utils.ClientDetails clientDetails = new com.snapdeal.payments.pms.client.utils.ClientDetails();
    	clientDetails.setClientKey(pms_client_key);
    	clientDetails.setClientName(pms_client_name);
    	clientDetails.setUrl(pms_client_url);
    	clientDetails.setXAuthToken(pms_client_xauthtoken);
    	
    	return new ProfileManagementClient(clientDetails);
    }
    
    @Bean
    @Scope
    public RuleDashboardClient initRuleDashboardClient() throws Exception {
    	com.snapdeal.payments.dashboard.utils.ClientDetails clientDetails= new com.snapdeal.payments.dashboard.utils.ClientDetails();
    	clientDetails.setUrl(rdc_url);
    	return new RuleDashboardClient(clientDetails);
    }
    
    @Bean
    @Scope
    public FPSClient initFPSsClient() throws Exception {
    	com.snapdeal.payments.fps.client.utils.ClientDetails clientDetails = new com.snapdeal.payments.fps.client.utils.ClientDetails();
    	clientDetails.setClientKey(fps_client_key);
    	clientDetails.setClientName(fps_client_id);
    	clientDetails.setUrl(fps_client_url);
    	return new FPSClient(clientDetails);
    }



	@Bean
	@Scope
	public Filter compressingFilter() {
		CompressingFilter compressingFilter = new CompressingFilter();
		return compressingFilter;
	}
	
	@Bean
	@Scope
	public KlickpayClient initKlickpayClient(){
	    return new KlickpayClient("https://pay-qa.freecharge.in", 50000, 50000, "NBh9oCyo", "ToKQA6");
	}
	
	@Bean
	@Scope
	public IGetAllRulesByCriteriaClient initCommunicatorClient() throws Exception {
		com.snapdeal.payments.communicator.utils.ClientDetails.init(communicatorIp, communicatorPort,"", communicatorClientKeyString);
		return new GetAllRulesByCriteriaClientImpl();
	}

		
}
