package com.snapdeal.opspanel.promotion.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.promotion.enums.OPSPaymentsReportType;
import com.snapdeal.opspanel.promotion.service.ReportSubAuthorization;

@Service("subReportAuthorization")
public class ReportSubAuthorizationImpl implements ReportSubAuthorization{

	HashMap<String,HashSet<OPSPaymentsReportType>> accessMap = new HashMap<String,HashSet<OPSPaymentsReportType>>();

	ReportSubAuthorizationImpl(){
	
		// -------------------------------------
		// added head name with corresponding reports api's for authorization

		HashSet<OPSPaymentsReportType> reportHeadOne = new HashSet<OPSPaymentsReportType>();
		reportHeadOne.add(OPSPaymentsReportType.ALL_CORP_ACCOUNT_ACTIVITY_REPORT);
		reportHeadOne.add(OPSPaymentsReportType.SPECIFIC_CORP_ACCOUNT_ACTIVITY_REPORT);
		accessMap.put("CorpAccountReport", reportHeadOne);

		HashSet<OPSPaymentsReportType> reportHeadTwo = new HashSet<OPSPaymentsReportType>();
		reportHeadTwo.add(OPSPaymentsReportType.WALLET_LOAD_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.WALLET_USAGE_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.WALLET_USAGE_CANCEL_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.WALLET_WITHDRAW_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.ALL_CREDITS_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.EXPIRY_REPORT);
		reportHeadTwo.add(OPSPaymentsReportType.WALLET_CREDIT_REPORT);
		accessMap.put("ERPReport", reportHeadTwo);

		HashSet<OPSPaymentsReportType> reportHeadThree = new HashSet<OPSPaymentsReportType>();
		reportHeadThree.add(OPSPaymentsReportType.MIRROR_ACCOUNT_REPORT);
		accessMap.put("MirrorAccountReport", reportHeadThree);

		HashSet<OPSPaymentsReportType> reportHeadFour = new HashSet<OPSPaymentsReportType>();
		reportHeadFour.add(OPSPaymentsReportType.IMPS_REPORT);
		accessMap.put("IMPSReport", reportHeadFour);

		HashSet<OPSPaymentsReportType> reportHeadFive = new HashSet<OPSPaymentsReportType>();
		reportHeadFive.add(OPSPaymentsReportType.WORKFLOW_COUNT_REPORT);
		reportHeadFive.add(OPSPaymentsReportType.ACCOUNTS_REPORT);
		accessMap.put("WalletReport", reportHeadFive);

		HashSet<OPSPaymentsReportType> reportHeadSix = new HashSet<OPSPaymentsReportType>();
		reportHeadSix.add(OPSPaymentsReportType.RBI_REPORT);
		accessMap.put("RBIReport", reportHeadSix);

		// -------------------------------------
	}
	
	public Boolean authorize(String reportHead, OPSPaymentsReportType opsPaymentsReportType) {

		for(Entry<String, HashSet<OPSPaymentsReportType>> accesspair:accessMap.entrySet()){

			HashSet<OPSPaymentsReportType> hashSetOfOPSPaymentsReportType = accesspair.getValue();
			
			for(OPSPaymentsReportType allAuthorizedTypesForUser:hashSetOfOPSPaymentsReportType){
				if(accesspair.getKey().equals(reportHead) && opsPaymentsReportType == allAuthorizedTypesForUser){
					return true;
				}
			}
			
		}
		
		return false;
	}

}
