/*package com.snapdeal.bulkprocess.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.model.GenericRequestResponseValues;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.registration.TaskExecutor;
import com.snapdeal.payments.ts.response.ExecutorResponse;

@Component
public class BulkRowExecutor implements TaskExecutor<GenericRequestResponseValues> {


	@Autowired
	TaskScheduler taskScheduler;
	
	@Autowired
	IBulkFileRegistration registration;

	public ExecutorResponse execute(GenericRequestResponseValues request) {
		ExecutorResponse executorResponse= new ExecutorResponse();
		registration.getProcessor().execute(request);
		return executorResponse;
	}

}	


*/