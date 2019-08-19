
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewSubscriberInDateRangeRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7974871482376657274L;
    @Tag(3)
    private Date startDate;
    @Tag(4)
    private Date endDate;
    @Tag(5)
    private String channel;

    public GetNewSubscriberInDateRangeRequest() {
    }
    
    public GetNewSubscriberInDateRangeRequest(Date startDate, Date endDate, String channel) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.channel = channel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
