package com.snapdeal.opspanel.promotion.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGenerationResponse;
import com.snapdeal.opspanel.promotion.Response.OPSPaymentsReportGetResponse;
import com.snapdeal.opspanel.promotion.enums.OPSPaymentsReportType;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGenerationRequest;
import com.snapdeal.opspanel.promotion.request.OPSPaymentsReportGetRequest;
import com.snapdeal.opspanel.promotion.service.OPSPaymentsReportService;
import com.snapdeal.payments.sdmoneyreport.api.model.GetCorpAccountReportRequest;
import com.snapdeal.payments.sdmoneyreport.api.model.GetJobDetailsInTimeDurationRequest;
import com.snapdeal.payments.sdmoneyreport.api.model.GetJobDetailsInTimeDurationResponse;
import com.snapdeal.payments.sdmoneyreport.api.model.GetReportRequest;
import com.snapdeal.payments.sdmoneyreport.client.PaymentsReportClient;
import com.snapdeal.payments.settlement.report.client.SettlementReportClient;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantInvoiceDetailsResponse;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsRequest;
import com.snapdeal.payments.settlement.report.explorer.model.GetMerchantSettlementReportDetailsResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Service("reportService")
public class OPSPaymentsReportServiceImpl implements OPSPaymentsReportService {

	@Autowired
	PaymentsReportClient paymentsReportClient;

	@Autowired
	ReportSubAuthorizationImpl subReportAuthorizationImpl;
	
	@Autowired
    SettlementReportClient SettlementReportClient;

