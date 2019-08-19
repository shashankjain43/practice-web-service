package com.snapdeal.merchant.taskscheduler;

import com.snapdeal.payments.ts.registration.TaskRequest;

import lombok.Data;

@Data
public class BulkRefundTaskRequest implements TaskRequest{

	private String taskId;
	private String merchantId;
	private String fileIdemKey;
	private String fileName;
	private Integer	 id;
	private String userId;
	private String userName;
	
	@Override
	public String getTaskId() {
		return taskId;
	}

}
