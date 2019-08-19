package com.snapdeal.opspanel.batch.utils;

public class BatchUtils {
	
	public static String[] bulkRefundColumns = {"id","amount","comments","platformId","feeReversalCode"};
	public static String[] bulkOutputRefundColumns = {"id","amount","comments","platformId","feeReversalCode","status","message"};

}
