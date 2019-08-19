/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.cps.listener.utils;

import java.util.HashMap;

import com.snapdeal.catalog.base.enums.CatalogStatusEnum;
import com.snapdeal.catalog.base.enums.ProductOfferState;

/**
 *  @version   1.0, Sep 16, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class ProductUtils {

private static HashMap<Object, Integer> productStatusPriorities;
    
    static{
        productStatusPriorities = new HashMap<Object, Integer>();
        productStatusPriorities.put(ProductOfferState.COMING_SOON, 2);
        productStatusPriorities.put(ProductOfferState.PREBOOK, 3);
        productStatusPriorities.put(ProductOfferState.BUY_NOW, 4);
        productStatusPriorities.put(ProductOfferState.DISCONTINUED, 1);
        productStatusPriorities.put(ProductOfferState.TERMINATED, 0);
        productStatusPriorities.put(CatalogStatusEnum.ACTIVE, 20);
        productStatusPriorities.put(CatalogStatusEnum.INACTIVE, 10);
        productStatusPriorities.put(CatalogStatusEnum.DELETED, 0);
    }

    public static int getProductStatusPriority(Object productStatus){
        return productStatusPriorities.get(productStatus) != null ? productStatusPriorities.get(productStatus) : -1;
    }
}