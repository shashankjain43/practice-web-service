/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 03-Apr-2013
 *  @author aniket
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.snapdeal.ums.validator.CronExpression;

@Entity
@Table(name = "ums_task", catalog = "ums")
public class UmsTask implements Serializable {

    private static final long   serialVersionUID     = 5050395096668166535L;

    private Integer             id;
    @NotEmpty
    private String              name;
    @NotEmpty
    private String              implClass;
    @NotEmpty
    @CronExpression
    private String              cronExpression;

    private boolean             clustered;
    private boolean             concurrent;

    private Date                created;

    private Date                updated;

    private boolean             enabled;
    @NotEmpty
    private String              emailTemplate;
    @NotEmpty
    private String              notificationEmail;

    private Date                lastExecTime;

    private String              lastExecResult;

    private Set<TaskParameter>  taskParameters       = new HashSet<TaskParameter>();

    private List<TaskParameter> umsTaskParameterList = new ArrayList<TaskParameter>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "umsTask", cascade = CascadeType.MERGE)
    public Set<TaskParameter> getTaskParameters() {
        return taskParameters;
    }

    public void setTaskParameters(Set<TaskParameter> taskParameters) {
        this.taskParameters = taskParameters;
    }

    public UmsTask() {
        super();
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

    @Column(name = "name", unique = true, nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "impl_class", unique = true, nullable = false, length = 128)
    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }

    @Column(name = "cron_expression", nullable = false, length = 128)
    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    @Column(name = "clustered", nullable = false)
    public boolean isClustered() {
        return clustered;
    }

    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }

    @Column(name = "concurrent", nullable = false)
    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, length = 19)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, length = 19, updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "email_template", nullable = true, length = 200)
    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Column(name = "notification_email", nullable = true, length = 200)
    public String getNotificationEmail() {
        return notificationEmail;
    }

    public void setNotificationEmail(String notificationEmail) {
        this.notificationEmail = notificationEmail;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_exec_time", length = 19)
    public Date getLastExecTime() {
        return this.lastExecTime;
    }

    public void setLastExecTime(Date lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    @Column(name = "last_exec_result", length = 65535)
    public String getLastExecResult() {
        return this.lastExecResult;
    }

    public void setLastExecResult(String lastExecResult) {
        this.lastExecResult = lastExecResult;
    }

    @Override
    public String toString() {
        return "Task1 [id=" + id + ", name=" + name + ", implClass=" + implClass + ", cronExpression=" + cronExpression + ", clustered=" + clustered + ", concurrent=" + concurrent
                + ", created=" + created + ", updated=" + updated + ", enabled=" + enabled + ", emailTemplate=" + emailTemplate + ", notificationEmail=" + notificationEmail
                + ", taskParameters=" + taskParameters + "]";
    }

    @Transient
    public List<TaskParameter> getumsTaskParameterList() {
        if (umsTaskParameterList.isEmpty()) {
            this.umsTaskParameterList.addAll(taskParameters);
        }
        return umsTaskParameterList;
    }

    public Integer getUmsTaskParameterValue(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (TaskParameter taskParameter : getumsTaskParameterList()) {
            if (name.equals(taskParameter.getName())) {
                return Integer.parseInt(taskParameter.getValue());
            }
        }
        return null;
    }
}
