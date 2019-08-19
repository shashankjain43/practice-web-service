package com.snapdeal.ums.ext.facebook;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.services.facebook.sro.FacebookUserSRO;

public class FacebookUserResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 6571914310990385492L;

    @Tag(5)
    private boolean           addIfNotExistsFacebookUser;

    @Tag(6)
    private FacebookUserSRO   fbUserSRO;

    @Tag(7)
    private boolean           isFacebookUserExists;

    public FacebookUserResponse() {
        super();
    }

    public boolean isAddIfNotExistsFacebookUser() {
        return addIfNotExistsFacebookUser;
    }

    public void setAddIfNotExistsFacebookUser(boolean addIfNotExistsFacebookUser) {
        this.addIfNotExistsFacebookUser = addIfNotExistsFacebookUser;
    }

    public FacebookUserSRO getFbUserSRO() {
        return fbUserSRO;
    }

    public void setFbUserSRO(FacebookUserSRO fbUserSRO) {
        this.fbUserSRO = fbUserSRO;
    }

    public boolean isFacebookUserExists() {
        return isFacebookUserExists;
    }

    public void setFacebookUserExists(boolean isFacebookUserExists) {
        this.isFacebookUserExists = isFacebookUserExists;
    }

}
