package com.snapdeal.ums.core.entity;

// Generated Oct 7, 2010 11:04:29 AM by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * EmailTemplate generated by hbm2java
 */
@Entity
@Table(name = "email_template", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class EmailTemplate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7389558214873718792L;
    private Integer           id;
    @NotEmpty
    private String            name;
    @NotEmpty
    private String            subjectTemplate;
    @NotEmpty
    private String            bodyTemplate;
    private String            from;
    private String            to;
    private String            cc;
    private String            bcc;
    private String            replyTo;
    private Date              created;
    private Integer           emailChannelId;
    private String            emailType;
    private boolean           enabled;
    //private String            requestParameterClass;
   
    private String            triggerId;


	@Column(name= "trigger_id",  nullable =false, length=45, columnDefinition = "varchar(45)")
    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
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

    @Column(name = "subject_template", nullable = false, length = 256)
    public String getSubjectTemplate() {
        return this.subjectTemplate;
    }

    public void setSubjectTemplate(String subjectTemplate) {
        this.subjectTemplate = subjectTemplate;
    }

    @Column(name = "body_template", nullable = false, length = 65535)
    public String getBodyTemplate() {
        return this.bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    @Column(name = "`from`", nullable = false, length = 100)
    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Column(name = "`to`", nullable = true, length = 500)
    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Column(name = "cc", length = 500)
    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Column(name = "bcc", length = 500)
    public String getBcc() {
        return this.bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    @Column(name = "reply_to", nullable = false, length = 100)
    public String getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, length = 19)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setEmailChannelId(Integer emailChannelId) {
        this.emailChannelId = emailChannelId;
    }

    @Column(name = "email_channel_id", nullable = false, length = 19)
    public Integer getEmailChannelId() {
        return emailChannelId;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    @Column(name = "email_type", length = 3)
    public String getEmailType() {
        return emailType;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }
    
//    
//    @Column(name = "request_parameter_class", length = 45)
//	public String getRequestParameterClass() {
//		return requestParameterClass;
//	}
//
//	public void setRequestParameterClass(String requestParameterClass) {
//		this.requestParameterClass = requestParameterClass;
//	}
    

}
