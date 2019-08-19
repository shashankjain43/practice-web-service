/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jan 28, 2011
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
import javax.persistence.Transient;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.utils.Constants;

@Entity
@Table(name = "mobile_subscriber_detail", catalog = "ums")
public class MobileSubscriberDetail implements Serializable {

    private static final long serialVersionUID = 1656956765019229254L;
    
    private Integer           id;
    private String            mobile;
    private boolean           verified;
    private String            uid;
    private Date              updated;
    private Date              created;

    public MobileSubscriberDetail() {
        this.updated = DateUtils.getCurrentTime();
    }

    public MobileSubscriberDetail(String mobile, boolean verified) {
        this.mobile = mobile;
        this.verified = verified;
        this.updated = DateUtils.getCurrentTime();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "mobile", nullable = false, length = 10)
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Column(name = "verified", nullable = false)
    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column(name = "uid", nullable = true, length = 32)
    public String getUid() {
        return uid;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true, length = 19)
    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, length = 19)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    
    @Transient
    public String getUIDCode() {
        int uniqueNo = Constants.TRANSACTION_CODE_SEQUENCE_START_NO + id;
        int randomNo = (int) (Math.random() * 100);

        // Appending the 2nd digit in the last 
        // Appending the 1st digit in the 5th place
        long temp = (((uniqueNo - (uniqueNo % 1000)) * 10) + (uniqueNo % 1000)) + ((randomNo / 10) * 1000);
        long code = temp * 10 + (randomNo % 10);

        return String.valueOf(code);
    }

}
