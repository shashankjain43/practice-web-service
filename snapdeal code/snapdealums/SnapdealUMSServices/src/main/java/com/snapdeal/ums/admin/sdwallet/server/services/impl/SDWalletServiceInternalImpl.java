/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 14-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.server.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.RefundSDWalletAgainstOrderIdResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletServiceInternal;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.core.entity.SDWalletTxnHistory;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.dao.user.sdwallet.ISDWalletDao;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;
import com.snapdeal.ums.services.UMSCheckpointBreachWarningService.CheckpointBreachScenario;

@Transactional
@Service("umsSDWalletServiceInternal")
public class SDWalletServiceInternalImpl implements ISDWalletServiceInternal {
    @Autowired
    private ISDWalletDao         sdWalletDao;

    @Autowired
    private IUserServiceInternal userService;
    
    @Autowired
    private IEmailServiceInternal emailService;
    
	@Autowired
	private IMSService imsService;

    private static final Logger  LOG = LoggerFactory.getLogger(SDWalletServiceInternalImpl.class);
    private static final String MISSING_SOURCE_APPID = "DUMMY-appId";

    @Transactional
    @Override
    public Integer refundSDWalletAgainstOrderId(Integer userId,
    											Integer amount,
    											Integer activityTypeId,
    											String orderCode,
    											String source,
    											String requestedBy,
    											String idempotentId,
    											RequestContextSRO requestContextSRO,
    											RefundSDWalletAgainstOrderIdResponse response){
    	
    	Integer newTransactionId = sdWalletDao.generateTransactionId(amount);
        //check for idempotency
        int sdWalletTxnHistoryId = 0;
        String sourceAppId=null;
        //requestor might be using old client.. Enabling him with newly added idem-potency feature
        if(idempotentId == null){
        	LOG.info("Input idempotentId is missing..continuing"
        			+ " with locally generated unique identifiers..");
			idempotentId = new StringBuilder(UUID.randomUUID().toString())
					.append(System.currentTimeMillis())
					.append(Thread.currentThread().getId())
					.append("_RF").toString();
        }
        if(requestContextSRO == null){
        	LOG.info("Input requestContextSRO is missing..continuing with default dummy appId");
        	sourceAppId = MISSING_SOURCE_APPID;
        }else{
        	sourceAppId = requestContextSRO.getAppIdent();
        }
        SDWalletTxnHistory existingsdWalletTxnHistory = sdWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(sourceAppId,idempotentId);
        if(existingsdWalletTxnHistory == null){
        	SDWalletTxnHistory txnHistory = new SDWalletTxnHistory(idempotentId,sourceAppId,newTransactionId,new Date());
        	sdWalletTxnHistoryId = sdWalletDao.addSDWalletTxnHistory(txnHistory);
            LOG.info("Txn history recorded: "+ txnHistory);
        }else{
            LOG.error(" Duplicate SDWallet Transaction while refundSDWalletAgainstOrderId, hence suppressing it");
        	response.setSuccessful(false);
        	response.setRefundSDWalletAgainstOrderId(existingsdWalletTxnHistory.getSdWalletTxnId());
			response.setMessage("Duplicate request!");
			return null;
        }
        //Moved below user validation to upper layer as we have removed db foriegn integrity constraint
        //User user = userService.getUserByIdWithoutRoles(userId);
        List<SDWalletHistory> sdWalletHistoryListOfOrderId = new ArrayList<SDWalletHistory>();
        sdWalletHistoryListOfOrderId = sdWalletDao.getSDWalletHistoryForRefundByOrderId(orderCode);
        if (sdWalletHistoryListOfOrderId.isEmpty()) {
            Integer expiryDays = 365;
            Date newExpiryDate = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DAY_OF_MONTH, (expiryDays));
            newExpiryDate.setHours(23);
            newExpiryDate.setMinutes(59);
            newExpiryDate.setSeconds(59);
            persistSDWallet(userId, newExpiryDate, amount, amount, newTransactionId, activityTypeId, orderCode, source, requestedBy,sdWalletTxnHistoryId);
            return newTransactionId;
        } else {
            Date newExpiryDate = new Date();
            for (SDWalletHistory sdWalletHistoryList : sdWalletHistoryListOfOrderId) {
                if ((int) ((sdWalletHistoryList.getExpiry().getTime() - DateUtils.getCurrentDate().getTime()) / (1000 * 60 * 60 * 24)) > sdWalletDao.getExpiryDaysCorrespondingToActivity(getActivityTypeByCode(
                        "rfexp").getId()))
                    newExpiryDate = sdWalletHistoryList.getExpiry();
                else {
                    newExpiryDate = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DAY_OF_MONTH,
                            (sdWalletDao.getExpiryDaysCorrespondingToActivity(getActivityTypeByCode("rfexp").getId())));
                    newExpiryDate.setHours(23);
                    newExpiryDate.setMinutes(59);
                    newExpiryDate.setSeconds(59);
                }
                if ((amount - sdWalletHistoryList.getAmount()) > 0) {
                    persistSDWallet(userId, newExpiryDate, sdWalletHistoryList.getAmount(), sdWalletHistoryList.getAmount(), newTransactionId, activityTypeId, orderCode, source,
                            requestedBy,sdWalletTxnHistoryId);
                    amount = amount - sdWalletHistoryList.getAmount();
                } else if ((amount - sdWalletHistoryList.getAmount()) <= 0) {
                    persistSDWallet(userId, newExpiryDate, amount, amount, newTransactionId, activityTypeId, orderCode, source, requestedBy,sdWalletTxnHistoryId);
                    break;
                }
            }
            return newTransactionId;
        }
    }

    @Transactional
    @Override
    public Integer creditSDWallet(Integer userId, Integer amount, Integer activityTypeId, String orderCode, Integer transactionId, String source, Integer expiryDay,
            String requestedBy, String idempotentId, RequestContextSRO requestContextSRO,CreditSDWalletResponse response){
        Integer newTransactionId = sdWalletDao.generateTransactionId(amount);
        //Moved below user validation to upper layer as we have removed db foriegn integrity constraint
        //User user = userService.getUserById(userId);
        //check for idem-potency
        int sdWalletTxnHistoryId = 0;
        //requestor might be using old client.. Enabling him with newly added idem-potency feature
        String sourceAppId=null;
        if(idempotentId == null){
        	LOG.info("Input idempotentId is missing..continuing"
        			+ " with locally generated unique identifiers..");
			idempotentId = new StringBuilder(UUID.randomUUID().toString())
					.append(System.currentTimeMillis())
					.append(Thread.currentThread().getId())
					.append("_CR").toString();
        }
        if(requestContextSRO == null){
        	LOG.info("Input requestContextSRO is missing..continuing with default dummy appId");
        	sourceAppId = MISSING_SOURCE_APPID;
        }else{
        	sourceAppId = requestContextSRO.getAppIdent();
        }
        SDWalletTxnHistory existingsdWalletTxnHistory = sdWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(sourceAppId,idempotentId);
        if(existingsdWalletTxnHistory == null){
        	SDWalletTxnHistory txnHistory = new SDWalletTxnHistory(idempotentId,sourceAppId,newTransactionId,new Date());
        	sdWalletTxnHistoryId = sdWalletDao.addSDWalletTxnHistory(txnHistory);
            LOG.info("Txn history recorded: "+ txnHistory);
        }else{
            LOG.error(" Duplicate SDWallet Transaction while creditSDWallet, hence suppressing it");
        	response.setSuccessful(false);
        	response.setTransactionId(existingsdWalletTxnHistory.getSdWalletTxnId());
			response.setMessage("Duplicate request!");
			return null;
        }
        
        if (transactionId != null) {
            List<SDWalletHistory> sdWalletHistoryListOfTransactionId = new ArrayList<SDWalletHistory>();
            sdWalletHistoryListOfTransactionId = sdWalletDao.getSDWalletHistoryForRefundByTransactionId(transactionId);
            Date newExpiryDate = new Date();
            for (SDWalletHistory sdWalletHistoryList : sdWalletHistoryListOfTransactionId) {
                if ((int) ((sdWalletHistoryList.getExpiry().getTime() - DateUtils.getCurrentDate().getTime()) / (1000 * 60 * 60 * 24)) > sdWalletDao.getExpiryDaysCorrespondingToActivity(1))
                    newExpiryDate = sdWalletHistoryList.getExpiry();
                else {
                    newExpiryDate = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DAY_OF_MONTH, (sdWalletDao.getExpiryDaysCorrespondingToActivity(1)));
                    newExpiryDate.setHours(23);
                    newExpiryDate.setMinutes(59);
                    newExpiryDate.setSeconds(59);
                }
                if ((amount - sdWalletHistoryList.getAmount()) > 0) {
                    persistSDWallet(userId, newExpiryDate, sdWalletHistoryList.getAmount(), sdWalletHistoryList.getAmount(), newTransactionId, activityTypeId, orderCode, source,
                            requestedBy, sdWalletTxnHistoryId);
                    amount = amount - sdWalletHistoryList.getAmount();
                } else if ((amount - sdWalletHistoryList.getAmount()) <= 0) {
                    persistSDWallet(userId, newExpiryDate, amount, amount, newTransactionId, activityTypeId, orderCode, source, requestedBy, sdWalletTxnHistoryId);
                    break;
                }
            }
        } else if (transactionId == null) {
            Integer expiryDays = (expiryDay > 0) ? expiryDay : sdWalletDao.getExpiryDaysCorrespondingToActivity(activityTypeId);
            Date newExpiryDate = DateUtils.addToDate(DateUtils.getCurrentDate(), Calendar.DAY_OF_MONTH, (expiryDays));
            newExpiryDate.setHours(23);
            newExpiryDate.setMinutes(59);
            newExpiryDate.setSeconds(59);
            persistSDWallet(userId, newExpiryDate, amount, amount, newTransactionId, activityTypeId, orderCode, source, requestedBy, sdWalletTxnHistoryId);
        }
        return newTransactionId;
    }

    @Transactional
    @Override
    public Integer debitSDWallet(Integer userId, Integer amount, Integer activityTypeId, String orderCode, String source, 
    		String requestedBy, String idempotentId, RequestContextSRO requestContextSRO,DebitSDWalletResponse response) {
    	//Moved below user validation to upper layer as we have removed db foriegn integrity constraint
        //User user = userService.getUserById(userId);
        Integer debitedAmount = amount;
        Integer totalAvailableSDWalletBalance = sdWalletDao.getTotalAvailableSDWalletBalanceByUserId(userId);
        if (totalAvailableSDWalletBalance >= amount) {
            Integer newTransactionId = sdWalletDao.generateTransactionId(amount);
            //check for idempotency
            int sdWalletTxnHistoryId = 0;
            String sourceAppId=null;
            //requestor might be using old client.. Enabling him with newly added idem-potency feature
            if(idempotentId == null){
            	LOG.info("Input idempotentId is missing..continuing"
            			+ " with locally generated unique identifiers..");
				idempotentId = new StringBuilder(UUID.randomUUID().toString())
						.append(System.currentTimeMillis())
						.append(Thread.currentThread().getId())
						.append("_DB").toString();
            }
            if(requestContextSRO == null){
            	LOG.info("Input requestContextSRO is missing..continuing with default dummy appId");
            	sourceAppId = MISSING_SOURCE_APPID;
            }else{
            	sourceAppId = requestContextSRO.getAppIdent();
            }
            SDWalletTxnHistory existingsdWalletTxnHistory = sdWalletDao.getSDWalletTxnHistoryByAppIdAndIdempotentId(sourceAppId,idempotentId);
            if(existingsdWalletTxnHistory == null){
            	SDWalletTxnHistory txnHistory = new SDWalletTxnHistory(idempotentId,sourceAppId,newTransactionId,new Date());
            	sdWalletTxnHistoryId = sdWalletDao.addSDWalletTxnHistory(txnHistory);
                LOG.info("Txn history recorded: "+ txnHistory);
            }else{
                LOG.error(" Duplicate SDWallet Transaction while debitSDWallet, hence suppressing it");
            	response.setSuccessful(false);
            	response.setTransactionId(existingsdWalletTxnHistory.getSdWalletTxnId());
    			response.setMessage("Duplicate request!");
    			return null;
            }
            List<SDWallet> sdWalletListForDebiting = new ArrayList<SDWallet>();
            sdWalletListForDebiting = sdWalletDao.getAvailableSDWalletByUserId(userId);
            if (!(sdWalletListForDebiting.isEmpty())) {
                for (SDWallet sdWallet : sdWalletListForDebiting) {
                    if (((sdWallet.getAmount() - amount) < 0) && (sdWallet.getAmount() > 0)) {
                        amount = ((-1) * (sdWallet.getAmount() - amount));
                        debitedAmount = sdWallet.getAmount();
                        updateSDWallet(userId, sdWallet, debitedAmount, activityTypeId, orderCode, source, newTransactionId, requestedBy, sdWalletTxnHistoryId);
                    } else if (((sdWallet.getAmount() - amount) >= 0) && (sdWallet.getAmount() > 0)) {
                        debitedAmount = amount;
                        updateSDWallet(userId, sdWallet, debitedAmount, activityTypeId, orderCode, source, newTransactionId, requestedBy, sdWalletTxnHistoryId);
                        break;
                    }
                }
            }
            return newTransactionId;
        } else {
            return null;
        }
    }

    public void persistSDWallet(Integer userId, Date newExpiryDate, Integer originalAmount, Integer amount, Integer newTransactionId, Integer activityTypeId, String orderCode,
            String source, String requestedBy, int sdWalletTxnHistoryId) {
        SDWallet sdWallet = new SDWallet();
        sdWallet.setUserId(userId);
        sdWallet.setAmount(amount);
        sdWallet.setOriginalAmount(originalAmount);
        sdWallet.setExpiry(newExpiryDate);
        sdWallet.setActivityId(activityTypeId);
        sdWallet.setReferenceId(orderCode);
        sdWallet.setCreated(new Date());
        LOG.info("Writing into SDWallet...");
        SDWallet updatedSDWallet = sdWalletDao.mergeSDWallet(sdWallet);
        LOG.info("Succesfully written into SDWallet: "+updatedSDWallet);

        SDWalletHistory sdWalletHistory = new SDWalletHistory();
        sdWalletHistory.setSdWalletId(updatedSDWallet.getId());
        sdWalletHistory.setUserId(userId);
        sdWalletHistory.setAmount(amount);
        sdWalletHistory.setExpiry(newExpiryDate);
        sdWalletHistory.setCreated(new Date());
        sdWalletHistory.setMode("cr");
        sdWalletHistory.setActivityId(activityTypeId);
        sdWalletHistory.setSdWalletTransaction(sdWalletDao.getSDWalletTransaction(newTransactionId));
        sdWalletHistory.setReferenceId(orderCode);
        sdWalletHistory.setSource(source);
        sdWalletHistory.setRequestedBy(requestedBy);
        sdWalletHistory.setSdWalletTxnHistoryId(sdWalletTxnHistoryId);
        LOG.info("Writing into SDWalletHistory...");
        sdWalletDao.mergeSDWalletHistory(sdWalletHistory);
        LOG.info("Succesfully written into SDWalletHistory: "+sdWalletHistory);
    }

    public void updateSDWallet(Integer userId, SDWallet sdWallet, Integer debitedAmount, Integer activityTypeId, String orderCode, String source, Integer newTransactionId,
            String requestedBy, int sdWalletTxnHistoryId) {
        sdWallet.setAmount(sdWallet.getAmount() - debitedAmount);
        SDWallet updateSdWallet = sdWalletDao.mergeSDWallet(sdWallet);
        SDWalletHistory sdWalletHistory = new SDWalletHistory();
        sdWalletHistory.setSdWalletId(updateSdWallet.getId());
        sdWalletHistory.setUserId(userId);
        sdWalletHistory.setAmount(debitedAmount);
        sdWalletHistory.setExpiry(sdWallet.getExpiry());
        sdWalletHistory.setCreated(new Date());
        sdWalletHistory.setMode("db");
        sdWalletHistory.setActivityId(activityTypeId);
        sdWalletHistory.setSdWalletTransaction(sdWalletDao.getSDWalletTransaction(newTransactionId));
        sdWalletHistory.setReferenceId(orderCode);
        sdWalletHistory.setSource(source);
        sdWalletHistory.setRequestedBy(requestedBy);
        sdWalletHistory.setSdWalletTxnHistoryId(sdWalletTxnHistoryId);
        sdWalletDao.mergeSDWalletHistory(sdWalletHistory);
    }

    @Override
    public List<SDWallet> getSDWalletByUserId(Integer userId,int limit) {
        List<SDWallet> sdWallet = sdWalletDao.getSDWalletByUserId(userId,limit);
        return sdWallet;
    }

    @Override
    public List<SDWallet> getExpiredSDWallet(Integer userId) {
        List<SDWallet> sdWallets = sdWalletDao.getExpiredSDWallet(userId);
        return sdWallets;
    }

    @Override
    public List<SDWallet> getSDWalletOfAvailableCredit(Integer userId) {
        List<SDWallet> sdList = sdWalletDao.getSDWalletOfAvailableCredit(userId);
        return sdList;
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryOfDebit(Integer userId) {
        List<SDWalletHistory> sdHistories = sdWalletDao.getSDWalletHistoryOfDebit(userId);
        return sdHistories;
    }

    @Override
    public List<SDWalletHistory> getCompleteSDWalletHistoryByUserId(Integer userId) {
        List<SDWalletHistory> histories = sdWalletDao.getSDWalletHistoryByUserId(userId);
        return histories;
    }
    
    @Override
    public List<SDWalletHistory> getCompleteSDWalletHistoryByUserId(Integer userId, int limit) {
        List<SDWalletHistory> histories = sdWalletDao.getSDWalletHistoryByUserId(userId, limit);
        return histories;
    }

    @Override
    public List<SDWalletHistory> getDebitSDWalletHistoryByOrderId(String orderId) {
        List<SDWalletHistory> histories = sdWalletDao.getDebitSDWalletHistoryByOrderId(orderId);
        return histories;
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryForMobile(Integer userId, String mode, Integer start, Integer pageSize, int limit) {
        List<SDWalletHistory> sdWalletHistory = sdWalletDao.getSDWalletHistoryForMobile(userId, mode, start, pageSize, limit);
        return sdWalletHistory;
    }

    @Override
    public Integer getNumberOfRecordsInSDWalletHistory(Integer userId, String mode) {
        Integer numberOfRecords = sdWalletDao.getNumberOfRecordsInSDWalletHistory(userId, mode);
        if (numberOfRecords != null)
            return numberOfRecords;
        else
            return null;
    }

    @Override
    public Integer getAvailableBalanceInSDWalletByUserId(Integer userId) {
        Integer sdBalance = sdWalletDao.getTotalAvailableSDWalletBalanceByUserId(userId);
        if (sdBalance != null)
            return sdBalance;
        else
            return null;
    }

    @Override
    public List<SDWalletActivityType> getAllActivityTypeData() {
        List<SDWalletActivityType> activityTypes = sdWalletDao.getAllActivityTypeData();
        return activityTypes;
    }

    @Override
    public boolean addSDWalletActivityType(String name, String code, String sdCash, Integer expiryDays, boolean async, boolean enabled) {
        SDWalletActivityType sdWalletActivityType = new SDWalletActivityType();
        sdWalletActivityType.setName(name);
        sdWalletActivityType.setCode(code);
        sdWalletActivityType.setSdCash(sdCash);
        sdWalletActivityType.setExpiryDays(expiryDays);
        sdWalletActivityType.setAsync(async);
        sdWalletActivityType.setEnabled(enabled);
        sdWalletActivityType.setCreated(new Date());
        sdWalletDao.addSDWalletActivityType(sdWalletActivityType);
        return true;
    }

    @Override
    public boolean deleteSDWalletActivityType(Integer id, String code) {
        if (id > 0) {
            sdWalletDao.deleteSDWalletActivityTypeById(id);
        } else if (StringUtils.isNotEmpty(code)) {
            sdWalletDao.deleteSDWalletActivityTypeByCode(code);
        }
        return true;
    }

    @Override
    public boolean modifySDWalletActivityType(SDWalletActivityType sdWalletActivityType) {
        if (StringUtils.isNotEmpty(sdWalletActivityType.getCode())) {
            sdWalletDao.modifySDWalletActivityType(sdWalletActivityType);
            return true;
        } else
            return false;
    }

    @Override
    public boolean isActivityTypeCodeExists(String code) {
        SDWalletActivityType sdWalletActivityType = sdWalletDao.getSDWalletActivityTypeByCode(code);
        if (sdWalletActivityType != null)
            return true;
        else
            return false;
    }

    @Override
    public SDWalletActivityType getActivityTypeById(Integer id) {
        return sdWalletDao.getSDWalletActivityTypeById(id);
    }

    @Override
    public SDWalletActivityType getActivityTypeByCode(String activityCode) {
        return sdWalletDao.getSDWalletActivityTypeByCode(activityCode);
    }

    @Override
    public int getUserSDCashUsedThisMonth(DateRange range, Integer userId) {
        int sdcashUsed = 0;
        /*
         * check all debits where mode = db and source = OMS
         */
        List<SDWalletHistory> history = sdWalletDao.getSDWalletHistoryOfDebitInDateRange(userId, range);
        for (SDWalletHistory item : history) {
            sdcashUsed += item.getAmount();
        }
        return sdcashUsed;
    }

    @Override
    public int getUserSDCashEarningOfMonth(DateRange range, Integer userId) {
        int sdcashEarning = 0;
        /*
         * check all credits where mode=cr
         */
        List<SDWalletHistory> history = sdWalletDao.getSDWalletHistoryOfCreditInDateRange(userId, range);
        for (SDWalletHistory item : history) {
            sdcashEarning += item.getAmount();
        }
        return sdcashEarning;
    }

    @Override
    public int getUserSDCashOnDate(Date givenDate, int userId) {
        int sdCashAtEndOfMonth = 0;
        List<SDWalletHistory> history = sdWalletDao.getSDWalletHistoryByUserId(userId);
        int credits = 0;
        int debits = 0;
        int expired = 0;
        //Build data per SDWalletId
        Map<Integer, List<ModeData>> sdWalletIdVsAmount = new HashMap<Integer, List<ModeData>>();
        for (SDWalletHistory element : history) {
            if (DateUtils.after(element.getCreated(), givenDate))
                continue;
            Integer sdWalletId = element.getSdWalletId();
            List<ModeData> modeVsAmount = sdWalletIdVsAmount.get(sdWalletId);
            if (modeVsAmount == null) {
                modeVsAmount = new ArrayList<ModeData>();
                sdWalletIdVsAmount.put(sdWalletId, modeVsAmount);
            }
            modeVsAmount.add(new ModeData(element.getMode(), element.getAmount(), element.getExpiry()));

        }

        for (List<ModeData> perSDWalletData : sdWalletIdVsAmount.values()) {
            int tempCredit = 0;
            int tempDebit = 0;
            int possibleExpiry = 0;
            Date expiry = null;
            for (ModeData data : perSDWalletData) {

                if (data.mode.equalsIgnoreCase("cr")) {
                    tempCredit += data.amount;
                    expiry = data.expiry;
                }
                if (data.mode.equalsIgnoreCase("db"))
                    tempDebit += data.amount;
            }
            possibleExpiry = tempCredit - tempDebit;
            if (DateUtils.after(givenDate, expiry)) {
                expired += possibleExpiry;
            }
            credits += tempCredit;
            debits += tempDebit;
        }

        //Now calculate
        sdCashAtEndOfMonth = credits - debits - expired;

        return sdCashAtEndOfMonth;
    }

    @Override
    public List<Integer> getAllUsersFromSDCashHistory(Date startDate, int firstResult, int maxResults) {
        return sdWalletDao.getAllUsersFromSDCashHistory(startDate, firstResult, maxResults);
    }

    @Override
    public int getUserSDWalletExpiredThisMonth(DateRange range, Integer userId) {
        int sdCashExpired = 0;
        List<SDWallet> sdWallets = sdWalletDao.getExpiredSDWalletInDateRange(userId, range);
        for (SDWallet item : sdWallets) {
            sdCashExpired += item.getAmount();
        }
        return sdCashExpired;
    }

    class ModeData {
        String mode;
        int    amount;
        Date   expiry;

        public ModeData(String mode, int amount, Date expiry) {
            super();
            this.mode = mode;
            this.amount = amount;
            this.expiry = expiry;
        }

    }
    
//	@Override
//	public void creditSDWalletAndSendEmail(Integer userId, Integer amount,
//			Integer activityTypeId, String orderCode, Integer transactionId,
//			String source, Integer expiryDays, String requestedBy,
//			boolean sendEmail) {
//		
//CreditSDWalletRequest newCreditSDWalletRequest=new CreditSDWalletRequest();
//		
//		newCreditSDWalletRequest.setUserId(userId);
//		newCreditSDWalletRequest.setActivityTypeId(activityTypeId);
//		newCreditSDWalletRequest.setAmount(amount);
//		newCreditSDWalletRequest.setOrderCode(orderCode);
//		newCreditSDWalletRequest.setExpiryDays(expiryDays);
//		newCreditSDWalletRequest.setRequestedBy(requestedBy);
//		newCreditSDWalletRequest.setSource(source);
//		newCreditSDWalletRequest.setTransactionId(transactionId);
//		creditSDWallet(newCreditSDWalletRequest.getUserId(),newCreditSDWalletRequest.getAmount(),newCreditSDWalletRequest.getActivityTypeId(),newCreditSDWalletRequest.getOrderCode(),newCreditSDWalletRequest.getTransactionId(),newCreditSDWalletRequest.getSource(),newCreditSDWalletRequest.getExpiryDays(),newCreditSDWalletRequest.getRequestedBy());
//		if(sendEmail==true)
//		{
//			String email=userService.getUserEmailById(userId);
//			//User user = userService.getUserById(userId);
//			emailService.sendSDWalletEmail("lovey.agrawal@snapdeal.com", amount, expiryDays);
//		}
//		LOG.info("sendEmail");
//	}


}
