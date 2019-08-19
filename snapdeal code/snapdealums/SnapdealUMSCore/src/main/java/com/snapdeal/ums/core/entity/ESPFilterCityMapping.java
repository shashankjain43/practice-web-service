/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 15, 2010
 *  @author rahul
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "esp_filter_city_mapping", catalog = "ums")
public class ESPFilterCityMapping implements Serializable {

    private static final long    serialVersionUID = 353828888256774573L;

    private Integer              id;
    private String               filter;
    private Integer              cityId;
    private String               mlid;
    private String               filterType;
    private String               siteId;
    private EmailServiceProvider emailServiceProvider;
   
    private Date              updated;

    public ESPFilterCityMapping() {
        this.updated = DateUtils.getCurrentTime();
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "filter", length = 200, nullable = true)
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Column(name = "city_id", unique = true, length = 10)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public void setMlid(String mlid) {
        this.mlid = mlid;
    }

    @Column(name = "mlid", length = 100)
    public String getMlid() {
        return mlid;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    @Column(name = "filter_type")
    public String getFilterType() {
        return filterType;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Column(name = "site_id", length = 15)
    public String getSiteId() {
        return siteId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, length = 19)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "esp_id", nullable = false)
    public EmailServiceProvider getEmailServiceProvider() {
        return this.emailServiceProvider;
    }

    public void setEmailServiceProvider(EmailServiceProvider emailServiceProvider) {
        this.emailServiceProvider = emailServiceProvider;
    }

}
