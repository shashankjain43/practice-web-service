package com.snapdeal.opspanel.userpanel.service;

import java.util.List;

import com.snapdeal.ims.request.AbusiveLanguageWordRequest;
import com.snapdeal.ims.request.AmlWordRequest;
import com.snapdeal.ims.request.DeleteAbusiveWordRequest;
import com.snapdeal.ims.request.GetAmlOrAbusiveWordlistRequest;
import com.snapdeal.ims.request.UpdateAbusiveWordRequest;
import com.snapdeal.ims.response.AddAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.DeleteAbusiveOrAmlWordResponse;
import com.snapdeal.ims.response.GetAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.UpdateAbusiveOrAmlWordResponse;
import com.snapdeal.opspanel.audit.response.AuditResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.response.AMLLastUpdatedResponse;

public interface AmlAbusiveServices {
	
	public AddAmlOrAbusiveWordResponse addAbusiveWords(AbusiveLanguageWordRequest request) throws OpsPanelException;
	
	public AddAmlOrAbusiveWordResponse uploadAmlWordFile(AmlWordRequest request) throws OpsPanelException;
	
	public DeleteAbusiveOrAmlWordResponse deleteAbusiveWord(DeleteAbusiveWordRequest request) throws OpsPanelException;
	
	public GetAmlOrAbusiveWordResponse getAmlorAbusiveWord(GetAmlOrAbusiveWordlistRequest request) throws OpsPanelException;
	
	public UpdateAbusiveOrAmlWordResponse updateAbusiveWords(UpdateAbusiveWordRequest request) throws OpsPanelException;
	
	public AMLLastUpdatedResponse getLastUpdated() throws OpsPanelException;
	
}
