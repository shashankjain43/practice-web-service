
package com.snapdeal.ums.ext.user;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsUserActivityPresentInHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5917766595654902692L;
	@Tag(3)
    private Integer activityId;
    @Tag(4)
    private Date created;
    @Tag(5)
    private Integer userId;

    public IsUserActivityPresentInHistoryRequest() {
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public IsUserActivityPresentInHistoryRequest(Integer activityId, Date created, Integer userId) {
        this.activityId = activityId;
        this.created = created;
        this.userId = userId;
    }

}
