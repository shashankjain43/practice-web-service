package com.snapdeal.vanila.utils;

import com.google.common.base.Joiner;
import com.snapdeal.mob.dto.TerminalInfoDTO;
import com.snapdeal.mob.response.GetTerminalInfoByMerchantIdResponse;

public class MOBUtils {
	
	public static StringBuffer getBufferForCSV(GetTerminalInfoByMerchantIdResponse response){
		String[] header =  {"merchantId","providerMerchantId","terminalId","platformId","delaerId"};	
		
		StringBuffer sb = new StringBuffer();
		sb.append(Joiner.on(",").join(header));
		sb.append("\r\n");
		
		for(TerminalInfoDTO dto : response.getTerminalInfoList()){
			sb.append(dto.getMerchantId());
			sb.append(",");
			sb.append(dto.getProviderMerchantId());
			sb.append(",");
			sb.append(dto.getTerminalId());
			sb.append(",");
			sb.append(dto.getPlatformId());
			sb.append(",");
			sb.append(dto.getDealerId());
			sb.append("\r\n");
		}
		
		return sb;
		
	}

}
