package com.snapdeal.opspanel.userpanel.service;

import java.util.concurrent.Future;

import com.snapdeal.opspanel.userpanel.bulk.BulkCSVRow;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;

public interface AsyncBulkService {

	public Future<BulkCSVRow> executeRow( int rowNumber, BulkCSVRow csvRow, ActionBulkRequest actionBulkRequest );
}
