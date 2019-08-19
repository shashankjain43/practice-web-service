package com.snapdeal.opspanel.userpanel.bulk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;

@Component
public class BulkProcessorAdaptor {

   @Autowired
   private ActionBulkProcessor actionBulkProcessor;

   public List<BulkCSVRow> executeOperation( List<BulkCSVRow> bulkCSVRowList, ActionBulkRequest actionBulkRequest) {
      switch( actionBulkRequest.getAction() ) {
         case ENABLE_USER:
         case DISABLE_USER:
         case SUSPEND_WALLET:
         case BLACK_LIST_USER:
         case WHITE_LIST_USER:
         case ENABLE_WALLET:
         case CLOSE_USER_ACCOUNT:
            return actionBulkProcessor.executeOperation(bulkCSVRowList, actionBulkRequest );
      }
      return null;
   }
}
