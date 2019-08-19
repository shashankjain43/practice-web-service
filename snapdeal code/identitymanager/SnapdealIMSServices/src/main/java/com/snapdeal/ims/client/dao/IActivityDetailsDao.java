package com.snapdeal.ims.client.dao;

import com.snapdeal.ims.client.dbmapper.entity.Activity;

/**
 * @author Subhash
 *
 */
public interface IActivityDetailsDao {
	/**
	 * This function will create activity in database
	 * @param activity
	 */
	public long createActivity(Activity activity);
}
