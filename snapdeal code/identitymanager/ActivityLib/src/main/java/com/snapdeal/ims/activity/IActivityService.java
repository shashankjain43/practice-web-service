package com.snapdeal.ims.activity;


/**
 * This interface should be implemented by client and the methods should be
 * handled appropriately.
 * 
 * @author kishan
 *
 */

public interface IActivityService {

	boolean logActivity(Object request, Object response, String className, String methodName);

}
