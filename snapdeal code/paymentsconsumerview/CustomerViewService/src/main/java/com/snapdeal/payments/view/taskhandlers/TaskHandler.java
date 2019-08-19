/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

/**
 * @author shubham.bansal
 *
 */
public interface TaskHandler<Req,Res> {
	public void execute(Req request) throws Exception;

}
