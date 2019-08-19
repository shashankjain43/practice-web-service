package com.snapdeal.payments.view.commons.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.snapdeal.payments.disbursement.model.type.TSMMetaDataType;
import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.metadata.aggregator.CustomerPaymentsMetadata;
import com.snapdeal.payments.metadata.aggregator.PayablesGlobalMetaData;
import com.snapdeal.payments.metadata.interpreter.DisbursementMetaDataInterpreter;
import com.snapdeal.payments.metadata.interpreter.GlobalMetadataInterpreter;
import com.snapdeal.payments.metadata.interpreter.MetadataInterpreter;
import com.snapdeal.payments.metadata.interpreter.OPSGlobalMetaDataInterpreter;
import com.snapdeal.payments.metadata.ops.OPSGLobalMetadata;
import com.snapdeal.payments.metadata.p2pengine.P2PEngineMetadata;
import com.snapdeal.payments.metadata.tsm.GlobalTxMetadata;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;

public class TSMMetaDataParser {

	private static GlobalMetadataInterpreter globalMetadataInterpreter = 
			new GlobalMetadataInterpreter();

	private static MetadataInterpreter<DisbursementMetadata> deInterprator = 
			DisbursementMetaDataInterpreter.getInstance();

	private static MetadataInterpreter<OPSGLobalMetadata> opsInterprator = 
			OPSGlobalMetaDataInterpreter.getInstance();

	
	public static P2PEngineMetadata getP2PEngineMetaData(String jsonString){
		return getGlobalMetData(jsonString,P2PEngineMetadata.class);
	}

	public static OPSGLobalMetadata getOPSMetaData(String metaData) {
		return opsInterprator.readjson(metaData);
	}

	public static DisbursementMetadata getDisburseMetaData(String meteData) throws 
				JsonParseException,JsonMappingException,IOException
	{
		
		final ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
			map = mapper.readValue(meteData,
					new TypeReference<Map<String, String>>() {
					});
	
		
		DisbursementMetadata metaData = new DisbursementMetadata();
		metaData.setTransactionReference(map
				.get(TSMMetaDataType.TransactionReference.name()));
		if (map.containsKey(TSMMetaDataType.SettlementStartTime.name())) {
			metaData.setSettlementStartTime(map
					.get(TSMMetaDataType.SettlementStartTime.name()));
		}
		metaData.setSettlementEndTime(map.get(TSMMetaDataType.SettlementEndTime.name()));
		metaData.setDomain(map.get(TSMMetaDataType.Domain.name()));
		metaData.setTransactionId(map.get(TSMMetaDataType.TransactionId.name()));
		metaData.setTransactionAmount(map.get(TSMMetaDataType.TransactionAmount.name()));
		metaData.setMerchantDisbursementId(map.get(TSMMetaDataType.MerchantDisbursementId.name()));
		metaData.setDisbursementType(map.get(TSMMetaDataType.DisbursementType.name()));
		metaData.setTransactionStatus(map.get(TSMMetaDataType.TransactionStatus.name()));
		metaData.setBankTransactionId(map.get(TSMMetaDataType.BankTransactionId.name()));
		metaData.setMerchantId(map.get(TSMMetaDataType.MerchantId.name()));
		metaData.setTransactionTimeStamp(map.get(TSMMetaDataType.TransactionTimeStamp.name()));
		return metaData;

	}
	public static CustomerPaymentsMetadata getCustomerPaymentMetaData(String metaData){
		CustomerPaymentsMetadata metadata = getGlobalMetData(metaData,
				CustomerPaymentsMetadata.class);
		return metadata ;
	}
	public static PayablesGlobalMetaData getPayableMetaData(String metaData){
		PayablesGlobalMetaData payblesmetdata = getGlobalMetData(
				metaData,
			PayablesGlobalMetaData.class);
		return payblesmetdata ;
	}
	public static <T extends GlobalTxMetadata> T getGlobalMetData(String metaData,
			Class<T> type) {
		T glblMetaData = null;
		try {
			glblMetaData = type.cast(globalMetadataInterpreter
					.readjson(metaData));
		} catch (ClassCastException e) {
			throw e;
		} catch (RuntimeException e) {
			PaymentsViewGenericException ex = new PaymentsViewGenericException(
					PaymentsViewServiceExceptionCodes.JSON_PARSE_EXCEPTION
							.errCode(),
					e.getMessage(), ExceptionType.JSON_PARSE_EXCEPTION);

			throw ex;
		}
		return glblMetaData;
	}
	
	
}
