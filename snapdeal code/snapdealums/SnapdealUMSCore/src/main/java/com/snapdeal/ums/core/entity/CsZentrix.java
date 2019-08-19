package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cs_zentrix", catalog = "ums")
public class CsZentrix implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4605717568301977116L;
    private Integer           id;
    private User              user;
    private Integer           zentrixId;
    private Date              updated;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "zentrix_id", nullable = false)
    public Integer getZentrixId() {
        return zentrixId;
    }

    public void setZentrixId(Integer zentrixId) {
        this.zentrixId = zentrixId;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }
}