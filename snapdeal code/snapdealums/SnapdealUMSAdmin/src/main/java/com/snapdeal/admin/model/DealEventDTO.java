/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 16, 2010
 *  @author rahul
 */
package com.snapdeal.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.snapdeal.catalog.base.sro.CitySRO;

public class DealEventDTO {

    private Date                                    date;
    private CitySRO                                 city;
    private final List<DealEventCategoryContentDTO> dealEventCategoryContents = new ArrayList<DealEventCategoryContentDTO>();
    private final List<DealEventCategoryContentDTO> eligibleCategoryContents  = new ArrayList<DealEventCategoryContentDTO>();
    private String                                  dealNewsletter;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CitySRO getCity() {
        return city;
    }

    public void setCity(CitySRO city) {
        this.city = city;
    }

    public List<DealEventCategoryContentDTO> getDealEventCategoryContents() {
        return dealEventCategoryContents;
    }

    public List<DealEventCategoryContentDTO> getEligibleCategoryContents() {
        return eligibleCategoryContents;
    }

    public String getDealNewsletter() {
        return dealNewsletter;
    }

    public void setDealNewsletter(String dealNewsletter) {
        this.dealNewsletter = dealNewsletter;
    }

}
