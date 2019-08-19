package com.snapdeal.ums.core.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "server_info", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = {"name" , "address"}))
public class ServerInfo implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1763173979376171947L;
    private Integer           id;
    private String            name;
    private String            address;
    private boolean           reloadRequired;
    private String            reloadRequiredFor;
    private Date              created;
    private Date              updated;
    private Date              lastReloaded;
    private String            lastReloadCache;
    private Date              started;

    public ServerInfo() {
    }

    public ServerInfo(String name, String address, boolean reloadRequired, Date created, Date updated, Date lastReloaded, String lastReloadCache, Date started) {
        this.name = name;
        this.address = address;
        this.reloadRequired = reloadRequired;
        this.created = created;
        this.updated = updated;
        this.lastReloaded = lastReloaded;
        this.lastReloadCache = lastReloadCache;
        this.started = started;
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

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address", nullable = false, length = 20)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "reloadRequired", nullable = false)
    public boolean isReloadRequired() {
        return this.reloadRequired;
    }

    public void setReloadRequired(boolean reloadRequired) {
        this.reloadRequired = reloadRequired;
    }

    @Column(name = "reloadRequiredFor", nullable = false, length = 200)
    public String getReloadRequiredFor() {
        return reloadRequiredFor;
    }

    public void setReloadRequiredFor(String reloadRequiredFor) {
        this.reloadRequiredFor = reloadRequiredFor;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, length = 19)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastReloaded")
    public Date getLastReloaded() {
        return this.lastReloaded;
    }

    public void setLastReloaded(Date lastReloaded) {
        this.lastReloaded = lastReloaded;
    }

    @Column(name = "lastReloadCache", nullable = false, length = 200)
    public String getLastReloadCache() {
        return lastReloadCache;
    }

    public void setLastReloadCache(String lastReloadCache) {
        this.lastReloadCache = lastReloadCache;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started")
    public Date getStarted() {
        return this.started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }


}
