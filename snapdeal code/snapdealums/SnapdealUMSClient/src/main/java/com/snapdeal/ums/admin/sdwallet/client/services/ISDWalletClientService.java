package com.snapdeal.ums.admin.sdwallet.client.services;

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

/**
 * This is the interface for the clients to interact with SD Wallet . It has APIs to credit(including refunds), debit
 * and get current SDWallet info. All credits and debits will be attached to a specific <code>ActivityType</code>. Both
 * the clients as well as ums server will load ActivityTypes in a cache OR it can be fetched from
 * <code>getAllActivityData()</code> method. (since the types can be added/deleted/modified dynamically). Each
 * activityType has a expiry attached to it which will be used for crediting.
 */
public interface ISDWalletClientService {

    /*
     * the method returns transactionCode
     * 
     * @Param transactionId can be supplied as null but for refund it has to be
     * provided Till the time OMS structure is not ready to work with
     * TransactionCode, please use <code>refundSDWalletAgainstOrderId</code>
     * API.
     */
    public CreditSDWalletResponse creditSDWallet(CreditSDWalletRequest request);

    /*
     * this method returns transactionCode
     */
    public DebitSDWalletResponse debitSDWallet(DebitSDWalletRequest request);

    /*
     * this method returns the SDWalletInfoSRO which contains rows of
     * SDWalletTable.
     */
    public GetSDWalletByUserIdResponse getSDWalletByUserId(GetSDWalletByUserIdRequest request);

    /*
     * this method returns all the rows of SDWalletHistory Table in a list
     * fashion for that particular userId.
     */
    public GetCompleteSDWalletHistoryByUserIdResponse getCompleteSDWalletHistoryByUserId(GetCompleteSDWalletHistoryByUserIdRequest request);

    /*
     * this method returns the available SDWallet balance.
     */
    public GetAvailableBalanceInSDWalletByUserIdResponse getAvailableBalanceInSDWalletByUserId(GetAvailableBalanceInSDWalletByUserIdRequest request);

    /*
     * this method is here to support OMS flow for the time being, so that they
     * can issue refunds on the basis of order code for now. This will be
     * removed from the interface in subsequent releases. This method will
     * return transactionCode
     */
    public RefundSDWalletAgainstOrderIdResponse refundSDWalletAgainstOrderId(RefundSDWalletAgainstOrderIdRequest request);

    /*
     * this method returns the complete activityType data.
     */
    public GetAllActivityTypeDataResponse getAllActivityTypeData(GetAllActivityTypeDataRequest request);

    public AddSDWalletActivityTypeResponse addSDWalletActivityType(AddSDWalletActivityTypeRequest request);

    public DeleteSDWalletActivityTypeResponse deleteSDWalletActivityType(DeleteSDWalletActivityTypeRequest request);

    public ModifySDWalletActivityTypeResponse modifySDWalletActivityType(ModifySDWalletActivityTypeRequest request);

    public IsActivityTypeCodeExistsResponse isActivityTypeCodeExists(IsActivityTypeCodeExistsRequest request);

    public GetSDWalletHistoryForMobileResponse getSDWalletHistoryForMobile(GetSDWalletHistoryForMobileRequest request);

    public GetAllActivityTypeDataResponse getActivityTypeById(GetAllActivityTypeDataRequest request);

    public GetAllActivityTypeDataResponse getActivityTypeByCode(GetAllActivityTypeDataRequest request);

    public SDWalletResponse getExpiredSDWallet(SDWalletRequest request);

    public SDWalletResponse getSDWalletOfAvailableCredit(SDWalletRequest request);

    public SDWalletHistoryResponse getSDWalletHistoryOfDebit(SDWalletHistoryRequest request);

    public NumberOfRecordsResponse getNumberOfRecordsInSDWalletHistory(NumberOfRecordsRequest request);

    public SDWalletHistoryResponse getDebitSDWalletHistoryByOrderId(SDWalletHistoryRequest request);

    public GetUserSDCashAtBegOfMonthResponse getUserSDWalletAtBegOfMonth(GetUserSDCashAtBegOfMonthRequest request);

    public GetUserSDCashEarningOfMonthResponse getUserSDWalletEarningOfMonth(GetUserSDCashEarningOfMonthRequest request);
    
    public GetUserSDCashUsedThisMonthResponse getUserSDWalletUsedThisMonth(GetUserSDCashUsedThisMonthRequest request);

    public GetUserSDCashAtEndOfMonthResponse getUserSDWalletAtEndOfMonth(GetUserSDCashAtEndOfMonthRequest request);

    public GetAllUsersFromSDWalletHistoryResponse getAllUsersFromSDCashHistory(GetAllUsersFromSDWalletHistoryRequest request);

    public GetUserSDCashExpiredThisMonthResponse getUserSDWalletExpiredThisMonth(GetUserSDCashExpiredThisMonthRequest getUserSDWalletExpiredThisMonthRequest);

}
