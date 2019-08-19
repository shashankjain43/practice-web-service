package com.snapdeal.ums.core.entity;

// Generated Apr 15, 2011 1:37:32 PM by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.base.utils.DateUtils;

/**
 * EmailServiceProvider generated by hbm2java
 */
@Entity
@Table(name = "email_service_provider", catalog = "ums")
public class EmailServiceProvider implements java.io.Serializable {

    public enum ESP {
        LYRIS(1, "Lyris"), EPSILON(2, "Epsilon"), OCTANE(3, "Octane");

        private Integer id;
        private String  name;

        private ESP(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 478024559835705260L;

    private Integer           id;
    private String            name;
    private String            siteId;
    private String            implClass;
    private Date              updated;

    public EmailServiceProvider() {
        this.updated = DateUtils.getCurrentTime();
    }

    public EmailServiceProvider(Integer id) {
        this.id = id;
        this.updated = DateUtils.getCurrentTime();
    }
    public EmailServiceProvider(String name) {
        this.name = name;
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

    @Column(name = "name", nullable = false, length = 48)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "site_id", nullable = false, length = 48)
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Column(name = "impl_class", nullable = false, length = 200)
    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
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
