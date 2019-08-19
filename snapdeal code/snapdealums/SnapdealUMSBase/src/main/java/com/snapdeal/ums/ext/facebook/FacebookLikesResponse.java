package com.snapdeal.ums.ext.facebook;

import java.util.HashSet;
import java.util.Set;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.services.facebook.sro.FacebookLikeSRO;

public class FacebookLikesResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -4865369295324702582L;

    @Tag(5)
    private Set<FacebookLikeSRO> fbLikeSRO = new HashSet<FacebookLikeSRO>(0);
    
    public FacebookLikesResponse() {
        super();
    }

    public Set<FacebookLikeSRO> getFbLikeSRO() {
        return fbLikeSRO;
    }

    public void setFbLikeSRO(Set<FacebookLikeSRO> fbLikeSRO) {
        this.fbLikeSRO = fbLikeSRO;
    }

}