	@Override
	public OPSPaymentsReportGenerationResponse generateReport(
			OPSPaymentsReportGenerationRequest opsPaymentsReportRequest, String reportHead)
					throws WalletServiceException {

		try {

			// ------------------------------
			// date checker
			
			long timediff = opsPaymentsReportRequest.getEndDate().getTime() - opsPaymentsReportRequest.getStartDate().getTime();
			
			if(timediff > (1000*60*60*24)){
				log.info("Sorry ! Date exceeds the limit of one day ");
				throw new WalletServiceException("MT-9005", "Date Limit Excedded");
			}

			// ------------------------------
			
			OPSPaymentsReportGenerationResponse opsPaymentsReportResponse = new OPSPaymentsReportGenerationResponse();

			// Getting the Report Type
			OPSPaymentsReportType reportType = opsPaymentsReportRequest.getOpsPaymentsReportType();

			if (!subReportAuthorizationImpl.authorize(reportHead, reportType)) {
				log.info("Sorry! You are not allowed to access these api's ");
				throw new WalletServiceException("MT-9003", "Unauthorized User");
			}

			GetReportRequest getReportRequest = new GetReportRequest();
			GetCorpAccountReportRequest getCorpAccountReportRequest = new GetCorpAccountReportRequest();

			// Making Request Object
			getReportRequest.setStartDate(opsPaymentsReportRequest.getStartDate());
			getReportRequest.setEndDate(opsPaymentsReportRequest.getEndDate());
			getReportRequest.setOnDemand(opsPaymentsReportRequest.getIsOnDemand());
			getReportRequest.setEmailRequired(opsPaymentsReportRequest.getIsEmailRequired());

			String response = null;
			
			switch (reportType) {

			case ALL_CORP_ACCOUNT_ACTIVITY_REPORT:

				log.info("Generating report for ALL_CORP_ACCOUNT_ACTIVITY_REPORT");

				getCorpAccountReportRequest.setStartDate(opsPaymentsReportRequest.getStartDate());
				getCorpAccountReportRequest.setEndDate(opsPaymentsReportRequest.getEndDate());
				getCorpAccountReportRequest.setOnDemand(opsPaymentsReportRequest.getIsOnDemand());
				getCorpAccountReportRequest.setEmailRequired(opsPaymentsReportRequest.getIsEmailRequired());

				response = paymentsReportClient.getCorpAccountTransactions(getCorpAccountReportRequest);

				opsPaymentsReportResponse.setResponse(response);
				break;

			case SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT:

				log.info("Generating report for SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT");

				getCorpAccountReportRequest.setStartDate(opsPaymentsReportRequest.getStartDate());
				getCorpAccountReportRequest.setEndDate(opsPaymentsReportRequest.getEndDate());
				getCorpAccountReportRequest.setOnDemand(opsPaymentsReportRequest.getIsOnDemand());
				getCorpAccountReportRequest.setEmailRequired(opsPaymentsReportRequest.getIsEmailRequired());
				getCorpAccountReportRequest.setCorpAccountId(opsPaymentsReportRequest.getAccountId());
				getCorpAccountReportRequest.setMerchantId(opsPaymentsReportRequest.getBusinessEntity());

				response = paymentsReportClient.getCorpAccountTransactions(getCorpAccountReportRequest);

				opsPaymentsReportResponse.setResponse(response);
				break;

			case WALLET_LOAD_REPORT:

				log.info("Generating report for WALLET_LOAD_REPORT");

				response = paymentsReportClient.getWalletLoadReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case WALLET_USAGE_REPORT:

				log.info("Generating report for WALLET_USAGE_REPORT");

				response = paymentsReportClient.getWalletUsageReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case WALLET_USAGE_CANCEL_REPORT:

				log.info("Generating report for WALLET_USAGE_CANCEL_REPORT");

				response = paymentsReportClient.getWalletUsageCancelReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case WALLET_WITHDRAW_REPORT:

				log.info("Generating report for WALLET_WITHDRAW_REPORT");

				response = paymentsReportClient.getWalletWithdrawReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case ALL_CREDITS_REPORT:

				log.info("Generating report for ALL_CREDITS_REPORT");

				response = paymentsReportClient.getAllAccountsReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case EXPIRY_REPORT:

				log.info("Generating report for EXPIRY_REPORT");

				response = paymentsReportClient.getExpiryReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case WALLET_CREDIT_REPORT:

				log.info("Generating report for WALLET_CREDIT_REPORT");

				response = paymentsReportClient.getWalletCreditReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case MIRROR_ACCOUNT_REPORT:

				log.info("Generating report for MIRROR_ACCOUNT_REPORT");

				response = paymentsReportClient.mirrorAccountReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case IMPS_REPORT:

				log.info("Generating report for IMPS_REPORT");

				response = paymentsReportClient.getIMPSReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case WORKFLOW_COUNT_REPORT:

				log.info("Generating report for WORKFLOW_COUNT_REPORT");

				response = paymentsReportClient.getWorkflowCountReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case ACCOUNTS_REPORT:

				log.info("Generating report for ACCOUNTS_REPORT");

				response = paymentsReportClient.getAllAccountsReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			case RBI_REPORT:

				log.info("Generating report for RBI_REPORT");

				response = paymentsReportClient.getRBIReport(getReportRequest);
				opsPaymentsReportResponse.setResponse(response);
				break;

			}

			log.info("Successfully generated the report");

			return opsPaymentsReportResponse;

		}
		catch(WalletServiceException walletServiceException){
			throw walletServiceException;
		}
		catch (Exception e) {
			log.info("Exception in generating the report " + e);
			throw new WalletServiceException("MT-9001", "Exception in generating the report");
		}

	}

