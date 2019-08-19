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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "esp_profile_field", catalog = "ums")
public class ESPProfileField implements Serializable {

    private static final long    serialVersionUID = 353828888256774573L;

    private Integer              id;
    private String               fieldName;
    private Integer              espId;
    private String               espFieldName;
    private Date              updated;

    public ESPProfileField() {
        this.updated = DateUtils.getCurrentTime();
    }

    public ESPProfileField(String mlid, String filter, Integer cityId, 
        String filterType, String siteId, int espId) 
    {
        this.fieldName = filter;
        this.espId = cityId;
        this.espFieldName = mlid;
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

    @Column(name = "field_name", length = 32, nullable = true)
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Column(name = "esp_id", length = 10)
    public Integer getEspId() {
        return espId;
    }

    public void setespId(Integer espId) {
        this.espId = espId;
    }

    public void setEspFieldName(String espFieldName) {
        this.espFieldName = espFieldName;
    }

    @Column(name = "esp_field_name", length = 32)
    public String getEspFieldName() {
        return espFieldName;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, length = 19)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
