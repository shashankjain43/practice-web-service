package com.snapdeal.ums.server.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
/**
 * This acts as a wrapper for using ims services.
 * @author shashank
 *
 */
@Service("imsService")
public class IMSService {

	private static final Logger LOG = LoggerFactory.getLogger(IMSService.class);

	@Autowired
	private IUserServiceClient imsUserServiceClient;

	@Autowired
	private IUMSConvertorService umsConvertorService;

	public enum UserOwner {

		UMS("ums"), IMS("ims");

		private String name;

		private UserOwner(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * This function will decide the userId belongs to which system(ims or ums domain).
	 * 
	 * @param userId
	 * @return
	 */
	public UserOwner getUserOwnerByUserId(Integer userId) {

		if (userId == null) {
			return null;
		}
		Integer imsUserIdBenchmark = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class).getImsUserIdBenchmark();
		if (imsUserIdBenchmark == null) {
			return null;
		}
		if (userId > imsUserIdBenchmark) {
			return UserOwner.IMS;
		} else {
			return UserOwner.UMS;
		}
	}

	/**
	 * This function is used only when userId belongs to IMS domain otherwise 
	 * it will become a cyclic call.
	 * 
	 * @param userId
	 * @return
	 */
	public UserSRO getUserFromIMSById(Integer userId) {

		if (userId == null) {
			return null;
		}
		UserDetailsDTO fetchedIMSUser = null;
		GetUserByIdRequest request= new GetUserByIdRequest();
		request.setUserId(Integer.toString(userId));
		GetUserResponse response=null;

		try {
			LOG.info("Call to IMS for getUserById with request: "+request);
			response = imsUserServiceClient.getUserById(request);
		} catch (ServiceException e) {
			LOG.error("ServiceException while fetching user "
					+ "from IMS with code: "+e.getErrCode()+" and message: "+e.getErrMsg());
		} catch(Exception ex){
			LOG.error("Exception while calling IMS: ",ex);
		}
		if(response!=null){
			fetchedIMSUser = response.getUserDetails();
		}
		return umsConvertorService.getUserSROFromIMSUser(fetchedIMSUser);
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserSRO getUserFromIMSByEmail(String email) {

		if (email == null) {
			return null;
		}
		UserDetailsDTO fetchedIMSUser = null;
		GetUserByEmailRequest request= new GetUserByEmailRequest();
		request.setEmailId(email);
		GetUserResponse response = null;

		try {
			LOG.info("Call to IMS for getUserByEmail with request: "+request);
			response = imsUserServiceClient.getUserByEmail(request);
		} catch (ServiceException e) {
			LOG.error("ServiceException while fetching user "
					+ "from IMS with code: "+e.getErrCode()+" and message: "+e.getErrMsg());
		} catch(Exception ex){
			LOG.error("Exception while calling IMS: ",ex);
		}
		if(response!=null){
			fetchedIMSUser = response.getUserDetails();
		}
		return umsConvertorService.getUserSROFromIMSUser(fetchedIMSUser);
	}
	
	/**
	 * This is used to check if user exists in IMS db. Used only when
	 * userId belongs to ims domain otherwise call will become cyclic.
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isUserExistsById(Integer userId) {
		
		boolean isExist = false;
		if (userId == null) {
			return isExist;
		}
		IsUserExistRequest request= new IsUserExistRequest();
		request.setUserId(Integer.toString(userId));
		IsUserExistResponse response=null;

		try {
			LOG.info("Call to IMS for isUserExistsById with request: "+request);
			response = imsUserServiceClient.isUserExist(request);
			isExist = response.isExist();
		} catch (ServiceException e) {
			LOG.error("ServiceException while IMS interaction "
					+ "with code: "+e.getErrCode()+" and message: "+e.getErrMsg());
		} catch(Exception ex){
			LOG.error("Exception while calling IMS: ",ex);
		}
		return isExist;
	}

}
