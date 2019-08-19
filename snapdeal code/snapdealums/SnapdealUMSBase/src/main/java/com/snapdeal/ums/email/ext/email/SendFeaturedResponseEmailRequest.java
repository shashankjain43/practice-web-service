
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.FeaturedSRO;

public class SendFeaturedResponseEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5213691606718258566L;
	@Tag(3)
    private FeaturedSRO featured;
    @Tag(4)
    private String email;
    @Tag(5)
    private String contentPath;

    public SendFeaturedResponseEmailRequest() {
    }

    public SendFeaturedResponseEmailRequest(FeaturedSRO featured, String email, String contentPath) {
        super();
        this.featured = featured;
        this.email = email;
        this.contentPath = contentPath;
    }

    public FeaturedSRO getFeatured() {
        return featured;
    }

    public void setFeatured(FeaturedSRO featured) {
        this.featured = featured;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}
