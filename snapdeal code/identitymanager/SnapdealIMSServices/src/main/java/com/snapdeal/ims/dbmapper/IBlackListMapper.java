package com.snapdeal.ims.dbmapper;

import java.util.List;

import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.dbmapper.entity.User;

public interface IBlackListMapper {

	/**
	 * This function will create a new entity
	 * 
	 * 
	 * @param blacklist
	 */
	public void create(BlackList blackList);

	/**
	 * This function will get all entities blacklisted
	 * 
	 */
	public List<BlackList> getAllEntities();
	
	/**
	 * This function will delete a new entity
	 * 
	 * 
	 * @param blacklist
	 */
	public void remove(BlackList blackList);

}
