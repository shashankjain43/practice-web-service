package com.snapdeal.opspanel.userpanel.bulk;

import lombok.Data;

@Data
public class BulkCSVRow {

   String id;

   String status;

   public BulkCSVRow( String id, String status ) {
	   this.id = id;
	   this.status = status;
   }

   public BulkCSVRow() {
   }
}
