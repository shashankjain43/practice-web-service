package com.snapdeal.opspanel.promotion.Response;

import java.util.ArrayList;

import lombok.Data;

import com.snapdeal.payments.roleManagementModel.request.Permission;

@Data
public class GetAllPermissionForAppResponse {

	public ArrayList<Permission> permissions= new ArrayList<Permission>();
}
