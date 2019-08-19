package com.snapdeal.ims.activity.dbmapper;

import com.snapdeal.ims.client.dbmapper.entity.Activity;

/**
 * @author Kishan
 *
 */
public interface IActivityDetailsMapper {
	/**
	 * This function will create activity in database
	 * @param activityDetailsEntity
	 */
	public long createActivity(Activity activityDetailsEntity);

}
