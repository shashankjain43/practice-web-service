package com.snapdeal.ums.core.entity;

// Generated Oct 13, 2010 7:43:52 PM by Hibernate Tools 3.3.0.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.snapdeal.base.expression.Expression;

/**
 * ActivityType generated by hbm2java
 */
@Entity
@Table(name = "sd_wallet_activity_type", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class SDWalletActivityType implements java.io.Serializable {

    private static final long serialVersionUID = 4036973805101596280L;

    private Integer           id;
    private String            name;
    private String            code;
    private String            sdCash;
    private boolean           async;
    private boolean           enabled;
    private Integer           expiryDays;
    private Date              created;
    private Date              updated;

    private Expression        expression;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", unique = true, nullable = false, length = 10)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "sd_cash", nullable = false, length = 100)
    public String getSdCash() {
        return this.sdCash;
    }

    public void setSdCash(String sdCash) {
        this.sdCash = sdCash;
        this.expression = Expression.compile(sdCash);
    }

    @Column(name = "async", nullable = false)
    public boolean getAsync() {
        return this.async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Column(name = "enabled", nullable = false)
    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Transient
    public Expression getExpression() {
        return this.expression;
    }

    @Column(name = "expiry_days")
    public Integer getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
