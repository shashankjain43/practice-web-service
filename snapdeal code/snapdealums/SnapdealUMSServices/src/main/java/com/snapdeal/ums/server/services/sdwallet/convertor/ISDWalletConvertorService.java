/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.server.services.sdwallet.convertor;

import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeSRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletHistorySRO;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletSRO;
import com.snapdeal.ums.core.entity.SDWallet;
import com.snapdeal.ums.core.entity.SDWalletActivityType;
import com.snapdeal.ums.core.entity.SDWalletHistory;

public interface ISDWalletConvertorService {

    public SDWalletSRO getSDWalletSROfromEntity(SDWallet sdWallet);

    public SDWallet getSDWalletEntityfromSRO(SDWalletSRO sdWalletSRO);

    public SDWalletHistorySRO getSDWalletHistorySROfromEntity(SDWalletHistory sdWalletHistory);

    public SDWalletHistory getSDWalletHistoryEntityfromSRO(SDWalletHistorySRO sdWalletHistorySRO);

    public SDWalletActivityType getActivityTypeEntityfromSRO(ActivityTypeSRO activityTypeSRO);

    public ActivityTypeSRO getActivityTypeSROfromEntity(SDWalletActivityType activityType);
}
