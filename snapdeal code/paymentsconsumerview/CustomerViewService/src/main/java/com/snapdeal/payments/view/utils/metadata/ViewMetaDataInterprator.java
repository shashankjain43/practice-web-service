package com.snapdeal.payments.view.utils.metadata;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.metadata.aggregator.PayablesGlobalMetaData;
import com.snapdeal.payments.metadata.escrowengine.EscrowEngineMetadata;
import com.snapdeal.payments.metadata.escrowengine.OrderDetails;
import com.snapdeal.payments.metadata.interpreter.DisbursementMetaDataInterpreter;
import com.snapdeal.payments.metadata.interpreter.GlobalMetadataInterpreter;
import com.snapdeal.payments.metadata.interpreter.MetadataInterpreter;
import com.snapdeal.payments.metadata.interpreter.OPSGlobalMetaDataInterpreter;
import com.snapdeal.payments.metadata.ops.OPSGLobalMetadata;
import com.snapdeal.payments.metadata.p2pengine.P2PEngineMetadata;
import com.snapdeal.payments.metadata.tsm.GlobalTxMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.enums.MerchantTxnSource;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.commons.utils.JsonUtils;
import com.snapdeal.payments.view.entity.LoadCashTxnEntity;
import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.PartyDetailsDto;

@Slf4j
@Service
public class ViewMetaDataInterprator {

	private GlobalMetadataInterpreter globalMetadataInterpreter;

	private MetadataInterpreter<DisbursementMetadata> deInterprator;

	private MetadataInterpreter<OPSGLobalMetadata> opsInterprator;
	
	private ObjectMapper mapper;
	

	@PostConstruct
	public void init() {
		globalMetadataInterpreter = new GlobalMetadataInterpreter();
		deInterprator = DisbursementMetaDataInterpreter.getInstance();
		opsInterprator = OPSGlobalMetaDataInterpreter.getInstance();
		mapper = new ObjectMapper();
	}
	public P2PEngineMetadata getP2PEngineMetaData(String jsonString){
		return getGlobalMetData(jsonString,P2PEngineMetadata.class);
	}


	public DisbursementMetadata getDisburseMetaData(String meteData) {
		return deInterprator.readjson(meteData);

	}

	@SuppressWarnings("unchecked")
	public Map<String, PartyDetailsDto> getDisplayInfo(String displayInfo,int recur) {
		Map<String, PartyDetailsDto> partyDetailsDtoMap = new HashMap<String, PartyDetailsDto>();
		if (StringUtils.isBlank(displayInfo))
			return partyDetailsDtoMap;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> srcDetails = new HashMap<String, Object>();
		Map<String, Object> destDetails = new HashMap<String, Object>();
		Map<String, Object> displayObject = new HashMap<String, Object>();
		try {
			displayObject = mapper.readValue(displayInfo, Map.class);
			if(displayObject.get(PaymentsViewConstants.SRC_PATY_INFO) !=null )
			{
			srcDetails  = JsonUtils.deSerialize(JsonUtils.serialize(displayObject
					.get(PaymentsViewConstants.SRC_PATY_INFO)),srcDetails.getClass()) ;
			}
			if(displayObject.get(PaymentsViewConstants.DEST_PATY_INFO) !=null ){
			destDetails =  JsonUtils.deSerialize(JsonUtils.serialize(displayObject
						.get(PaymentsViewConstants.DEST_PATY_INFO)),destDetails.getClass());;
			}
			partyDetailsDtoMap.put(PaymentsViewConstants.SRC_PATY_INFO,
					getPartyDetails(srcDetails));
			partyDetailsDtoMap.put(PaymentsViewConstants.DEST_PATY_INFO,
					getPartyDetails(destDetails));

		} catch (Throwable  e) {
				try {
					displayObject = mapper.readValue(displayInfo, Map.class);
				
				if(displayObject.get(PaymentsViewConstants.SRC_PATY_INFO) !=null )
				{
				srcDetails  = JsonUtils.deSerialize(displayObject
						.get(PaymentsViewConstants.SRC_PATY_INFO).toString(),srcDetails.getClass()) ;
				}
				if(displayObject.get(PaymentsViewConstants.DEST_PATY_INFO) !=null ){
				destDetails =  JsonUtils.deSerialize(displayObject
							.get(PaymentsViewConstants.DEST_PATY_INFO).toString(),destDetails.getClass());;
				}
				partyDetailsDtoMap.put(PaymentsViewConstants.SRC_PATY_INFO,
						getPartyDetails(srcDetails));
				partyDetailsDtoMap.put(PaymentsViewConstants.DEST_PATY_INFO,
						getPartyDetails(destDetails));
				} catch (Throwable e1) {
					displayInfo = displayInfo.replaceAll("\\\\", "");
					 if (recur == 0) {
							partyDetailsDtoMap = getDisplayInfo(displayInfo, recur + 1);
						}
				}
			log.error("EXCEPTION IN DISPLAY MESSGAE " + e.getMessage());
		}
		//log.info(displayObject.toString());
		return partyDetailsDtoMap;
	}
	
