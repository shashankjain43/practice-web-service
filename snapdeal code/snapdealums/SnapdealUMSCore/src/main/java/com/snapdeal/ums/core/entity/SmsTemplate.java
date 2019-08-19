package com.snapdeal.ums.core.entity;

// Generated Oct 7, 2010 5:29:17 PM by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SmsTemplate generated by hbm2java
 */
@Entity
@Table(name = "sms_template", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class SmsTemplate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7103811102364526040L;
    private Integer           id;
    private String            name;
    private String            bodyTemplate;
    private Integer           smsChannelId;
    private String            smsType;
    private boolean           dndScrubbingOn;
    private boolean           enabled;

    public SmsTemplate() {
    }

    public SmsTemplate(String name, String bodyTemplate) {
        this.name = name;
        this.bodyTemplate = bodyTemplate;
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

    @Column(name = "name", unique = true, nullable = false, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "body_template", nullable = false)
    public String getBodyTemplate() {
        return this.bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    @Column(name = "sms_channel_id", nullable = false)
    public Integer getSmsChannelId() {
        return this.smsChannelId;
    }

    public void setSmsChannelId(Integer smsChannel) {
        this.smsChannelId = smsChannel;
    }

    @Column(name = "dnd_scrubbing_on")
    public boolean isDndScrubbingOn() {
        return dndScrubbingOn;
    }

    public void setDndScrubbingOn(boolean dndScrubbingOn) {
        this.dndScrubbingOn = dndScrubbingOn;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    @Column(name = "sms_type")
    public String getSmsType() {
        return smsType;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

}
