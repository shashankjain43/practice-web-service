package com.snapdeal.opspanel.clientkeymanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.clientkeymanagement.dao.TargetApiMapperDao;
import com.snapdeal.opspanel.clientkeymanagement.entity.TargetApiMapperEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.service.TargetApiMapperService;

@Slf4j
@Service("targetApiMapperService")
public class TargetApiMapperServiceImpl implements TargetApiMapperService{
	
	@Autowired
	TargetApiMapperDao targetApiMapperDao;

	@Override
	public void insertIntoTargetApiMapper(TargetApiMapperEntity entity) throws ClientViewerException{
		
		try {
			targetApiMapperDao.insertIntoTargetApiMapper(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3011", "Error while inserting api in database!");
		}	
	}

	@Override
	public List<TargetApiMapperEntity> getAllTargetApiMapper() throws ClientViewerException{
		
		List<TargetApiMapperEntity> allTargetApiMapper  = new ArrayList<TargetApiMapperEntity>();
		try {
			allTargetApiMapper = targetApiMapperDao.getAllTargetApiMapper();
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3012", "Error while fetching apis in database!");
		}	
		
		return allTargetApiMapper;
	}

	@Override
	public List<String> getApisForTargetApplication(String targetApplication) throws ClientViewerException {
		List<String> apisForTargetApplication = new ArrayList<String>();
		try {
			apisForTargetApplication = targetApiMapperDao.getApisForTargetApplication(targetApplication);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3013", "Error while getting apis for target applications in database!");
		}
		return apisForTargetApplication;
	}

	@Override
	public void updateApiName(TargetApiMapperEntity entity) throws ClientViewerException {		
		try {
			targetApiMapperDao.updateApiName(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3014", "Error while updating api name in database!");
		}
		
	}

	@Override
	public void disableApi(TargetApiMapperEntity entity) throws ClientViewerException {
		try {
			targetApiMapperDao.disableApi(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3015", "Error while disabling api in database!");
		}
	}

	@Override
	public void enableApi(TargetApiMapperEntity entity) throws ClientViewerException {
		try {
			targetApiMapperDao.enableApi(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3016", "Error while enabling api in database!");
		}	
	}

	@Override
	public void upsertApiForTargetApplication(TargetApiMapperEntity request) throws ClientViewerException {
		try {
			request.setApiName( request.getApiId() );
			request.setActiveStatus( true );
			if( request.getOldApiId() == null ) {
				targetApiMapperDao.insertIntoTargetApiMapper( request );
			} else {
				int numOfRows = targetApiMapperDao.updateApiName( request );
				if ( numOfRows == 0 ) {
					throw new ClientViewerException( "MT-3019", "Api does not exist in database." );
				}
			}
		} catch( ClientViewerException e ) {
			log.info( "ClientViewerException while upserting api name for target application " + ExceptionUtils.getFullStackTrace( e ) );
			throw e;
		} catch( Exception e ) {
			log.info(  "Exception while upserting api name for target application " + ExceptionUtils.getFullStackTrace( e ) );
			throw new ClientViewerException( "MT-3017", "Could not update or insert api in database!" );
		}
	}

	@Override
	public void deleteApiForTargetApplication(TargetApiMapperEntity request) throws ClientViewerException {
		try {
			targetApiMapperDao.deleteApi( request );
		} catch( Exception e ) {
			log.info( "Excepton while deleting api name for target application "  + ExceptionUtils.getFullStackTrace( e ) );
			throw new ClientViewerException( "MT-3018", "Could not dlete Api from database" );
		}
	}

}