	@SuppressWarnings("rawtypes")
	private PartyDetailsDto getPartyDetails(Map partyDetails){
		PartyDetailsDto partyDetailsDto = new PartyDetailsDto();
		if(partyDetails ==null || partyDetails.isEmpty())
			return partyDetailsDto;
		if(partyDetails.get(PaymentsViewConstants.MOBILE)!=null){
			partyDetailsDto.setMobileNumber(partyDetails.get(PaymentsViewConstants.MOBILE).toString());
		}
		if(partyDetails.get("contactName")!=null){
				partyDetailsDto.setName(partyDetails.get("contactName").toString());
		}else if(partyDetails.get(PaymentsViewConstants.NAME)!=null){
				partyDetailsDto.setName(partyDetails.get(PaymentsViewConstants.NAME).toString());
		}
		if(partyDetails.get(PaymentsViewConstants.TAG)!=null){
			partyDetailsDto.setPartyTag(partyDetails.get(PaymentsViewConstants.TAG).toString());
		}
		return partyDetailsDto ;
	}

	public OPSGLobalMetadata getOPSMetaData(String metaData) {
		return opsInterprator.readjson(metaData);
	}

	public <T extends GlobalTxMetadata> T getGlobalMetData(String metaData,
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

	public TransactionDetailsEntity fillAggregatorMetaData(String meteData,
			TransactionDetailsEntity entity) {
		entity.setSource(MerchantTxnSource.AGGREGATOR.getMerchantTxnSource());
		GlobalTxMetadata metadata = getGlobalMetData(meteData,
				GlobalTxMetadata.class);
		fillGlblMetaData(metadata, entity);
		entity.setDisplayInfo(getDisplayInfoAsString(metadata));
		return entity;

	}
	private String getDisplayInfoAsString(GlobalTxMetadata metaData){
		Map<String,String> displayInfoDetails = new HashMap<String, String>();
		displayInfoDetails.put(PaymentsViewConstants.CUSTOMER_NAME,metaData.getDisplayName());
		displayInfoDetails.put(PaymentsViewConstants.EMAIL,metaData.getEmail());
		displayInfoDetails.put(PaymentsViewConstants.MOBILE,metaData.getMobileNumber());
		displayInfoDetails.put(PaymentsViewConstants.USER_ID,metaData.getUserId());
		return JsonUtils.serialize(displayInfoDetails) ;
	}
	private String getEscrowDisplayInfo(EscrowEngineMetadata metaData){
		Map<String,String> displayInfoDetails = new HashMap<String,String>();
		JsonUtils.deSerialize(getDisplayInfoAsString(metaData),displayInfoDetails.getClass());
		displayInfoDetails.put(PaymentsViewConstants.USER_ID,metaData.getSourceImsId());
		return JsonUtils.serialize(displayInfoDetails) ;
	}

	public TransactionDetailsEntity fillOPSMetaData(String meteData,
			TransactionDetailsEntity entity) {
		OPSGLobalMetadata opsmetdata = getOPSMetaData(meteData);

		entity.setSource(MerchantTxnSource.OPS.getMerchantTxnSource());
		fillOPSMetaData(opsmetdata, entity);
		fillGlblMetaData(opsmetdata, entity);
		entity.setDisplayInfo(getDisplayInfoAsString(opsmetdata));
		return entity;

	}
	public TransactionDetailsEntity fillEscrowMetaData(String metaData,
			TransactionDetailsEntity entity){
		EscrowEngineMetadata escrowMetaData = getGlobalMetData(metaData,EscrowEngineMetadata.class);
		entity.setSource(MerchantTxnSource.ESCROW.getMerchantTxnSource());
		fillGlblMetaData(escrowMetaData, entity);
		entity.setDisplayInfo(getEscrowDisplayInfo(escrowMetaData));
		entity.setMerchantTxnId(escrowMetaData.getTxnDisplayId());
		entity.setUserId(escrowMetaData.getSourceImsId());
		entity.setPlatform(escrowMetaData.getPlatformId());
		return entity;
	}
	public TransactionDetailsEntity fillP2MMetaData(String meteData,
			TransactionDetailsEntity entity, TransactionType type) {
		P2PEngineMetadata p2pEngineMetaData = getP2PEngineMetaData(meteData);
		entity.setMerchantTxnId(p2pEngineMetaData.getOrderId());
		Map<String, PartyDetailsDto> displayInfo = getDisplayInfo(p2pEngineMetaData.getDisplayInfo(),0) ;
		PartyDetailsDto srcParty = displayInfo.get(PaymentsViewConstants.SRC_PATY_INFO) ;
		PartyDetailsDto  destParty = displayInfo.get(PaymentsViewConstants.DEST_PATY_INFO) ;
		//String merchantTag = null ;
		if( type ==  TransactionType.P2P_REQUEST_MONEY){
			entity.setUserId(p2pEngineMetaData.getReceiverId());
			if(srcParty!=null)
			entity.setMerchantTag(srcParty.getPartyTag());
			if(destParty!=null)
			entity.setCustomerName(destParty.getName());
			entity.setDisplayInfo(getDisplayInfoMap(destParty,p2pEngineMetaData.getReceiverId()));
		}else if(type ==  TransactionType.P2P_SEND_MONEY){
			if(destParty!=null)
			entity.setMerchantTag(destParty.getPartyTag());
			entity.setUserId(p2pEngineMetaData.getSenderIMSId());
			entity.setDisplayInfo(getDisplayInfoMap(srcParty,p2pEngineMetaData.getSenderIMSId()));
			if(srcParty!=null)
			entity.setCustomerName(srcParty.getName());
		}else{
			entity.setCustomerName(p2pEngineMetaData.getDisplayName());
		}
		//entity.setDisplayInfo(p2pEngineMetaData.getDisplayInfo());
		entity.setProductId(p2pEngineMetaData.getProductId());
		entity.setPlatform(p2pEngineMetaData.getPlateForm());
		entity.setOsVersion(p2pEngineMetaData.getOsVersion());
		//entity.setCustmerIP(p2pEngineMetaData.getSenderIpAddress());
		entity.setLocation(p2pEngineMetaData.getLocation());
		entity.setSource(MerchantTxnSource.P2M.getMerchantTxnSource());
		//entity.setMerchantTag(p2pEngineMetaData.getMerchantTag());
		return entity;
	}
	private String getDisplayInfoMap(PartyDetailsDto dto,String userId){
		Map<String,String> displayInfoMap = new HashMap<String, String>();
		displayInfoMap.put(PaymentsViewConstants.CUSTOMER_NAME,dto.getName());
		displayInfoMap.put(PaymentsViewConstants.EMAIL,dto.getEmailId());
		displayInfoMap.put(PaymentsViewConstants.MOBILE,dto.getMobileNumber());
		displayInfoMap.put(PaymentsViewConstants.USER_ID,userId);
		displayInfoMap.put(PaymentsViewConstants.TAG,dto.getPartyTag()) ;
		return JsonUtils.serialize(displayInfoMap) ;
	}

	public void fillGlblMetaData(GlobalTxMetadata metadata,
			TransactionDetailsEntity entity) {
		entity.setMerchantTxnId(metadata.getMerchantOrderId());
		entity.setUserId(metadata.getUserId());
		entity.setProductId(metadata.getProductId());
		entity.setPlatform(metadata.getPlateForm());
		entity.setOsVersion(metadata.getOsVersion());
		entity.setCustmerIP(metadata.getCoustmerIp());
		entity.setLocation(metadata.getLocation());
		entity.setCustomerName(metadata.getDisplayName());
	}

	public void filledWithMetadataOfPayable(NotificationMessage notifactionMsg,
			TransactionDetailsEntity entity) {
		PayablesGlobalMetaData payblesmetdata = getGlobalMetData(
				notifactionMsg.getGlobalTxnMetaData(),
				PayablesGlobalMetaData.class);
		fillPayableMetaData(payblesmetdata, entity);
	}

	public void fillPayableMetaData(PayablesGlobalMetaData metadata,
			TransactionDetailsEntity entity) {
		log.info("setting info sucessfully for merchant fee");
		entity.setMerchantFee(metadata.getMerchantFee());
		entity.setServiceTax(metadata.getServiceTax());
		entity.setSwachBharatCess(metadata.getSwachhBharatCess());
		entity.setNetDeduction(metadata.getNetDeduction());
		entity.setTxnAmount(metadata.getTotalTransactionAmount());
		entity.setAmountPayable(metadata.getMerchantPayableAmount());
	}

	public void fillOPSMetaData(OPSGLobalMetadata metadata,
			TransactionDetailsEntity entity) {
		entity.setStoreId(metadata.getStoreId());
		entity.setStoreName(metadata.getStoreName());
		entity.setTerminalId(metadata.getTerminalId());
	}
	
	public void fillLoadCashMetaData(String txnMetaData,
			LoadCashTxnEntity entity) {
		// TODO Auto-generated method stub
	}

}
