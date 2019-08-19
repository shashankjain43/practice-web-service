package com.snapdeal.ums.admin.server.services.ext.csadmin;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.CsZentrixSRO;

public class CsZentrixUserResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -2642956929510189240L;
    @Tag(5)
    private CsZentrixSRO      getCsZentrixIdByUser;

    public CsZentrixUserResponse() {
    }

    public CsZentrixUserResponse(CsZentrixSRO getCsZentrixIdByUser) {
        super();
        this.getCsZentrixIdByUser = getCsZentrixIdByUser;
    }

    public CsZentrixSRO getGetCsZentrixIdByUser() {
        return getCsZentrixIdByUser;
    }

    public void setGetCsZentrixIdByUser(CsZentrixSRO getCsZentrixIdByUser) {
        this.getCsZentrixIdByUser = getCsZentrixIdByUser;
    }

}
