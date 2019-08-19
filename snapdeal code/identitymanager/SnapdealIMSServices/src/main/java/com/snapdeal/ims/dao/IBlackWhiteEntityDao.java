package com.snapdeal.ims.dao;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.dbmapper.entity.User;

public interface IBlackWhiteEntityDao {
	/**
	 * This function will create a new entity to be blacklisted
	 * 
	 * @param BlackList
	 */
	public void create(BlackList blackList);

	/**
	 * This function will get all entities blacklisted
	 * 
	 */
	public List<BlackList> getAllEntities();
	
	/**
	 * This function will delete an entity blacklisted
	 * 
	 * @param BlackList
	 */
	public void remove(BlackList blackList);

}
