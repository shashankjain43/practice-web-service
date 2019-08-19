package com.snapdeal.ims.task.test;

import com.snapdeal.ims.fortknox.request.FortKnoxRequest;
import com.snapdeal.ims.wallet.request.WalletUserMigrationStatus;

public interface ITaskService {
   
   public void createCompleteTask(String imsIdentifier, 
                                  String businessId, 
                                  String taskId,
                                  FortKnoxRequest fortKnoxRequest,
                                  String emailId, 
                                  WalletUserMigrationStatus status);
   
}
