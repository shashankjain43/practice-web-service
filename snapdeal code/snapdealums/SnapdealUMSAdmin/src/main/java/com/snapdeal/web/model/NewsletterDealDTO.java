/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 1, 2010
 *  @author Vikash
 */
package com.snapdeal.web.model;

public class NewsletterDealDTO {
    public NewsletterDealDTO() {
    }

    private Integer dealId;
    private boolean selected;
    private Integer dealPriority;

    public Integer getDealId() {
        return dealId;
    }

    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getDealPriority() {
        return dealPriority;
    }

    public void setDealPriority(Integer dealPriority) {
        this.dealPriority = dealPriority;
    }

}
