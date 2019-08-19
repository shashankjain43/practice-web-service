package com.snapdeal.ims.utils;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.enums.Platform;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserWithMobileOnlyRequest;

public class UserPlatformResourceUtil {

	public static CreateUserWithMobileOnlyRequest getUserPlatformResource(CreateUserWithMobileOnlyRequest request){
		if(request.getPlatform()==null){
			request.setPlatform(Platform.OTHERS);
		}
		if(StringUtils.isBlank(request.getResource())){
			request.setResource("OTHERS");
		}
		return request;
	}

	public static CreateUserMobileGenerateRequest getUserPlatformResource(
			CreateUserMobileGenerateRequest request) {
		if (request.getPlatform() == null) {
			request.setPlatform(Platform.OTHERS);
		}
		if (StringUtils.isBlank(request.getResource())) {
			request.setResource("OTHERS");
		}
		return request;
	}

	public static CreateSocialUserRequest getUserPlatformResource(
			CreateSocialUserRequest request) {
		if (request.getPlatform() == null) {
			request.setPlatform(Platform.OTHERS);
		}
		if (StringUtils.isBlank(request.getResource())) {
			request.setResource("OTHERS");
		}
		return request;
	}

	public static CreateSocialUserWithMobileRequest getUserPlatformResource(
			CreateSocialUserWithMobileRequest request) {
		if (request.getPlatform() == null) {
			request.setPlatform(Platform.OTHERS);
		}
		if (StringUtils.isBlank(request.getResource())) {
			request.setResource("OTHERS");
		}
		return request;
	}

}
