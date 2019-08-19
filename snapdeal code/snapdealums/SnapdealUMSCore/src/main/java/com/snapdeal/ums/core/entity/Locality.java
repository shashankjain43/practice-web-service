/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jul 7, 2011
 *  @author rahul
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locality", catalog = "ums")
public class Locality implements Serializable {

    private static final long serialVersionUID = 7786688995239091701L;

    private Integer           id;
    private String            name;
    private Integer           cityId;
    private boolean           enabled;
    private Double            latitude;
    private Double            longitude;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, length = 250)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "city_id", nullable = false)
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "latitude", precision = 22, scale = 0)
    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Column(name = "longitude", precision = 22, scale = 0)
    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
