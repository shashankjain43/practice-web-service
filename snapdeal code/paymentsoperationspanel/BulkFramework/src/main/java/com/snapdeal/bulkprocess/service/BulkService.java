package com.snapdeal.bulkprocess.service;

import com.snapdeal.bulkprocess.model.GenericBulkTaskRequest;

public interface BulkService {
	
	public void submitJob(GenericBulkTaskRequest taskRequest);

}
