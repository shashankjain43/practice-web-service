/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 14-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.dao.user.sdwallet.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.core.entity.SDWalletTransaction;
import com.snapdeal.ums.core.entity.SDWalletTxnHistory;
import com.snapdeal.ums.dao.user.sdwallet.ISDWalletDao;

@Repository("sdWalletDao")
@SuppressWarnings("unchecked")
public class SDWalletDaoImpl implements ISDWalletDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<SDWallet> getSDWalletByUserId(int userId, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWallet where userId=:userId order by created desc");
        query.setMaxResults(limit);
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<SDWallet> getExpiredSDWallet(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWallet where userId=:userId and expiry<current_date()");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<SDWallet> getExpiredSDWalletInDateRange(int userId , DateRange range) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWallet where userId=:userId and expiry>=:begin and expiry<=:end ");
        query.setParameter("userId", userId);
        query.setParameter("begin", range.getStart());
        query.setParameter("end", range.getEnd());
        return query.list();
    }
    
    @Override
    public List<SDWallet> getSDWalletOfAvailableCredit(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWallet as sdwallet where sdwallet.userId=:userId and sdwallet.expiry>=current_date()");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryOfDebit(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId and mode=:mode");
        query.setParameter("userId", userId);
        query.setParameter("mode", "db");
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryOfCredit(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId and mode=:mode");
        query.setParameter("userId", userId);
        query.setParameter("mode", "cr");
        return query.list();
    }
    @Override
    public List<SDWalletHistory> getSDWalletHistoryByUserId(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId");
        query.setParameter("userId", userId);
        return query.list();
    }
    
    @Override
    public List<SDWalletHistory> getSDWalletHistoryByUserId(Integer userId, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId");
        query.setMaxResults(limit);
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryForMobile(int userId, String mode, int start, int pageSize, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId and mode=:mode order by created desc" );
        query.setParameter("userId", userId);
        query.setParameter("mode", mode);
        query.setFirstResult(start);
        query.setMaxResults(pageSize);
        query.setMaxResults(limit);
        return query.list();
    }

    @Override
    public Integer getNumberOfRecordsInSDWalletHistory(int userId, String mode) {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(id) from SDWalletHistory where userId=:userId and mode=:mode");
        query.setParameter("userId", userId);
        query.setParameter("mode", mode);
        Long l = (Long) query.uniqueResult();
        if (l != null)
            return l.intValue();
        else
            return null;
    }

    @Override
    public Integer getTotalAvailableSDWalletBalanceByUserId(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "select sum(sdwallet.amount) from SDWallet as sdwallet where sdwallet.userId=:userId and sdwallet.expiry>=current_date()");
        query.setParameter("userId", userId);
        Long l = (Long) query.uniqueResult();
        if (l != null)
            return l.intValue();
        else
            return null;
    }

     @Override
    public boolean debitSDWalletCash(SDWalletHistory sdWalletHistory, Integer amountDebited, Integer sdWalletRowId) {
        sessionFactory.getCurrentSession().persist(sdWalletHistory);

        Query query = sessionFactory.getCurrentSession().createQuery(
                "UPDATE SDWallet sdwallet SET sdwallet.amount = sdwallet.amount - :amountDebited WHERE sdwallet.id = :sdWalletRowId");
        query.setParameter("amountDebited", amountDebited);
        query.setParameter("sdWalletRowId", sdWalletRowId);
        return query.executeUpdate() > 0;
    }

    //    @Override
    //    public void creditSDWAlletCash(SDWallet sdWallet, SDWalletHistory sdWalletHistory) {
    //        sessionFactory.getCurrentSession().persist(sdWalletHistory);
    //        sessionFactory.getCurrentSession().persist(sdWallet);
    //    }

    @Override
    public int generateTransactionId(Integer amount) {
        SDWalletTransaction sdWalletTransaction = new SDWalletTransaction();
        sdWalletTransaction.setAmount(amount);
        sdWalletTransaction.setCreated(new Date());
        sessionFactory.getCurrentSession().persist(sdWalletTransaction);
        return sdWalletTransaction.getId();
    }

    @EnableMonitoring
    @Override
    public List<SDWallet> getAvailableSDWalletByUserId(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWallet where userId=:userId and expiry>=current_date() order by expiry");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryForRefundByOrderId(String orderId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where referenceId=:orderId order by expiry desc");
        query.setParameter("orderId", orderId);
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getDebitSDWalletHistoryByOrderId(String orderId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where referenceId=:orderId and mode=:mode order by expiry");
        query.setParameter("orderId", orderId);
        query.setParameter("mode", "db");
        return query.list();
    }

    @EnableMonitoring
    @Override
    public List<SDWalletHistory> getSDWalletHistoryForRefundByTransactionId(int transactionId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where transactionId=:trasactionId order by expiry desc");
        query.setParameter("transactionId", transactionId);
        return query.list();
    }

    @Override
    public int getExpiryDaysCorrespondingToActivity(int activityId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select expiryDays from SDWalletActivityType where id=:activityId");
        query.setParameter("activityId", activityId);
        return (Integer) query.uniqueResult();
    }

    @Override
    public List<SDWalletActivityType> getAllActivityTypeData() {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletActivityType");
        return query.list();
    }

    @Override
    public SDWalletTransaction getSDWalletTransaction(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletTransaction where id=:id");
        query.setParameter("id", id);
        return (SDWalletTransaction) query.uniqueResult();
    }

    @Override
    public boolean addSDWalletActivityType(SDWalletActivityType sdWalletActivityType) {
        sessionFactory.getCurrentSession().persist(sdWalletActivityType);
        return true;
    }

    @Override
    public boolean deleteSDWalletActivityTypeById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from SDWalletActivityType where id=:id");
        query.setParameter("id", id);
        return true;
    }

    @Override
    public boolean deleteSDWalletActivityTypeByCode(String code) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from SDWalletActivityType where code=:code");
        query.setParameter("code", code);
        return true;
    }

    @Override
    public boolean modifySDWalletActivityType(SDWalletActivityType sdWalletActivityType) {
        sessionFactory.getCurrentSession().merge(sdWalletActivityType);
        return true;
    }

    @Override
    public SDWalletActivityType getSDWalletActivityTypeById(Integer id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletActivityType where id=:id");
        query.setParameter("id", id);
        return (SDWalletActivityType) query.uniqueResult();
    }

    @Override
    public SDWalletActivityType getSDWalletActivityTypeByCode(String code) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletActivityType where code=:code");
        query.setParameter("code", code);
        return (SDWalletActivityType) query.uniqueResult();
    }

    @Override
    public SDWallet mergeSDWallet(SDWallet sdWallet) {
        return (SDWallet) sessionFactory.getCurrentSession().merge(sdWallet);
    }

    @Override
    public SDWalletHistory mergeSDWalletHistory(SDWalletHistory sdWalletHistory) {
        return (SDWalletHistory) sessionFactory.getCurrentSession().merge(sdWalletHistory);
    }

    @Override
    public List<Integer> getAllUsersFromSDCashHistory(Date startDate, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("select DISTINCT(userId) from SDWalletHistory where created>= :startDate");
        query.setParameter("startDate", startDate);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return (List<Integer>) query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryOfDebitInDateRange(Integer userId, DateRange range) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId and mode=:mode and created>=:begin and created<=:end");
        query.setParameter("userId", userId);
        query.setParameter("mode", "db");
        query.setParameter("begin", range.getStart());
        query.setParameter("end", range.getEnd());
        return query.list();
    }

    @Override
    public List<SDWalletHistory> getSDWalletHistoryOfCreditInDateRange(Integer userId, DateRange range) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletHistory where userId=:userId and mode=:mode and created>=:begin and created<=:end");
        query.setParameter("userId", userId);
        query.setParameter("mode", "cr");
        query.setParameter("begin", range.getStart());
        query.setParameter("end", range.getEnd());
        return query.list();
    }
    
    @Override
	public SDWalletTxnHistory getSDWalletTxnHistoryById(int id) {
		Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletTxnHistory where id=:id");
        query.setParameter("id", id);
        return (SDWalletTxnHistory) query.uniqueResult();
	}

	@Override
	public int addSDWalletTxnHistory(SDWalletTxnHistory sdWalletTxnHistory) {
		return (Integer) sessionFactory.getCurrentSession().save(sdWalletTxnHistory);
	}

	@Override
	public SDWalletTxnHistory getSDWalletTxnHistoryByAppIdAndIdempotentId(String appId,
			String idempotentId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from SDWalletTxnHistory where sourceUniqueTxnId= :idempotentId"
				+ " and sourceAppId= :appId");
        query.setParameter("idempotentId", idempotentId);
        query.setParameter("appId", appId);
        return (SDWalletTxnHistory) query.uniqueResult();
	}

    

   
}
