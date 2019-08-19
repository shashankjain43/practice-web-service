
package com.snapdeal.ums.email.ext.email;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendDailyMerchantEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4410805590192915009L;
	@Tag(3)
    private String emailIds;
    @Tag(4)
    private List<Integer> suborderIds;

    public SendDailyMerchantEmailRequest() {
    }

    public SendDailyMerchantEmailRequest(String emailIds, List<Integer> suborderIds) {
        super();
        this.emailIds = emailIds;
        this.suborderIds = suborderIds;
    }

    public String getEmailIds() {
        return emailIds;
    }

    public void setEmailIds(String emailIds) {
        this.emailIds = emailIds;
    }

    public List<Integer> getSuborderIds() {
        return suborderIds;
    }

    public void setSuborderIds(List<Integer> suborderIds) {
        this.suborderIds = suborderIds;
    }


}
