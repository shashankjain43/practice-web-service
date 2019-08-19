
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class GetNewsletterByMsgIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6682320976648100786L;
	@Tag(5)
    private NewsletterSRO getNewsletterByMsgId;

    public GetNewsletterByMsgIdResponse() {
    }

    public GetNewsletterByMsgIdResponse(NewsletterSRO getNewsletterByMsgId) {
        super();
        this.getNewsletterByMsgId = getNewsletterByMsgId;
    }

    public NewsletterSRO getGetNewsletterByMsgId() {
        return getNewsletterByMsgId;
    }

    public void setGetNewsletterByMsgId(NewsletterSRO getNewsletterByMsgId) {
        this.getNewsletterByMsgId = getNewsletterByMsgId;
    }

}
