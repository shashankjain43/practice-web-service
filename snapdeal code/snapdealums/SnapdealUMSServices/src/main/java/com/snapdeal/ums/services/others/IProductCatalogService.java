/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Jul-2011
 *  @author amit
 */
package com.snapdeal.ums.services.others;

import com.snapdeal.catalog.base.model.GetPopularProductOfferGroupListByCategoryPageURLRequest;
import com.snapdeal.catalog.base.model.GetProductOfferByIdRequest;
import com.snapdeal.catalog.base.model.GetProductOfferGroupListResponse;
import com.snapdeal.catalog.base.model.GetProductOfferResponse;


public interface IProductCatalogService {
    
    public GetProductOfferGroupListResponse getPopularProductOfferGroupsByCategoryPageUrl(GetPopularProductOfferGroupListByCategoryPageURLRequest request);
    public GetProductOfferResponse getProductOfferById(GetProductOfferByIdRequest request);


}
