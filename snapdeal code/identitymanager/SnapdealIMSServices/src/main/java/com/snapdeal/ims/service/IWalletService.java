package com.snapdeal.ims.service;

import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;


/**
 * This interface is responsible for SDMoney/Wallet creation.
 */
public interface IWalletService {
   
   /**
    * This method will create walletCreationTask. Once task is 
    * created it will be picked by task scheduler for execution
    * @param String: imsIdentifier
    * @param String: businessId
    * @param String: taskId 
    */
   public void createSDWalletTask(String imsIdentifier, 
                                  String businessId, 
                                  String taskId);
   
   /**
    * this method will create Migration State changes in task table. 
    * and then Executor will pick this and will notify wallet team.    * 
    * @param imsIdentifier
    * @param businessId
    * @param taskId
    */
   public void createNotificationMigrationStateChangeTask(String imsIdentifier, 
           String businessId, 
           String emailId, WalletUserMigrationStatus status);
}
