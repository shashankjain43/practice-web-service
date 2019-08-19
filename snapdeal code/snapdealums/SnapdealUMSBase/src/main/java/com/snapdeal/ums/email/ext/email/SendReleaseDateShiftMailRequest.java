
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendReleaseDateShiftMailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5754896746390590232L;
    @Tag(3)
    private Integer orderId;
    @Tag(4)
    private Integer suborderId;
    @Tag(5)
    private String newReleaseDate;
    @Tag(6)
    private String oldReleaseDate;

    public SendReleaseDateShiftMailRequest() {
    }
    
    

    public SendReleaseDateShiftMailRequest(Integer orderId, Integer suborderId, String newReleaseDate, String oldReleaseDate) {
        super();
        this.orderId = orderId;
        this.suborderId = suborderId;
        this.newReleaseDate = newReleaseDate;
        this.oldReleaseDate = oldReleaseDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

    public String getNewReleaseDate() {
        return newReleaseDate;
    }

    public void setNewReleaseDate(String newReleaseDate) {
        this.newReleaseDate = newReleaseDate;
    }

    public String getOldReleaseDate() {
        return oldReleaseDate;
    }

    public void setOldReleaseDate(String oldReleaseDate) {
        this.oldReleaseDate = oldReleaseDate;
    }

}
