
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class SendUserSDCashHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7649639136162587204L;
	@Tag(3)
    private String userEmail;
    @Tag(4)
    private String userName;
    @Tag(5)
    private int sdcashAtBegOfMonth;
    @Tag(6)
    private int sdcashEarningOfMonth;
    @Tag(7)
    private int sdcashUsedThisMonth;
    @Tag(8)
    private int sdCashExpiredThisMonth;
    @Tag(9)
    private int sdcashAvailable;
    @Tag(10)
    private com.snapdeal.base.utils.DateUtils.DateRange range;
    @Tag(11)
    private int currSDCash;
    @Tag(12)
    private String linkToBeSent;
    @Tag(13)
    private String contextPath;
    @Tag(14)
    private String contentPath;

    public SendUserSDCashHistoryRequest() {
    }

    public SendUserSDCashHistoryRequest(String userEmail, String userName, int sdcashAtBegOfMonth, int sdcashEarningOfMonth, int sdcashUsedThisMonth, int sdcashAvailable,
            int sdCashExpiredThisMonth,
            DateRange range, int currSDCash, String linkToBeSent, String contextPath, String contentPath) {
        super();
        this.userEmail = userEmail;
        this.userName = userName;
        this.sdcashAtBegOfMonth = sdcashAtBegOfMonth;
        this.sdcashEarningOfMonth = sdcashEarningOfMonth;
        this.sdcashUsedThisMonth = sdcashUsedThisMonth;
        this.sdcashAvailable = sdcashAvailable;
        this.sdCashExpiredThisMonth = sdCashExpiredThisMonth;
        this.range = range;
        this.currSDCash = currSDCash;
        this.linkToBeSent = linkToBeSent;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public int getSdCashExpiredThisMonth() {
        return sdCashExpiredThisMonth;
    }

    public void setSdCashExpiredThisMonth(int sdCashExpiredThisMonth) {
        this.sdCashExpiredThisMonth = sdCashExpiredThisMonth;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSdcashAtBegOfMonth() {
        return sdcashAtBegOfMonth;
    }

    public void setSdcashAtBegOfMonth(int sdcashAtBegOfMonth) {
        this.sdcashAtBegOfMonth = sdcashAtBegOfMonth;
    }

    public int getSdcashEarningOfMonth() {
        return sdcashEarningOfMonth;
    }

    public void setSdcashEarningOfMonth(int sdcashEarningOfMonth) {
        this.sdcashEarningOfMonth = sdcashEarningOfMonth;
    }

    public int getSdcashUsedThisMonth() {
        return sdcashUsedThisMonth;
    }

    public void setSdcashUsedThisMonth(int sdcashUsedThisMonth) {
        this.sdcashUsedThisMonth = sdcashUsedThisMonth;
    }

    public int getSdcashAvailable() {
        return sdcashAvailable;
    }

    public void setSdcashAvailable(int sdcashAvailable) {
        this.sdcashAvailable = sdcashAvailable;
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getRange() {
        return range;
    }

    public void setRange(com.snapdeal.base.utils.DateUtils.DateRange range) {
        this.range = range;
    }

    public int getCurrSDCash() {
        return currSDCash;
    }

    public void setCurrSDCash(int currSDCash) {
        this.currSDCash = currSDCash;
    }

    public String getLinkToBeSent() {
        return linkToBeSent;
    }

    public void setLinkToBeSent(String linkToBeSent) {
        this.linkToBeSent = linkToBeSent;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}
