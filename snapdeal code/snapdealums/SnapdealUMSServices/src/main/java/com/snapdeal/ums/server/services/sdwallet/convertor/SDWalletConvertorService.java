/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.server.services.sdwallet.convertor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeSRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;
import com.snapdeal.ums.dao.user.sdwallet.ISDWalletDao;
import com.snapdeal.ums.server.services.IUserServiceInternal;

@Service("umsSDWalletConvertorService")
@Transactional(readOnly = true)
public class SDWalletConvertorService implements ISDWalletConvertorService {

    @Autowired
    private ISDWalletDao         sdWalletDao;

    @Autowired
    private IUserServiceInternal userService;

    @Override
    public SDWalletSRO getSDWalletSROfromEntity(SDWallet sdWallet) {
        if (sdWallet == null)
            return null;
        SDWalletSRO sro = new SDWalletSRO();
        sro.setId(sdWallet.getId());
        sro.setUserId(sdWallet.getUserId());
        sro.setAmount(sdWallet.getAmount());
        sro.setOriginalAmount(sdWallet.getOriginalAmount());
        sro.setExpiry(sdWallet.getExpiry());
        sro.setActivityId(sdWallet.getActivityId());
        sro.setReferenceId(sdWallet.getReferenceId());
        sro.setCreated(sdWallet.getCreated());
        sro.setUpdated(sdWallet.getUpdated());
        return sro;
    }

    @Override
    public SDWallet getSDWalletEntityfromSRO(SDWalletSRO sdWalletSRO) {
        if (sdWalletSRO == null)
            return null;
        SDWallet sdWallet = new SDWallet();
        sdWallet.setId(sdWalletSRO.getId());
        sdWallet.setUserId(sdWalletSRO.getUserId());
        //sdWallet.setUser(userService.getUserById(sdWalletSRO.getUserId()));
        sdWallet.setAmount(sdWalletSRO.getAmount());
        sdWallet.setOriginalAmount(sdWalletSRO.getOriginalAmount());
        sdWallet.setExpiry(sdWalletSRO.getExpiry());
        sdWallet.setActivityId(sdWalletSRO.getActivityId());
        sdWallet.setReferenceId(sdWalletSRO.getReferenceId());
        sdWallet.setCreated(sdWalletSRO.getCreated());
        sdWallet.setUpdated(sdWalletSRO.getUpdated());
        return sdWallet;
    }

    @Override
    public SDWalletHistorySRO getSDWalletHistorySROfromEntity(SDWalletHistory sdWalletHistory) {
        if (sdWalletHistory == null)
            return null;
        SDWalletHistorySRO sro = new SDWalletHistorySRO();
        sro.setId(sdWalletHistory.getId());
        sro.setSdWalletId(sdWalletHistory.getSdWalletId());
        sro.setUserId(sdWalletHistory.getUserId());
        //sro.setUserId(sdWalletHistory.getUser().getId());
        sro.setAmount(sdWalletHistory.getAmount());
        sro.setExpiry(sdWalletHistory.getExpiry());
        sro.setCreated(sdWalletHistory.getCreated());
        sro.setUpdated(sdWalletHistory.getUpdated());
        sro.setMode(sdWalletHistory.getMode());
        sro.setActivityId(sdWalletHistory.getActivityId());
        sro.setTransactionId(sdWalletHistory.getSdWalletTransaction().getId());
        sro.setReferenceId(sdWalletHistory.getReferenceId());
        sro.setSource(sdWalletHistory.getSource());
        sro.setRequestedBy(sdWalletHistory.getRequestedBy());
        return sro;
    }

    @Override
    public SDWalletHistory getSDWalletHistoryEntityfromSRO(SDWalletHistorySRO sdWalletHistorySRO) {
        if (sdWalletHistorySRO == null)
            return null;
        SDWalletHistory sdWalletHistory = new SDWalletHistory();
        sdWalletHistory.setId(sdWalletHistorySRO.getId());
        sdWalletHistory.setSdWalletId(sdWalletHistorySRO.getSdWalletId());
        sdWalletHistory.setUserId(sdWalletHistorySRO.getUserId());
        //sdWalletHistory.setUser(userService.getUserById(sdWalletHistorySRO.getUserId()));
        sdWalletHistory.setAmount(sdWalletHistorySRO.getAmount());
        sdWalletHistory.setExpiry(sdWalletHistorySRO.getExpiry());
        sdWalletHistory.setCreated(sdWalletHistorySRO.getCreated());
        sdWalletHistory.setUpdated(sdWalletHistorySRO.getUpdated());
        sdWalletHistory.setMode(sdWalletHistorySRO.getMode());
        sdWalletHistory.setActivityId(sdWalletHistorySRO.getActivityId());
        sdWalletHistory.setSdWalletTransaction(sdWalletDao.getSDWalletTransaction(sdWalletHistorySRO.getTransactionId()));
        sdWalletHistory.setReferenceId(sdWalletHistorySRO.getReferenceId());
        sdWalletHistory.setSource(sdWalletHistorySRO.getSource());
        sdWalletHistory.setRequestedBy(sdWalletHistorySRO.getRequestedBy());
        return sdWalletHistory;
    }

    @Override
    public SDWalletActivityType getActivityTypeEntityfromSRO(ActivityTypeSRO activityTypeSRO) {
        if (activityTypeSRO == null)
            return null;
        SDWalletActivityType entity = new SDWalletActivityType();
        entity.setAsync(activityTypeSRO.isAsync());
        entity.setCode(activityTypeSRO.getCode());
        entity.setEnabled(activityTypeSRO.isEnabled());
        entity.setId(activityTypeSRO.getId());
        entity.setName(activityTypeSRO.getName());
        entity.setSdCash(activityTypeSRO.getSdCash());
        entity.setExpiryDays(activityTypeSRO.getExpiryDays());
        entity.setCreated(activityTypeSRO.getCreated());
        return entity;
    }

    @Override
    public ActivityTypeSRO getActivityTypeSROfromEntity(SDWalletActivityType activityType) {
        if (activityType == null)
            return null;
        ActivityTypeSRO sro = new ActivityTypeSRO();
        sro.setAsync(activityType.getAsync());
        sro.setCode(activityType.getCode());
        sro.setEnabled(activityType.getEnabled());
        sro.setId(activityType.getId());
        sro.setName(activityType.getName());
        sro.setSdCash(activityType.getSdCash());
        sro.setExpiryDays(activityType.getExpiryDays());
        sro.setCreated(activityType.getCreated());
        return sro;
    }

}
