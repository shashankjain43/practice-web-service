package com.snapdeal.ims.dashboard.dbmapper;

import java.util.List;

import com.snapdeal.ims.request.GetDiscrepencyCountServiceRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesServiceRequest;

public interface IDiscrepencyCountDao  
{
	/*
	 * retrieves users whose id corresponding to Freecharge is not null in upgrade table but null in user table 
	 */
	public Integer getFcDiscrepencyCount(GetDiscrepencyCountServiceRequest request);


	/*
	 * retrieves users whose id corresponding to Snapdeal is not null in upgrade table but null in user table 
	 */
	public Integer getSdDiscrepencyCount(GetDiscrepencyCountServiceRequest request);


	/*
	 * retrieves users whose id corresponding to Freecharge and Snapdeal both is not null in upgrade table but null in user table 
	 */
	public Integer getSdFcDiscrepencyCount(GetDiscrepencyCountServiceRequest request);


	/*
	 * retrieves List of emailIds of users having Discrepency in freecharge id 
	 */
	public List<String> getFcDiscrepencyEmailList(GetEmailForDiscrepencyCasesServiceRequest request);


	/*
	 * retrieves List of emailIds of users having Discrepency in Snapdeal id  
	 */
	public List<String> getSdDiscrepencyEmailList(GetEmailForDiscrepencyCasesServiceRequest request);


	/*
	 * retrieves List of emailIds of users having Discrepency in both freecharge and snapdeal id 
	 */
	public List<String> getSdFcDiscrepencyEmailList(GetEmailForDiscrepencyCasesServiceRequest request);
}

