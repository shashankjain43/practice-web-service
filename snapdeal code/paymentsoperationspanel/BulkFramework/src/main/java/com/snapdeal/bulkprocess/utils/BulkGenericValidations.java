package com.snapdeal.bulkprocess.utils;

import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;

public class BulkGenericValidations {

	public static boolean preStartChecks(IBulkFileRegistration registration) throws BulkProcessorException{
		if(registration == null){
			throw new BulkProcessorException("BPC-0006", BulkProcessorUtils.REGISTRATION_NOT_FOUND);
		}
		if(registration.getExecutor() == null){
			throw new BulkProcessorException("BPC-0007", BulkProcessorUtils.EXECUTER_NOT_FOUND);
		}
		
		if(registration.getProcessor() == null){
			throw new BulkProcessorException("BPC-0008", BulkProcessorUtils.PROCESSOR_NOT_FOUND);
		}
		return true;
	}

}
