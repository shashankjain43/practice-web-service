/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 28-Jul-2011
 *  @author praveen
 */
package com.snapdeal.ums.services.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ipms.base.common.model.ProductVendorSRO;
import com.snapdeal.ipms.base.exception.IPMSException;
import com.snapdeal.ipms.base.request.GetProductVendorsRequest;
import com.snapdeal.ipms.base.response.GetProductVendorsResponse;
import com.snapdeal.ipms.client.service.IIPMSClientService;

@Service("umsProductVendorService")
@Transactional
public class ProductVendorServiceImpl implements IProductVendorService {

    @Autowired
    private IIPMSClientService  ipmsClientService;

    @Override
    public ProductVendorSRO getProductVendorByCode(String code) throws IPMSException, TransportException {
        GetProductVendorsResponse response = ipmsClientService.getProductVendors(new GetProductVendorsRequest(code));
        if (response.isSuccessful() && !response.getVendors().isEmpty()) {
            return response.getVendors().get(0);
        }
        return null;
    }

}
