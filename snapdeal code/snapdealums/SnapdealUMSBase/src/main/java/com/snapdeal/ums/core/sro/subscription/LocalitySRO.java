package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class LocalitySRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4505617661589296772L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            name;
    @Tag(3)
    private Integer           cityId;
    @Tag(4)
    private boolean           enabled;
    @Tag(5)
    private Double            latitude;
    @Tag(6)
    private Double            longitude;

    
    public LocalitySRO(){
        
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
