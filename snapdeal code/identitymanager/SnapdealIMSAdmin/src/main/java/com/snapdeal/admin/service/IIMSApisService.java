package com.snapdeal.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.snapdeal.admin.request.CreateIMSApiRequest;
import com.snapdeal.admin.request.UpdateIMSApiRequest;
import com.snapdeal.admin.response.GetAllIMSApisResponse;
import com.snapdeal.admin.response.createIMSApiResponse;
import com.snapdeal.admin.response.updateIMSApiResponse;
import com.snapdeal.ims.exception.ValidationException;

/**
 * @author himanshu
 *
 */
public interface IIMSApisService {

   public GetAllIMSApisResponse getAllApis(HttpServletRequest request) throws ValidationException;

   public createIMSApiResponse createIMSApi(CreateIMSApiRequest request) throws ValidationException;

   public updateIMSApiResponse updateIMSApi(UpdateIMSApiRequest request) throws ValidationException;

}
