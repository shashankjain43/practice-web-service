package com.snapdeal.ims.test;

import java.util.UUID;

import lombok.Data;

import com.snapdeal.payments.ts.registration.TaskRequest;

@Data
public class TestTaskRequest implements TaskRequest {

	private String email;
	private int taskNumber;
	@Override
	public String getTaskId() {
		return UUID.randomUUID().toString();
	}

}
