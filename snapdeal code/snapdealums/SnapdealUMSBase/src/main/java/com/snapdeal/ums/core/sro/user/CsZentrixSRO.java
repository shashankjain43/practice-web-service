package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.Date;
import com.dyuproject.protostuff.Tag;

public class CsZentrixSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7763599072485966206L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private UserSRO           userSRO;
    @Tag(3)
    private Integer           zentrixId;
    @Tag(4)
    private Date              updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserSRO getUserSRO() {
        return userSRO;
    }

    public void setUserSRO(UserSRO userSRO) {
        this.userSRO = userSRO;
    }

    public Integer getZentrixId() {
        return zentrixId;
    }

    public void setZentrixId(Integer zentrixId) {
        this.zentrixId = zentrixId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
