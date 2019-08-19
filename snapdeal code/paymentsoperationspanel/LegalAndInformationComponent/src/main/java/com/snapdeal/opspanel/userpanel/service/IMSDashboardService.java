package com.snapdeal.opspanel.userpanel.service;
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
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.ims.request.GetDiscrepencyCountRequest;
 
 public interface IMSDashboardService {
 
    public GetDiscrepencyCountResponse getDiscrepencyCountForUsers(GetDiscrepencyCountRequest request) throws InfoPanelException;
    
    public GtokenSizeResponse getTokensForUser(GtokenSizeRequest emailId) throws InfoPanelException;
    
    public WalletCountResponse getWalletCount(WalletFilterRequest request) throws InfoPanelException;
    
    public BlacklistEmailResponse getBlacklistEmails(BlackListRequest request) throws InfoPanelException;
    
    public GetUserOtpDetailsResponse getUserOtpDetails(GetUserOtpDetailsRequest request) throws InfoPanelException;
    
    public UserStatusResponse getStatus(UserStatusRequest request) throws InfoPanelException;
    
    public GetUserHistoryDetailsResponse getUserHistoryDetails(GetUserHistoryDetailsRequest request) throws InfoPanelException;
    
    public DiscrepencyCasesEmailResponse getAllEmailsDiscrepenceycases(GetEmailForDiscrepencyCasesRequest request) throws InfoPanelException;
    
  
 
 }
