/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 14-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.dao.user.sdwallet;

import java.util.Date;
import java.util.List;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.core.entity.SDWalletTransaction;
import com.snapdeal.ums.core.entity.SDWalletTxnHistory;

public interface ISDWalletDao {

    public List<SDWallet> getSDWalletByUserId(int userId, int limit);

    public List<SDWalletHistory> getSDWalletHistoryByUserId(int userId);
    
    public List<SDWalletHistory> getSDWalletHistoryByUserId(Integer userId, int limit);

    public List<SDWalletHistory> getSDWalletHistoryForMobile(int userId, String mode, int start, int pageSize, int limit);

    public List<SDWallet> getAvailableSDWalletByUserId(int userId);

    public List<SDWalletHistory> getSDWalletHistoryForRefundByOrderId(String orderId);

    public List<SDWalletHistory> getSDWalletHistoryForRefundByTransactionId(int transactionId);

    public int getExpiryDaysCorrespondingToActivity(int activityId);

    public Integer getTotalAvailableSDWalletBalanceByUserId(int userId);

    public int generateTransactionId(Integer amount);

    public boolean debitSDWalletCash(SDWalletHistory sdWalletHistory, Integer amountDebited, Integer sdWalletRowId);

    public SDWallet mergeSDWallet(SDWallet sdWallet);

    public SDWalletHistory mergeSDWalletHistory(SDWalletHistory sdWalletHistory);

    public List<SDWalletActivityType> getAllActivityTypeData();

    public SDWalletTransaction getSDWalletTransaction(int id);

    public boolean addSDWalletActivityType(SDWalletActivityType sdWalletActivityType);

    public boolean deleteSDWalletActivityTypeById(int id);

    public boolean deleteSDWalletActivityTypeByCode(String code);

    public boolean modifySDWalletActivityType(SDWalletActivityType sdWalletActivityType);

    public SDWalletActivityType getSDWalletActivityTypeById(Integer id);

    public SDWalletActivityType getSDWalletActivityTypeByCode(String code);

    public List<SDWallet> getExpiredSDWallet(int userId);

    public List<SDWallet> getSDWalletOfAvailableCredit(int userId);

    public List<SDWalletHistory> getSDWalletHistoryOfDebit(int userId);

    public List<SDWalletHistory> getSDWalletHistoryOfCredit(int userId);
    
    public Integer getNumberOfRecordsInSDWalletHistory(int userId, String mode);

    public List<SDWalletHistory> getDebitSDWalletHistoryByOrderId(String orderId);


    public List<Integer> getAllUsersFromSDCashHistory(Date startDate, int firstResult, int maxResults);

    List<SDWallet> getExpiredSDWalletInDateRange(int userId, DateRange range);

    public List<SDWalletHistory> getSDWalletHistoryOfDebitInDateRange(Integer userId, DateRange range);

    public List<SDWalletHistory> getSDWalletHistoryOfCreditInDateRange(Integer userId, DateRange range);

	public SDWalletTxnHistory getSDWalletTxnHistoryById(int id);

	public int addSDWalletTxnHistory(
			SDWalletTxnHistory sdWalletTxnHistory);
	
	public SDWalletTxnHistory getSDWalletTxnHistoryByAppIdAndIdempotentId(
			String appId, String idempotentId);

    
}