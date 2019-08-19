package com.snapdeal.opspanel.audit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.audit.dao.GenericAuditDao;
import com.snapdeal.opspanel.audit.entity.AuditEntity;
import com.snapdeal.opspanel.audit.request.AuditRequest;
import com.snapdeal.opspanel.audit.response.AuditResponse;
import com.snapdeal.opspanel.audit.service.AuditingService;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditingServiceImpl implements AuditingService {
	
	
	@Autowired
	GenericAuditDao genericAuditDao;

	@Override
	public int getCount(AuditRequest auditRequest) throws OpsPanelException {
		// TODO Auto-generated method stub
		validateDates(auditRequest.getStartDate(), auditRequest.getEndDate(), 14);
		
		String email = auditRequest.getEmail();
		String searchId = auditRequest.getSearchId();
		AuditEntity entity = new AuditEntity();
		if(email!= null && email.isEmpty()){
			email=null;
		}
		if(searchId!=null && searchId.isEmpty()){
			searchId=null;
		}
		
			entity.setEmailId(email);
			entity.setContext(auditRequest.getContext());
			entity.setSearchId(searchId);
			entity.setStartDate(auditRequest.getStartDate());
			entity.setEndDate(auditRequest.getEndDate());
			//entity.setPageSize(auditRequest.getPageSize());
			//entity.setOffset(auditRequest.getOffset()*auditRequest.getPageSize());
			try{
				return genericAuditDao.getCount(entity);
			}catch(Exception e){
				log.info("Exception While Querying database in AuditViewService " + ExceptionUtils.getFullStackTrace(e));
				throw new OpsPanelException("AV-1005","Error in Fetching total count From Database" );
			}
		
	}

	@Override
	public List<AuditResponse> getResponse(AuditRequest auditRequest) throws OpsPanelException {
		// TODO Auto-generated method stub
	
		
		validateDates(auditRequest.getStartDate(), auditRequest.getEndDate(), 14);
		
		String email = auditRequest.getEmail();
		String searchId = auditRequest.getSearchId();
		AuditEntity entity = new AuditEntity();
		if(email!= null && email.isEmpty()){
			email=null;
		}
		if(searchId!=null && searchId.isEmpty()){
			searchId=null;
		}
		
			entity.setEmailId(email);
			entity.setContext(auditRequest.getContext());
			entity.setSearchId(searchId);
			entity.setStartDate(auditRequest.getStartDate());
			entity.setEndDate(auditRequest.getEndDate());
			entity.setPageSize(auditRequest.getPageSize());
			entity.setOffset(auditRequest.getOffset()*auditRequest.getPageSize());
			try{
				return genericAuditDao.getResponse(entity);
			}catch(Exception e){
				log.info("Exception While Querying database in AuditViewService " + ExceptionUtils.getFullStackTrace(e));
				throw new OpsPanelException("AV-1001","Error in Fetching data From Database" );
			}
	}
	
	public void validateDates(String startDate , String endDate , int difference) throws OpsPanelException{
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date sdate= formatter.parse(startDate);
			Date edate = formatter.parse(endDate);
			if(edate.getTime()<sdate.getTime()){
				throw new OpsPanelException("AV-1002", "End Date must be greater than Start Date");
			}
			int days =  (int) ((edate.getTime()- sdate.getTime())/ (1000 * 60 * 60 * 24)) ;
			if(days>difference){
				throw new OpsPanelException("AV-1003", "Difference betwen start and end Date can be max 14 days.");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			throw new OpsPanelException("AV-1004", "Dates must be in the format yyyy-MM-dd HH:mm:ss. Please Check.");
		}
	}

}
