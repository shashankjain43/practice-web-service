package com.snapdeal.opspanel.promotion.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.User;

public class RMSUtils {

	public final String[] APPLIST={"OPS_ACTIONPANEL","OPS_INFOPANEL","OPS_ACCESSMANAGEMENT"};

	public static GenericResponse getPermissionsAsApp(User user) {
		HashMap<String,List<Permission>> map = new HashMap<String, List<Permission>>();
		for(Permission permission:user.getPermissions()){
			if(map.get(permission.getAppName())==null) {
				map.put(permission.getAppName(), new ArrayList<Permission>());
			}
			map.get(permission.getAppName()).add(permission);
		}
		GenericResponse genericResponse =OPSUtils.getGenericResponse(map);
		return genericResponse;
	}
}
