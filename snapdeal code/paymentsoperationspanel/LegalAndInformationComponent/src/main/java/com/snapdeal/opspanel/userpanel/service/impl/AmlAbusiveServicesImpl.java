package com.snapdeal.opspanel.userpanel.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.client.IAmlAndAbusiveServiceClient;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.AbusiveLanguageWordRequest;
import com.snapdeal.ims.request.AmlWordRequest;
import com.snapdeal.ims.request.DeleteAbusiveWordRequest;
import com.snapdeal.ims.request.GetAmlOrAbusiveWordlistRequest;
import com.snapdeal.ims.request.UpdateAbusiveWordRequest;
import com.snapdeal.ims.response.AddAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.DeleteAbusiveOrAmlWordResponse;
import com.snapdeal.ims.response.GetAmlOrAbusiveWordResponse;
import com.snapdeal.ims.response.UpdateAbusiveOrAmlWordResponse;
import com.snapdeal.opspanel.audit.dao.GenericAuditDao;
import com.snapdeal.opspanel.audit.entity.AuditEntity;
import com.snapdeal.opspanel.audit.response.AuditResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.response.AMLLastUpdatedResponse;
import com.snapdeal.opspanel.userpanel.service.AmlAbusiveServices;

@Slf4j
@Service("amlAbusiveServices")
public class AmlAbusiveServicesImpl implements AmlAbusiveServices{

	@Autowired
	IAmlAndAbusiveServiceClient amlAndAbusiveServiceClient;
	
	@Autowired
	GenericAuditDao genericAuditDao;
	
	public static final String AML_UPLOAD = "amlAbusiveServicesUpload";

