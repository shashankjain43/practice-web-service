package com.snapdeal.ims.service;

import com.snapdeal.ims.fortknox.request.FortKnoxRequest;

/**
 * This interface is responsible for FortKnox task creation.
 */
public interface IFortKnoxService {

	/**
	 * This method will create FortKnot task. Once task is created it will be
	 * picked by task scheduler for execution
	 * 
	 * @param fortKnoxRequest
	 */
	
	void createFortKnotTask(FortKnoxRequest fortKnoxRequest);
}
