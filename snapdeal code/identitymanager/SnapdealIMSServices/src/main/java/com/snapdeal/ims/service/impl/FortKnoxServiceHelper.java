package com.snapdeal.ims.service.impl;

import com.klickpay.fortknox.MergeType;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.service.IFortKnoxService;
import com.snapdeal.ims.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FortKnoxServiceHelper {

   @Autowired
   private IFortKnoxService fortKnoxService;

   @Autowired
   private IUserService userService;

   
   
   public void createFortKnoxTask(UserDetailsDTO userDetailsDTO) {
	   createFortKnoxTask(userDetailsDTO, null);
   }
   
	/**
	 * Helper method to make a call to merge cards.
	 * 
	 * @param userDetailsDTO
	 * @param mergeType
	 */
   
   public void createFortKnoxTask(UserDetailsDTO userDetailsDTO, MergeType mergeType) {

	   boolean mergeCardEnable = Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.MERGE_CARD_ENABLED));
      if (mergeCardEnable) {
         FortKnoxRequest fortKnoxRequest = new FortKnoxRequest();
         fortKnoxRequest.setEmailId(userDetailsDTO.getEmailId());
         fortKnoxRequest.setUserId(userDetailsDTO.getUserId());
         if(mergeType!=null){
        	 fortKnoxRequest.setTaskId(userDetailsDTO.getUserId()+"_"+mergeType.toString()); 
         }else{
             fortKnoxRequest.setTaskId(userDetailsDTO.getUserId());        	 
         }
         fortKnoxRequest.setSdUserId(String.valueOf(userDetailsDTO
					.getSdUserId()));
		 fortKnoxRequest.setFcUserId(String.valueOf(userDetailsDTO
					.getFcUserId()));
		 fortKnoxRequest.setMergeType(mergeType);
		 
         fortKnoxService.createFortKnotTask(fortKnoxRequest);
      } else {
         log.warn("Card merge api is not enabled.");
      }
   }

}
