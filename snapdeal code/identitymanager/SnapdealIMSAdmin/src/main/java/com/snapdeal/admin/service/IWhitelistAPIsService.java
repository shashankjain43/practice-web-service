package com.snapdeal.admin.service;

import javax.servlet.http.HttpServletRequest;

import com.snapdeal.admin.request.GetWhiteListAPIsStatusRequest;
import com.snapdeal.admin.request.WhitelistApiUpdateStatusRequest;
import com.snapdeal.admin.response.ClientWhiteListAPIsResponse;
import com.snapdeal.admin.response.ClientWhiteListAPIsUpdateResponse;
import com.snapdeal.ims.exception.ValidationException;

public interface IWhitelistAPIsService {

   public ClientWhiteListAPIsResponse getWhiteListAPIsStatus(GetWhiteListAPIsStatusRequest request, HttpServletRequest servletRequest)
            throws ValidationException;

   public ClientWhiteListAPIsUpdateResponse allowAPI(WhitelistApiUpdateStatusRequest request)
            throws ValidationException;
   
   public ClientWhiteListAPIsUpdateResponse restrictAPI(WhitelistApiUpdateStatusRequest request)
            throws ValidationException;
}
