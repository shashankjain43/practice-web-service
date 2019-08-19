package com.snapdeal.ums.admin.sdwallet.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.sdwallet.client.services.ISDWalletClientService;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DeleteSDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DeleteSDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAllActivityTypeDataRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAllActivityTypeDataResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetCompleteSDWalletHistoryByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletHistoryForMobileRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetSDWalletHistoryForMobileResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.IsActivityTypeCodeExistsRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.IsActivityTypeCodeExistsResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.ModifySDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.ModifySDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.NumberOfRecordsRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.NumberOfRecordsResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletHistoryRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletHistoryResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.SDWalletResponse;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryRequest;
import com.snapdeal.ums.ext.user.GetAllUsersFromSDWalletHistoryResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashAtBegOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtBegOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashAtEndOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashAtEndOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashEarningOfMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashEarningOfMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashExpiredThisMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashExpiredThisMonthResponse;
import com.snapdeal.ums.ext.user.GetUserSDCashUsedThisMonthRequest;
import com.snapdeal.ums.ext.user.GetUserSDCashUsedThisMonthResponse;

@Service("SDWalletClientService")
public class SDWalletClientServiceImpl implements ISDWalletClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/sdwallet";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(SDWalletClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/sdwallet/", "sdwalletserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    @Override
    public CreditSDWalletResponse creditSDWallet(CreditSDWalletRequest request) {
        CreditSDWalletResponse response = new CreditSDWalletResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/creditSDWallet";
            response = (CreditSDWalletResponse) transportService.executeRequest(url, request, null, CreditSDWalletResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public DebitSDWalletResponse debitSDWallet(DebitSDWalletRequest request) {
        DebitSDWalletResponse response = new DebitSDWalletResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/debitSDWallet";
            response = (DebitSDWalletResponse) transportService.executeRequest(url, request, null, DebitSDWalletResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

  
    @Override
    public SDWalletResponse getExpiredSDWallet(SDWalletRequest request) {
        SDWalletResponse response = new SDWalletResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getExpiredSDWallet";
            response = (SDWalletResponse) transportService.executeRequest(url, request, null, SDWalletResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SDWalletResponse getSDWalletOfAvailableCredit(SDWalletRequest request) {
        SDWalletResponse response = new SDWalletResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSDWalletOfAvailableCredit";
            response = (SDWalletResponse) transportService.executeRequest(url, request, null, SDWalletResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetSDWalletByUserIdResponse getSDWalletByUserId(GetSDWalletByUserIdRequest request) {
        GetSDWalletByUserIdResponse response = new GetSDWalletByUserIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSDWalletByUserId";
            response = (GetSDWalletByUserIdResponse) transportService.executeRequest(url, request, null, GetSDWalletByUserIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

 
    @Override
    public SDWalletHistoryResponse getSDWalletHistoryOfDebit(SDWalletHistoryRequest request) {
        SDWalletHistoryResponse response = new SDWalletHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSDWalletHistoryOfDebit";
            response = (SDWalletHistoryResponse) transportService.executeRequest(url, request, null, SDWalletHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SDWalletHistoryResponse getDebitSDWalletHistoryByOrderId(SDWalletHistoryRequest request) {
        SDWalletHistoryResponse response = new SDWalletHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getDebitSDWalletHistoryByOrderId";
            response = (SDWalletHistoryResponse) transportService.executeRequest(url, request, null, SDWalletHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetCompleteSDWalletHistoryByUserIdResponse getCompleteSDWalletHistoryByUserId(GetCompleteSDWalletHistoryByUserIdRequest request) {
        GetCompleteSDWalletHistoryByUserIdResponse response = new GetCompleteSDWalletHistoryByUserIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getCompleteSDWalletHistoryByUserId";
            response = (GetCompleteSDWalletHistoryByUserIdResponse) transportService.executeRequest(url, request, null, GetCompleteSDWalletHistoryByUserIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetAvailableBalanceInSDWalletByUserIdResponse getAvailableBalanceInSDWalletByUserId(GetAvailableBalanceInSDWalletByUserIdRequest request) {
        GetAvailableBalanceInSDWalletByUserIdResponse response = new GetAvailableBalanceInSDWalletByUserIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAvailableBalanceInSDWalletByUserId";
            response = (GetAvailableBalanceInSDWalletByUserIdResponse) transportService.executeRequest(url, request, null, GetAvailableBalanceInSDWalletByUserIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public RefundSDWalletAgainstOrderIdResponse refundSDWalletAgainstOrderId(RefundSDWalletAgainstOrderIdRequest request) {
        RefundSDWalletAgainstOrderIdResponse response = new RefundSDWalletAgainstOrderIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/refundSDWalletAgainstOrderId";
            response = (RefundSDWalletAgainstOrderIdResponse) transportService.executeRequest(url, request, null, RefundSDWalletAgainstOrderIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetAllActivityTypeDataResponse getAllActivityTypeData(GetAllActivityTypeDataRequest request) {
        GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllActivityTypeData";
            response = (GetAllActivityTypeDataResponse) transportService.executeRequest(url, request, null, GetAllActivityTypeDataResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public AddSDWalletActivityTypeResponse addSDWalletActivityType(AddSDWalletActivityTypeRequest request) {
        AddSDWalletActivityTypeResponse response = new AddSDWalletActivityTypeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addSDWalletActivityType";
            response = (AddSDWalletActivityTypeResponse) transportService.executeRequest(url, request, null, AddSDWalletActivityTypeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public DeleteSDWalletActivityTypeResponse deleteSDWalletActivityType(DeleteSDWalletActivityTypeRequest request) {
        DeleteSDWalletActivityTypeResponse response = new DeleteSDWalletActivityTypeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/deleteSDWalletActivityType";
            response = (DeleteSDWalletActivityTypeResponse) transportService.executeRequest(url, request, null, DeleteSDWalletActivityTypeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public ModifySDWalletActivityTypeResponse modifySDWalletActivityType(ModifySDWalletActivityTypeRequest request) {
        ModifySDWalletActivityTypeResponse response = new ModifySDWalletActivityTypeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/modifySDWalletActivityType";
            response = (ModifySDWalletActivityTypeResponse) transportService.executeRequest(url, request, null, ModifySDWalletActivityTypeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public IsActivityTypeCodeExistsResponse isActivityTypeCodeExists(IsActivityTypeCodeExistsRequest request) {
        IsActivityTypeCodeExistsResponse response = new IsActivityTypeCodeExistsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isActivityTypeCodeExists";
            response = (IsActivityTypeCodeExistsResponse) transportService.executeRequest(url, request, null, IsActivityTypeCodeExistsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetSDWalletHistoryForMobileResponse getSDWalletHistoryForMobile(GetSDWalletHistoryForMobileRequest request) {
        GetSDWalletHistoryForMobileResponse response = new GetSDWalletHistoryForMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSDWalletHistoryForMobile";
            response = (GetSDWalletHistoryForMobileResponse) transportService.executeRequest(url, request, null, GetSDWalletHistoryForMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public NumberOfRecordsResponse getNumberOfRecordsInSDWalletHistory(NumberOfRecordsRequest request) {
        NumberOfRecordsResponse response = new NumberOfRecordsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNumberOfRecordsInSDWalletHistory";
            response = (NumberOfRecordsResponse) transportService.executeRequest(url, request, null, NumberOfRecordsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetAllActivityTypeDataResponse getActivityTypeById(GetAllActivityTypeDataRequest request) {
        GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActivityTypeById";
            response = (GetAllActivityTypeDataResponse) transportService.executeRequest(url, request, null, GetAllActivityTypeDataResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetAllActivityTypeDataResponse getActivityTypeByCode(GetAllActivityTypeDataRequest request) {
        GetAllActivityTypeDataResponse response = new GetAllActivityTypeDataResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActivityTypeByCode";
            response = (GetAllActivityTypeDataResponse) transportService.executeRequest(url, request, null, GetAllActivityTypeDataResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetUserSDCashAtBegOfMonthResponse getUserSDWalletAtBegOfMonth(GetUserSDCashAtBegOfMonthRequest request)

    {
        GetUserSDCashAtBegOfMonthResponse response = new GetUserSDCashAtBegOfMonthResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserSDCashAtBegOfMonth";
            response = (GetUserSDCashAtBegOfMonthResponse) transportService.executeRequest(url, request, null, GetUserSDCashAtBegOfMonthResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetUserSDCashEarningOfMonthResponse getUserSDWalletEarningOfMonth(GetUserSDCashEarningOfMonthRequest request)

    {
        GetUserSDCashEarningOfMonthResponse response = new GetUserSDCashEarningOfMonthResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserSDCashEarningOfMonth";
            response = (GetUserSDCashEarningOfMonthResponse) transportService.executeRequest(url, request, null, GetUserSDCashEarningOfMonthResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetUserSDCashUsedThisMonthResponse getUserSDWalletUsedThisMonth(GetUserSDCashUsedThisMonthRequest request)

    {
        GetUserSDCashUsedThisMonthResponse response = new GetUserSDCashUsedThisMonthResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserSDCashUsedThisMonth";
            response = (GetUserSDCashUsedThisMonthResponse) transportService.executeRequest(url, request, null, GetUserSDCashUsedThisMonthResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetUserSDCashAtEndOfMonthResponse getUserSDWalletAtEndOfMonth(GetUserSDCashAtEndOfMonthRequest request)

    {
        GetUserSDCashAtEndOfMonthResponse response = new GetUserSDCashAtEndOfMonthResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserSDCashAtEndOfMonth";
            response = (GetUserSDCashAtEndOfMonthResponse) transportService.executeRequest(url, request, null, GetUserSDCashAtEndOfMonthResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetAllUsersFromSDWalletHistoryResponse getAllUsersFromSDCashHistory(GetAllUsersFromSDWalletHistoryRequest request)

    {
        GetAllUsersFromSDWalletHistoryResponse response = new GetAllUsersFromSDWalletHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllUsersFromSDCashHistory";
            response = (GetAllUsersFromSDWalletHistoryResponse) transportService.executeRequest(url, request, null, GetAllUsersFromSDWalletHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetUserSDCashExpiredThisMonthResponse getUserSDWalletExpiredThisMonth(GetUserSDCashExpiredThisMonthRequest getUserSDWalletExpiredThisMonthRequest) {

        GetUserSDCashExpiredThisMonthResponse response = new GetUserSDCashExpiredThisMonthResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserSDCashExpiredThisMonth";
            response = (GetUserSDCashExpiredThisMonthResponse) transportService.executeRequest(url, getUserSDWalletExpiredThisMonthRequest, null,
                    GetUserSDCashExpiredThisMonthResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
