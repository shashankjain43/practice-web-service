package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class UpdateResponse extends ServiceResponse {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4305655927102584669L;

    @Tag(5)
    private NewsletterSRO     newsletter;

    public UpdateResponse() {
    }

    public NewsletterSRO getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(NewsletterSRO newsletter) {
        this.newsletter = newsletter;
    }

}
