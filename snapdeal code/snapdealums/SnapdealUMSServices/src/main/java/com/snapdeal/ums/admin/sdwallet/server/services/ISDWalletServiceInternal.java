/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 14-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.server.services;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;

/**
 * This is the interface for the clients to interact with SD Wallet . It has
 * APIs to credit(including refunds), debit and get current SDWallet info. All
 * credits and debits will be attached to a specific <code>ActivityType</code>.
 * Both the clients as well as ums server will load ActivityTypes in a cache OR
 * it can be fetched from <code>getAllActivityData()</code> method. (since the
 * types can be added/deleted/modified dynamically). Each activityType has a
 * expiry attached to it which will be used for crediting.
 */
public interface ISDWalletServiceInternal {

	/*
	 * the method returns transactionCode
	 * 
	 * @Param transactionId can be supplied as null but for refund it has to be
	 * provided Till the time OMS structure is not ready to work with
	 * TransactionCode, please use <code>refundSDWalletAgainstOrderId</code>
	 * API.
	 */
	public Integer creditSDWallet(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDay, String requestedBy,
			String sourceUniqTxnId, RequestContextSRO requestContextSRO,CreditSDWalletResponse response);
			

	/*
	 * this method returns transactionCode
	 */
	public Integer debitSDWallet(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, String source,
			String requestedBy, String sourceUniqTxnId, RequestContextSRO requestContextSRO, DebitSDWalletResponse response);

	/*
	 * this method returns the rows of SDWalletTable in a list fashion.
	 */
	public List<SDWallet> getSDWalletByUserId(Integer userId, int limit);

	/*
	 * this method returns all the rows of SDWalletHistory Table in a list
	 * fashion for that particular userId.
	 */
	public List<SDWalletHistory> getCompleteSDWalletHistoryByUserId(
			Integer userId);

	/*
	 * this method returns limited no. of rows of SDWalletHistory Table in a
	 * list fashion for that particular userId.
	 */
	public List<SDWalletHistory> getCompleteSDWalletHistoryByUserId(
			Integer userId, int limit);

	/*
	 * this method returns the available SDWallet balance.
	 */
	public Integer getAvailableBalanceInSDWalletByUserId(Integer userId);

	/*
	 * this method returns the complete activityType data.
	 */
	public List<SDWalletActivityType> getAllActivityTypeData();

	/*
	 * this method is here to support OMS flow for the time being, so that they
	 * can issue refunds on the basis of order code for now. This will be
	 * removed from the interface in subsequent releases. This method will
	 * return transactionCode
	 */
	Integer refundSDWalletAgainstOrderId(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, String source,
			String requestedBy, String sourceUniqTxnId, RequestContextSRO requestContextSRO, RefundSDWalletAgainstOrderIdResponse response);

	public boolean addSDWalletActivityType(String name, String code,
			String sdCash, Integer expiryDays, boolean async, boolean enabled);

	public boolean deleteSDWalletActivityType(Integer id, String code);

	public boolean modifySDWalletActivityType(
			SDWalletActivityType sdWalletActivityType);

	public boolean isActivityTypeCodeExists(String code);

	public List<SDWalletHistory> getSDWalletHistoryForMobile(Integer userId,
			String mode, Integer start, Integer pageSize, int limit);

	public SDWalletActivityType getActivityTypeById(Integer id);

	public SDWalletActivityType getActivityTypeByCode(String activityCode);

	public List<SDWallet> getExpiredSDWallet(Integer userId);

	public List<SDWallet> getSDWalletOfAvailableCredit(Integer userId);

	public List<SDWalletHistory> getSDWalletHistoryOfDebit(Integer userId);

	public Integer getNumberOfRecordsInSDWalletHistory(Integer userId,
			String mode);

	public List<SDWalletHistory> getDebitSDWalletHistoryByOrderId(String orderId);

	public int getUserSDCashUsedThisMonth(DateRange range, Integer userId);

	public int getUserSDCashEarningOfMonth(DateRange range, Integer userId);

	public List<Integer> getAllUsersFromSDCashHistory(Date startDate,
			int firstResult, int maxResults);

	public int getUserSDWalletExpiredThisMonth(DateRange range, Integer userId);

	int getUserSDCashOnDate(Date givenDate, int userId);
	//
	// public void creditSDWalletAndSendEmail(Integer userId, Integer amount,
	// Integer activityTypeId, String orderCode, Integer transactionId,
	// String source, Integer expiryDays, String requestedBy,
	// boolean sendEmail);

}