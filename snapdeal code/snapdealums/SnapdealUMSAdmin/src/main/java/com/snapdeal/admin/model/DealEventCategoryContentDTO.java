/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 04-Sep-2012
 *  @author sunny
 */
package com.snapdeal.admin.model;

import java.util.ArrayList;
import java.util.List;

import com.snapdeal.catalog.base.sro.MinServiceDealSRO;
import com.snapdeal.catalog.base.sro.ServiceDealCategorySRO;

public class DealEventCategoryContentDTO {
    private ServiceDealCategorySRO  category;
    private List<MinServiceDealSRO> deals = new ArrayList<MinServiceDealSRO>();
    private Integer                 position;

    public ServiceDealCategorySRO getCategory() {
        return category;
    }

    public void setCategory(ServiceDealCategorySRO category) {
        this.category = category;
    }

    public List<MinServiceDealSRO> getDeals() {
        return deals;
    }

    public void setDeals(List<MinServiceDealSRO> deals) {
        this.deals = deals;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }
}
