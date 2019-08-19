 package com.snapdeal.opspanel.userpanel.service.impl;
 
 import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.service.IMSDashboardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 import com.snapdeal.ims.client.IDashBoardServiceClient;
import com.snapdeal.ims.client.impl.DashboardClientServiceImpl;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.request.BlackListRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesRequest;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.request.GtokenSizeRequest;
import com.snapdeal.ims.request.UserSearchRequest;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.request.WalletFilterRequest;
import com.snapdeal.ims.response.BlacklistEmailResponse;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.response.WalletCountResponse;

 import lombok.extern.slf4j.Slf4j;

import com.snapdeal.ims.request.GetDiscrepencyCountRequest;
 
 @Slf4j
 @Service("OpsPanelIMSService")
 public class IMSDashboardServiceImpl implements IMSDashboardService {
 
    @Autowired
    IDashBoardServiceClient dashboardClient;
 
    @Override
    public BlacklistEmailResponse getBlacklistEmails(BlackListRequest request)
             throws InfoPanelException {
 
       try {
          return dashboardClient.getBlacklistEmails((BlackListRequest) request);
       } catch( HttpTransportException httpe ) {
          log.info( "HttpTransportException while getting blacklist emails " + httpe );
          throw new InfoPanelException( "MT-5604", httpe.getErrMsg() );
       } catch( ServiceException se ) {
          log.info( "ServiceException while getting backlist emails " + se );
          throw new InfoPanelException( "MT-5606", se.getErrMsg() );
       }
    }
 
    @Override
    public GetDiscrepencyCountResponse getDiscrepencyCountForUsers(
             GetDiscrepencyCountRequest request) throws InfoPanelException {

       try {
          return dashboardClient.getSDFCIdDiscrepencyCountForUsers(request);
       } catch( HttpTransportException httpe ) {
          log.info( "HttpTransportExceptoin while getting discrepancy count for users " + httpe );
          throw new InfoPanelException( "MT-5605", httpe.getErrMsg());
       } catch( ServiceException se ) {
          log.info( "ServiceException while getting discrepancy count for users " + se );
          throw new InfoPanelException( "MT-5607", se.getErrMsg() );
       }
    }
    
    
    @Override
    public DiscrepencyCasesEmailResponse getAllEmailsDiscrepenceycases(GetEmailForDiscrepencyCasesRequest request) throws InfoPanelException {
    	try {
            return dashboardClient.getAllEmailForSDFCIdDiscrepenceycases(request);
         } catch( HttpTransportException httpe ) {
            log.info( "HttpTransportExceptoin while getting all Emails Discrepencey cases " + httpe );
            throw new InfoPanelException( "MT-5605", httpe.getErrMsg());
         } catch( ServiceException se ) {
            log.info( "ServiceException while getting all Emails Discrepencey cases " + se );
            throw new InfoPanelException( "MT-5607", se.getErrMsg() );
         }
    }
 
    @Override
    public GtokenSizeResponse getTokensForUser(GtokenSizeRequest emailId) throws InfoPanelException {
 
       try {
          return dashboardClient.gTokenSizeWithEmailId( emailId );
       } catch (HttpTransportException e) {
          log.info( "HttpTransportException while getting token size: " + e );
          throw new InfoPanelException( "MT-5602", e.getErrMsg() );
       } catch (ServiceException e) {
          log.info( "ServiceException while getting token size: " + e );
          throw new InfoPanelException( "MT-5603", e.getErrMsg() );
       }
 
    }
 
    @Override
    public GetUserOtpDetailsResponse getUserOtpDetails(GetUserOtpDetailsRequest request)
             throws InfoPanelException {
 
       try {
          return dashboardClient.getUserOtpDetails(request);
       } catch( HttpTransportException e ) {
          log.info( "HttpTransportException while getting user otp details: " + e );
          throw new InfoPanelException( "MT-5608", e.getErrMsg() );
       } catch( ServiceException e ) {
          log.info( "ServiceException while getting user otp details: " + e );
          throw new InfoPanelException( "MT-5609", e.getErrMsg() );
       }
    }
 
    @Override
    public WalletCountResponse getWalletCount(WalletFilterRequest request)
             throws InfoPanelException {
 
       try {
          return dashboardClient.getWalletCountBasedOnFilter(request);
       } catch (HttpTransportException e) {
          log.info( "HttpTransportException while getting wallet count based on filter: " + e );
          throw new InfoPanelException( "MT-5600", e.getErrMsg() );
       } catch (ServiceException e) {
          log.info( "ServiceException while getting wallet count based on filter " + e );
          throw new InfoPanelException( "MT- 5601", e.getErrMsg() );
       }
 
    }
 
 
    @Override
    public UserStatusResponse getStatus(UserStatusRequest request) throws InfoPanelException {
 
       try {
          return dashboardClient.getStatus( request );
       } catch( HttpTransportException e ) {
          log.info( "HttpTransportException while getting status for user: " + e);
          throw new InfoPanelException( "MT-5612", e.getErrMsg() );
       } catch( ServiceException e ) {
          log.info( "ServiceException while getting status for user: " + e );
          throw new InfoPanelException( "MT-5613", e.getErrMsg());
       }
    }
    
    @Override
    public GetUserHistoryDetailsResponse getUserHistoryDetails(GetUserHistoryDetailsRequest request) throws InfoPanelException {
    	try {
            return dashboardClient.getUserHistoryDetails( request );
         } catch( HttpTransportException e ) {
            log.info( "HttpTransportException while getting user history details: " + e);
            throw new InfoPanelException( "MT-5622", e.getErrMsg() );
         } catch( ServiceException e ) {
            log.info( "ServiceException while getting user history details: " + e );
            throw new InfoPanelException( "MT-5623", e.getErrMsg());
         }
    }
 
 }
