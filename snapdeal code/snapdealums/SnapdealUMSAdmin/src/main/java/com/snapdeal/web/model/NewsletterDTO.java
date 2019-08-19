/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 1, 2010
 *  @author Vikash
 */
package com.snapdeal.web.model;

import java.util.ArrayList;
import java.util.List;

public class NewsletterDTO {
    private Integer                 zoneId;
    private Integer                 zonePriority;
    private List<NewsletterDealDTO> dealDTO = new ArrayList<NewsletterDealDTO>();

    public NewsletterDTO() {
    }

    public Integer getZonePriority() {
        return zonePriority;
    }

    public void setZonePriority(Integer zonePriority) {
        this.zonePriority = zonePriority;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public List<NewsletterDealDTO> getDealDTO() {
        return dealDTO;
    }

    public void setDealDTO(List<NewsletterDealDTO> dealDTO) {
        this.dealDTO = dealDTO;
    }

}
