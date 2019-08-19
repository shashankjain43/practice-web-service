
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetVoucherTextResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1771224897607246908L;
    @Tag(5)
    private String getVoucherText;

    public GetVoucherTextResponse() {
    }

    public GetVoucherTextResponse(String getVoucherText) {
        super();
        this.getVoucherText = getVoucherText;
    }

    public String getGetVoucherText() {
        return getVoucherText;
    }

    public void setGetVoucherText(String getVoucherText) {
        this.getVoucherText = getVoucherText;
    }

}
