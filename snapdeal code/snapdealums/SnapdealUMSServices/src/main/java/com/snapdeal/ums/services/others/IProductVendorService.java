/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 28-Jul-2011
 *  @author praveen
 */
package com.snapdeal.ums.services.others;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ipms.base.common.model.ProductVendorSRO;
import com.snapdeal.ipms.base.exception.IPMSException;

public interface IProductVendorService {

    ProductVendorSRO getProductVendorByCode(String code) throws IPMSException, TransportException;

   /* List<ProductVendorSRO> getAllProductVendors();*/
}
