package com.snapdeal.payments.view.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.dao.impl.PersistanceManagerImpl;
import com.snapdeal.payments.view.datasource.DataBaseShardRelationMap;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;

@Slf4j
@Component
public class UtilityService {

	@Autowired
	PersistanceManagerImpl persistanceManger ;
	
	@Autowired
	private DataBaseShardRelationMap databaseMap ;
	
	public String healthCheck(){
		Map<String, List<String>> databaseShardMap = databaseMap
				.getDataBaseShardRelationMap();
		Set<String> keySet = databaseShardMap.keySet();
		for(String key : keySet){
			List<String> databaseList = databaseShardMap.get(key) ;
			ViewTypeEnum viewTtpe = null ;
			if(key.equalsIgnoreCase("merchant_view")){
				viewTtpe = ViewTypeEnum.MERCHANTVIEW ;
			}else if(key.equalsIgnoreCase("request_view")){
				viewTtpe = ViewTypeEnum.REQUESTVIEW ;
			}
			for(String shardKey : databaseList){
				 PaymentsViewShardContextHolder.setShardKey(shardKey);
				 if(viewTtpe == ViewTypeEnum.MERCHANTVIEW){
					 persistanceManger.healthCheckForMerchant();
				 }else if(viewTtpe == ViewTypeEnum.REQUESTVIEW){
					 persistanceManger.healthCheckForRequestView();
				 }
				 PaymentsViewShardContextHolder.clearShardKey();
			}
		}
		return PaymentsViewConstants.RESPONSE_200;
	}
}