package com.snapdeal.opspanel.userpanel.bulk;

import java.util.List;

import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;


public interface BulkProcessor {

   public List<BulkCSVRow> executeOperation( List<BulkCSVRow> bulkCSVRowList, ActionBulkRequest actionBulkRequest );

}
