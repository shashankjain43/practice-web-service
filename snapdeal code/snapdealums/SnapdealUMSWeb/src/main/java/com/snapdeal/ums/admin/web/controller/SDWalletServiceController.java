package com.snapdeal.ums.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.AddSDWalletActivityTypeResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletSendEmailRequest;
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
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
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

@Controller
@RequestMapping("/service/ums/admin/sdwallet/")
public class SDWalletServiceController {

    @Autowired
    private ISDWalletService sDWalletService; 

    

    /**
     * Controller that calls the service to credit SDCash and send out email if the trigger mail is set
     * @param CreditSDWalletSendEmailRequest 
     * @return CreditSDWalletResponse
     */
    @RequestMapping(value = "creditSDWallet", produces = "application/sd-service")
    @ResponseBody
    public CreditSDWalletResponse creditSDWallet(@RequestBody CreditSDWalletSendEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
    CreditSDWalletResponse response = sDWalletService.creditSDWalletAndSendEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    

    @RequestMapping(value = "creditSDWalletAndSendEmail", produces = "application/sd-service")
    @ResponseBody
    public CreditSDWalletResponse creditSDWalletAndSendEmail(@RequestBody CreditSDWalletSendEmailRequest request,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CreditSDWalletResponse response = sDWalletService.creditSDWalletAndSendEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "debitSDWallet", produces = "application/sd-service")
    @ResponseBody
    public DebitSDWalletResponse debitSDWallet(@RequestBody DebitSDWalletRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        DebitSDWalletResponse response = sDWalletService.debitSDWallet(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSDWalletByUserId", produces = "application/sd-service")
    @ResponseBody
    public GetSDWalletByUserIdResponse getSDWalletByUserId(@RequestBody GetSDWalletByUserIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetSDWalletByUserIdResponse response = sDWalletService.getSDWalletByUserId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getExpiredSDWallet", produces = "application/sd-service")
    @ResponseBody
    public SDWalletResponse getExpiredSDWallet(@RequestBody SDWalletRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SDWalletResponse response = sDWalletService.getExpiredSDWallet(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSDWalletOfAvailableCredit", produces = "application/sd-service")
    @ResponseBody
    public SDWalletResponse getSDWalletOfAvailableCredit(@RequestBody SDWalletRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SDWalletResponse response = sDWalletService.getSDWalletOfAvailableCredit(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSDWalletHistoryOfDebit", produces = "application/sd-service")
    @ResponseBody
    public SDWalletHistoryResponse getSDWalletHistoryOfDebit(@RequestBody SDWalletHistoryRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SDWalletHistoryResponse response = sDWalletService.getSDWalletHistoryOfDebit(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getDebitSDWalletHistoryByOrderId", produces = "application/sd-service")
    @ResponseBody
    public SDWalletHistoryResponse getDebitSDWalletHistoryByOrderId(@RequestBody SDWalletHistoryRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SDWalletHistoryResponse response = sDWalletService.getDebitSDWalletHistoryByOrderId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCompleteSDWalletHistoryByUserId", produces = "application/sd-service")
    @ResponseBody
    public GetCompleteSDWalletHistoryByUserIdResponse getCompleteSDWalletHistoryByUserId(@RequestBody GetCompleteSDWalletHistoryByUserIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetCompleteSDWalletHistoryByUserIdResponse response = sDWalletService.getCompleteSDWalletHistoryByUserId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAvailableBalanceInSDWalletByUserId", produces = "application/sd-service")
    @ResponseBody
    public GetAvailableBalanceInSDWalletByUserIdResponse getAvailableBalanceInSDWalletByUserId(@RequestBody GetAvailableBalanceInSDWalletByUserIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        GetAvailableBalanceInSDWalletByUserIdResponse response = sDWalletService.getAvailableBalanceInSDWalletByUserId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "refundSDWalletAgainstOrderId", produces = "application/sd-service")
    @ResponseBody
    public RefundSDWalletAgainstOrderIdResponse refundSDWalletAgainstOrderId(@RequestBody RefundSDWalletAgainstOrderIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        RefundSDWalletAgainstOrderIdResponse response = sDWalletService.refundSDWalletAgainstOrderId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAllActivityTypeData", produces = "application/sd-service")
    @ResponseBody
    public GetAllActivityTypeDataResponse getAllActivityTypeData(@RequestBody GetAllActivityTypeDataRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllActivityTypeDataResponse response = sDWalletService.getAllActivityTypeData(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addSDWalletActivityType", produces = "application/sd-service")
    @ResponseBody
    public AddSDWalletActivityTypeResponse addSDWalletActivityType(@RequestBody AddSDWalletActivityTypeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        AddSDWalletActivityTypeResponse response = sDWalletService.addSDWalletActivityType(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "deleteSDWalletActivityType", produces = "application/sd-service")
    @ResponseBody
    public DeleteSDWalletActivityTypeResponse deleteSDWalletActivityType(@RequestBody DeleteSDWalletActivityTypeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        DeleteSDWalletActivityTypeResponse response = sDWalletService.deleteSDWalletActivityType(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "modifySDWalletActivityType", produces = "application/sd-service")
    @ResponseBody
    public ModifySDWalletActivityTypeResponse modifySDWalletActivityType(@RequestBody ModifySDWalletActivityTypeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        ModifySDWalletActivityTypeResponse response = sDWalletService.modifySDWalletActivityType(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "isActivityTypeCodeExists", produces = "application/sd-service")
    @ResponseBody
    public IsActivityTypeCodeExistsResponse isActivityTypeCodeExists(@RequestBody IsActivityTypeCodeExistsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        IsActivityTypeCodeExistsResponse response = sDWalletService.isActivityTypeCodeExists(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSDWalletHistoryForMobile", produces = "application/sd-service")
    @ResponseBody
    public GetSDWalletHistoryForMobileResponse getSDWalletHistoryForMobile(@RequestBody GetSDWalletHistoryForMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetSDWalletHistoryForMobileResponse response = sDWalletService.getSDWalletHistoryForMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNumberOfRecordsInSDWalletHistory", produces = "application/sd-service")
    @ResponseBody
    public NumberOfRecordsResponse getNumberOfRecordsInSDWalletHistory(@RequestBody NumberOfRecordsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        NumberOfRecordsResponse response = sDWalletService.getNumberOfRecordsInSDWalletHistory(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActivityTypeById", produces = "application/sd-service")
    @ResponseBody
    public GetAllActivityTypeDataResponse getActivityTypeById(@RequestBody GetAllActivityTypeDataRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllActivityTypeDataResponse response = sDWalletService.getActivityTypeById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActivityTypeByCode", produces = "application/sd-service")
    @ResponseBody
    public GetAllActivityTypeDataResponse getActivityTypeByCode(@RequestBody GetAllActivityTypeDataRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllActivityTypeDataResponse response = sDWalletService.getActivityTypeByCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    

    @RequestMapping(value = "getUserSDCashUsedThisMonth", produces = "application/sd-service")
    @ResponseBody
    public GetUserSDCashUsedThisMonthResponse getUserSDCashUsedThisMonth(@RequestBody GetUserSDCashUsedThisMonthRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserSDCashUsedThisMonthResponse response = sDWalletService.getUserSDWalletUsedThisMonth(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserSDCashExpiredThisMonth", produces = "application/sd-service")
    @ResponseBody
    public GetUserSDCashExpiredThisMonthResponse getUserSDCashExpiredThisMonth(@RequestBody GetUserSDCashExpiredThisMonthRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserSDCashExpiredThisMonthResponse response = sDWalletService.getUserSDWalletExpiredThisMonth(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    
    @RequestMapping(value = "getUserSDCashEarningOfMonth", produces = "application/sd-service")
    @ResponseBody
    public GetUserSDCashEarningOfMonthResponse getUserSDCashEarningOfMonth(@RequestBody GetUserSDCashEarningOfMonthRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserSDCashEarningOfMonthResponse response = sDWalletService.getUserSDWalletEarningOfMonth(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getUserSDCashAtBegOfMonth", produces = "application/sd-service")
    @ResponseBody
    public GetUserSDCashAtBegOfMonthResponse getUserSDCashAtBegOfMonth(@RequestBody GetUserSDCashAtBegOfMonthRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserSDCashAtBegOfMonthResponse response = sDWalletService.getUserSDWalletAtBegOfMonth(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getUserSDCashAtEndOfMonth", produces = "application/sd-service")
    @ResponseBody
    public GetUserSDCashAtEndOfMonthResponse getUserSDCashAtEndOfMonth(@RequestBody GetUserSDCashAtEndOfMonthRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetUserSDCashAtEndOfMonthResponse response = sDWalletService.getUserSDWalletAtEndOfMonth(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getAllUsersFromSDCashHistory", produces = "application/sd-service")
    @ResponseBody
    public GetAllUsersFromSDWalletHistoryResponse getAllUsersFromSDCashHistory(@RequestBody GetAllUsersFromSDWalletHistoryRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllUsersFromSDWalletHistoryResponse response = sDWalletService.getAllUsersFromSDCashHistory(request);

        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    
}