	@Override
	public OPSPaymentsReportGetResponse getReport(OPSPaymentsReportGetRequest opsPaymentsReportGetRequest,
			String reportHead) throws Exception {

		try {
						
			OPSPaymentsReportGetResponse opsPaymentsReportGetResponse = new OPSPaymentsReportGetResponse();

			// Getting the Date
			Date startDate = opsPaymentsReportGetRequest.getStartDate();

			// Getting the Report Type
			OPSPaymentsReportType reportType = opsPaymentsReportGetRequest.getOpsPaymentsReportType();

			if (!subReportAuthorizationImpl.authorize(reportHead, reportType)) {
				log.info("Sorry! You are not allowed to access these api's ");
				throw new WalletServiceException("MT-9004", "Unauthorized User");
			}

			GetJobDetailsInTimeDurationRequest getJobDetailsInTimeDurationRequest = new GetJobDetailsInTimeDurationRequest();
			GetJobDetailsInTimeDurationResponse getJobDetailsInTimeDurationResponse = new GetJobDetailsInTimeDurationResponse();

			switch (reportType) {

			case ALL_CORP_ACCOUNT_ACTIVITY_REPORT:

				log.info("Getting report for ALL_CORP_ACCOUNT_ACTIVITY_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("corpAccountJob");
				break;

			case SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT:

				log.info("Getting report for SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("corpAccountJob");
				break;

			case WALLET_LOAD_REPORT:

				log.info("Getting report for WALLET_LOAD_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWalletLoadReportJob");
				break;

			case WALLET_USAGE_REPORT:

				log.info("Getting report for WALLET_USAGE_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWalletUsageReportJob");
				break;

			case WALLET_USAGE_CANCEL_REPORT:

				log.info("Getting report for WALLET_USAGE_CANCEL_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWalletUsageCancelReportJob");
				break;

			case WALLET_WITHDRAW_REPORT:

				log.info("Getting report for WALLET_WITHDRAW_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWalletWithdrawReportJob");
				break;

			case ALL_CREDITS_REPORT:

				log.info("Getting report for ALL_CREDITS_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getAllCreditsReportJob");
				break;

			case EXPIRY_REPORT:

				log.info("Getting report for EXPIRY_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getExpiryReportJob");
				break;

			case WALLET_CREDIT_REPORT:

				log.info("Getting report for WALLET_CREDIT_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWalletCreditReportJob");
				break;

			case MIRROR_ACCOUNT_REPORT:

				log.info("Getting report for MIRROR_ACCOUNT_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("mirrorAccountJob");
				break;

			case IMPS_REPORT:

				log.info("Getting report for IMPS_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getIMPSReportJob");
				break;

			case WORKFLOW_COUNT_REPORT:

				log.info("Getting report for WORKFLOW_COUNT_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getWorkflowCountReportJob");
				break;

			case ACCOUNTS_REPORT:

				log.info("Getting report for ACCOUNTS_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("allAccountsJob");
				break;

			case RBI_REPORT:

				log.info("Getting the report for RBI_REPORT");

				getJobDetailsInTimeDurationRequest.setJobName("getRbiReportJob");
				break;

			}

			getJobDetailsInTimeDurationRequest.setLastEvaluated(opsPaymentsReportGetRequest.getLastEvaluated());
			getJobDetailsInTimeDurationRequest.setPageSize(opsPaymentsReportGetRequest.getPageSize());

			if (startDate != null)
				getJobDetailsInTimeDurationRequest.setStartTime(opsPaymentsReportGetRequest.getStartDate());

			if (startDate != null)
				getJobDetailsInTimeDurationRequest.setEndTime(opsPaymentsReportGetRequest.getEndDate());
	
			getJobDetailsInTimeDurationResponse = paymentsReportClient
					.getJobDetailsInTimeDuration(getJobDetailsInTimeDurationRequest);

			opsPaymentsReportGetResponse
					.setReportDetailsList(getJobDetailsInTimeDurationResponse.getReportDetailsList());

			log.info("Successfully getting the report");

			return opsPaymentsReportGetResponse;

		}
		catch(WalletServiceException walletServiceException){
			throw walletServiceException;
		}
		catch (Exception e) {
			log.info("Exception in getting the report " + e.getStackTrace());
			throw new WalletServiceException("MT-9002", "Exception in getting the report");
		}
	}

	@Override
	public GetMerchantSettlementReportDetailsResponse getMerchantSettlementReport(
			GetMerchantSettlementReportDetailsRequest getMerchantsettlementReportDetailsRequest) throws WalletServiceException {

		
		try {
			log.info("Getting the settlement report for merchantId :"+getMerchantsettlementReportDetailsRequest.getMerchantId());
			return SettlementReportClient.getMerchantSettlementReportDetails(getMerchantsettlementReportDetailsRequest);
		} catch (Exception e) {
			log.info("Exception in getting the settlement report :" +e.getClass().getName() + ExceptionUtils.getFullStackTrace(e));
			throw new WalletServiceException("MT-9002", e.getMessage());
		}
	}

	@Override
	public GetMerchantInvoiceDetailsResponse getMerchantInvoiceDetails(GetMerchantInvoiceDetailsRequest request) throws OpsPanelException {
		try {
			return SettlementReportClient.getMerchantInvoiceDetails(request);
		} catch( Exception e ) {
			log.info(  "Exception occurred while getting merchant invoice details " + ExceptionUtils.getFullStackTrace( e ) );
			throw new OpsPanelException( "MT-9003", e.getMessage() );
		}
	}

}
