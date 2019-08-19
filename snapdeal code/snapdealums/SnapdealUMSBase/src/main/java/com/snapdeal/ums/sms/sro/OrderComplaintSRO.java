
package com.snapdeal.ums.sms.sro;

import java.io.Serializable;
import java.util.Date;
import com.dyuproject.protostuff.Tag;

public class OrderComplaintSRO
    implements Serializable
{
    
 
    /**
     * 
     */
    private static final long serialVersionUID = 7670827097727430418L;
    @Tag(1)
    private Integer id;
    @Tag(2)
    private Integer catalogId;
    @Tag(3)
    private Integer complaintTypeId;
    @Tag(4)
    private String customerName;
    @Tag(5)
    private String email;
    @Tag(6)
    private String mobile;
    @Tag(7)
    private String orderCode;
    @Tag(8)
    private String subOrderCode;
    @Tag(9)
    private String comments;
    @Tag(10)
    private Integer reportedByUserId;
    @Tag(11)
    private Integer zoneId;
    @Tag(12)
    private String nextAction;
    @Tag(13)
    private String ticketId;
    @Tag(14)
    private char weightCount;
    @Tag(15)
    private String queryUrl;
    @Tag(16)
    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getComplaintTypeId() {
        return complaintTypeId;
    }

    public void setComplaintTypeId(Integer complaintTypeId) {
        this.complaintTypeId = complaintTypeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSubOrderCode() {
        return subOrderCode;
    }

    public void setSubOrderCode(String subOrderCode) {
        this.subOrderCode = subOrderCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getReportedByUserId() {
        return reportedByUserId;
    }

    public void setReportedByUserId(Integer reportedByUserId) {
        this.reportedByUserId = reportedByUserId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public char getWeightCount() {
        return weightCount;
    }

    public void setWeightCount(char weightCount) {
        this.weightCount = weightCount;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
