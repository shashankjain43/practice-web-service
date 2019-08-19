
package com.snapdeal.ums.services.ext.smsservice;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendVoucherSmsRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7017235620761356431L;
    @Tag(3)
    private List<Integer> suborders;
    @Tag(4)
    private int catalogId;

    public SendVoucherSmsRequest2() {
    }

    public List<Integer> getSuborders() {
        return suborders;
    }

    public void setSuborders(List<Integer> suborders) {
        this.suborders = suborders;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

}