	@Override
	public AddAmlOrAbusiveWordResponse addAbusiveWords(AbusiveLanguageWordRequest request) throws OpsPanelException{

		AddAmlOrAbusiveWordResponse response = new AddAmlOrAbusiveWordResponse();
		try {
			response = amlAndAbusiveServiceClient.addAbusiveWords(request);
		} catch (HttpTransportException hte) {
			log.info("\n HttpTransportException while addAbusiveWords ... WILL BE RETRIED...");
			log.info("Exception : [" + hte.getErrCode() + " , " + hte.getErrMsg() + "]");
			try {
				response = amlAndAbusiveServiceClient.addAbusiveWords(request);
			} catch (HttpTransportException hte2) {
				log.info("\n HttpTransportException while addAbusiveWords ... FAILURE...");
				log.info("Exception on RETRY : [" + hte2.getErrCode() + " , " + hte2.getErrMsg() + "]");
				throw new OpsPanelException(hte2.getErrCode().toString(), hte2.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");

			} catch (ServiceException se){
				log.info("\n ServiceException while addAbusiveWords ... FAILURE...");
				log.info("Exception on RETRY: [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
				throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
			} catch(Exception e){
				log.info("Other Exception while calling addAbusiveWords ... FAILURE ...");
				log.info("Exception : [" + e.getMessage() + "]");
				throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			}
		} catch (ServiceException se){
			log.info("\n ServiceException while addAbusiveWords ... FAILURE...");
			log.info("Exception : [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
			throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
		} catch(Exception e){
			log.info("Other Exception while calling addAbusiveWords ... FAILURE ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		}

		return response;

	}

	@Override
	public AddAmlOrAbusiveWordResponse uploadAmlWordFile(AmlWordRequest request) throws OpsPanelException{

		AddAmlOrAbusiveWordResponse response = new AddAmlOrAbusiveWordResponse();
		try {
			response = amlAndAbusiveServiceClient.uploadAmlWordFile(request);
		} catch (HttpTransportException hte) {
			log.info("\n HttpTransportException while uploadAmlWordFile ... WILL BE RETRIED...");
			log.info("Exception : [" + hte.getErrCode() + " , " + hte.getErrMsg() + "]");
			try {
				response = amlAndAbusiveServiceClient.uploadAmlWordFile(request);
			} catch (HttpTransportException hte2) {
				log.info("\n HttpTransportException while uploadAmlWordFile ... FAILURE...");
				log.info("Exception on RETRY : [" + hte2.getErrCode() + " , " + hte2.getErrMsg() + "]");
				throw new OpsPanelException(hte2.getErrCode().toString(), hte2.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");

			} catch (ServiceException se){
				log.info("\n ServiceException while uploadAmlWordFile ... FAILURE...");
				log.info("Exception on RETRY: [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
				throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
			} catch (IOException ioe){
				log.info("\n IOException while uploadAmlWordFile ... FAILURE...");
				log.info("Exception on RETRY : [" + ioe.getMessage() + "]");
				throw new OpsPanelException("IO_EXCEPTION", ioe.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			} catch(Exception e){
				log.info("Other Exception while calling uploadAmlWordFile ... FAILURE ...");
				log.info("Exception : [" + e.getMessage() + "]");
				throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			}
		} catch (ServiceException se){
			log.info("\n ServiceException while uploadAmlWordFile ... FAILURE...");
			log.info("Exception : [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
			throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
		} catch (IOException ioe){
			log.info("\n IOException while uploadAmlWordFile ... FAILURE...");
			log.info("Exception : [" + ioe.getMessage() + "]");
			throw new OpsPanelException("IO_EXCEPTION", ioe.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		} catch(Exception e){
			log.info("Other Exception while calling uploadAmlWordFile ... FAILURE ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		}

		return response;

	}


	@Override
	public DeleteAbusiveOrAmlWordResponse deleteAbusiveWord(DeleteAbusiveWordRequest request) throws OpsPanelException{

		DeleteAbusiveOrAmlWordResponse response = new DeleteAbusiveOrAmlWordResponse();
		try {
			response = amlAndAbusiveServiceClient.deleteAbusiveWord(request);
		} catch (HttpTransportException hte) {
			log.info("\n HttpTransportException while deleteAbusiveWord ... WILL BE RETRIED...");
			log.info("Exception : [" + hte.getErrCode() + " , " + hte.getErrMsg() + "]");
			try {
				response = amlAndAbusiveServiceClient.deleteAbusiveWord(request);
			} catch (HttpTransportException hte2) {
				log.info("\n HttpTransportException while deleteAbusiveWord ... FAILURE...");
				log.info("Exception on RETRY : [" + hte2.getErrCode() + " , " + hte2.getErrMsg() + "]");
				throw new OpsPanelException(hte2.getErrCode().toString(), hte2.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");

			} catch (ServiceException se){
				log.info("\n ServiceException while deleteAbusiveWord ... FAILURE...");
				log.info("Exception on RETRY: [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
				throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
			} catch(Exception e){
				log.info("Other Exception while calling deleteAbusiveWord ... FAILURE ...");
				log.info("Exception : [" + e.getMessage() + "]");
				throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			}
		} catch (ServiceException se){
			log.info("\n ServiceException while deleteAbusiveWord ... FAILURE...");
			log.info("Exception : [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
			throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
		} catch(Exception e){
			log.info("Other Exception while calling deleteAbusiveWord ... FAILURE ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		}

		return response;

	}
	
	@Override
	public GetAmlOrAbusiveWordResponse getAmlorAbusiveWord(GetAmlOrAbusiveWordlistRequest request) throws OpsPanelException{

		GetAmlOrAbusiveWordResponse response = new GetAmlOrAbusiveWordResponse();
		try {
			response = amlAndAbusiveServiceClient.getAmlorAbusiveWord(request);
		} catch (HttpTransportException hte) {
			log.info("\n HttpTransportException while getAmlorAbusiveWord ... WILL BE RETRIED...");
			log.info("Exception : [" + hte.getErrCode() + " , " + hte.getErrMsg() + "]");
			try {
				response = amlAndAbusiveServiceClient.getAmlorAbusiveWord(request);
			} catch (HttpTransportException hte2) {
				log.info("\n HttpTransportException while getAmlorAbusiveWord ... FAILURE...");
				log.info("Exception on RETRY : [" + hte2.getErrCode() + " , " + hte2.getErrMsg() + "]");
				throw new OpsPanelException(hte2.getErrCode().toString(), hte2.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");

			} catch (ServiceException se){
				log.info("\n ServiceException while getAmlorAbusiveWord ... FAILURE...");
				log.info("Exception on RETRY: [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
				throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
			} catch(Exception e){
				log.info("Other Exception while calling getAmlorAbusiveWord ... FAILURE ...");
				log.info("Exception : [" + e.getMessage() + "]");
				throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			}
		} catch (ServiceException se){
			log.info("\n ServiceException while getAmlorAbusiveWord ... FAILURE...");
			log.info("Exception : [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
			throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
		} catch(Exception e){
			log.info("Other Exception while calling getAmlorAbusiveWord ... FAILURE ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		}

		return response;

	}
	
	@Override
	public UpdateAbusiveOrAmlWordResponse updateAbusiveWords(UpdateAbusiveWordRequest request) throws OpsPanelException{

		UpdateAbusiveOrAmlWordResponse response = new UpdateAbusiveOrAmlWordResponse();
		try {
			response = amlAndAbusiveServiceClient.updateAbusiveWords(request);
		} catch (HttpTransportException hte) {
			log.info("\n HttpTransportException while updateAbusiveWords ... WILL BE RETRIED...");
			log.info("Exception : [" + hte.getErrCode() + " , " + hte.getErrMsg() + "]");
			try {
				response = amlAndAbusiveServiceClient.updateAbusiveWords(request);
			} catch (HttpTransportException hte2) {
				log.info("\n HttpTransportException while updateAbusiveWords ... FAILURE...");
				log.info("Exception on RETRY : [" + hte2.getErrCode() + " , " + hte2.getErrMsg() + "]");
				throw new OpsPanelException(hte2.getErrCode().toString(), hte2.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");

			} catch (ServiceException se){
				log.info("\n ServiceException while updateAbusiveWords ... FAILURE...");
				log.info("Exception on RETRY: [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
				throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
			} catch(Exception e){
				log.info("Other Exception while calling updateAbusiveWords ... FAILURE ...");
				log.info("Exception : [" + e.getMessage() + "]");
				throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
			}
		} catch (ServiceException se){
			log.info("\n ServiceException while updateAbusiveWords ... FAILURE...");
			log.info("Exception : [" + se.getErrCode() + " , " + se.getErrMsg() + "]");
			throw new OpsPanelException(se.getErrCode().toString(), se.getErrMsg(), "AML_AND_ABUSIVE_SERVICE");
		} catch(Exception e){
			log.info("Other Exception while calling updateAbusiveWords ... FAILURE ...");
			log.info("Exception : [" + e.getMessage() + "]");
			throw new OpsPanelException("RUN_TIME_EXCEPTION", e.getMessage(), "AML_AND_ABUSIVE_SERVICE");
		}

		return response;

	}

	@Override
	public AMLLastUpdatedResponse getLastUpdated() throws OpsPanelException {
		AuditEntity auditEntity =  new AuditEntity();
		auditEntity.setContext(AML_UPLOAD);
		List<AuditResponse> listAudit = new ArrayList<AuditResponse>();
		
		AMLLastUpdatedResponse response = new AMLLastUpdatedResponse();
		response.setUpdatedBy("N/A");
		response.setUpdatedOn("N/A");
		
		try{
			listAudit = genericAuditDao.getLastSuccess(auditEntity);
		}catch(Exception e){
			log.info("Exception While Querying database in AuditViewService " + ExceptionUtils.getFullStackTrace(e));
			/*throw new OpsPanelException("AV-1001","Error in Fetching data From Database" );*/
		}
		if(listAudit != null && listAudit.size() != 0){
			log.info("ListAudit for AML Upload Found! ...");
			AuditResponse auditResponse = listAudit.get(0);
			if(auditResponse != null){
				log.info("Top element of ListAudit for AML Upload Found! ...");
				response.setUpdatedBy(auditResponse.getEmail());
				response.setUpdatedOn(auditResponse.getTimeStamp());
				return response;
			} else {
				log.info("Top element of ListAudit for AML Upload is NULL! ...");
			}
		} else {
			log.info("ListAudit for AML Upload is NULL or empty! ...");
		}
		return response;
	}



}
