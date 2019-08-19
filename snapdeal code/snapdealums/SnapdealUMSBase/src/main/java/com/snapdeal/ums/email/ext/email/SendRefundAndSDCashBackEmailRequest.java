
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendRefundAndSDCashBackEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2657816517534589653L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private Integer orderId;
    @Tag(5)
    private String shippingMethodCode;
    @Tag(6)
    private List<String>  cancelledProducts;
    @Tag(7)
    private Integer sdCashRefund;
    @Tag(8)
    private boolean newUser;
    @Tag(9)
    private String confirmationLink;
    @Tag(10)
    private String contextPath;
    @Tag(11)
    private String contentPath;

    public SendRefundAndSDCashBackEmailRequest() {
    }
    
    public SendRefundAndSDCashBackEmailRequest(UserSRO user, Integer orderId, String shippingMethodCode, List<String> cancelledProducts, Integer sdCashRefund, boolean newUser, String confirmationLink,
            String contextPath, String contentPath) {
        super();
        this.user = user;
        this.orderId = orderId;
        this.shippingMethodCode = shippingMethodCode;
        this.cancelledProducts = cancelledProducts;
        this.sdCashRefund = sdCashRefund;
        this.newUser = newUser;
        this.confirmationLink = confirmationLink;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public String getShippingMethodCode() {
        return shippingMethodCode;
    }

    public void setShippingMethodCode(String shippingMethodCode) {
        this.shippingMethodCode = shippingMethodCode;
    }

    public List<String>  getCancelledProducts() {
        return cancelledProducts;
    }

    public void setCancelledProducts(List<String>  cancelledProducts) {
        this.cancelledProducts = cancelledProducts;
    }

    public Integer getSdCashRefund() {
        return sdCashRefund;
    }

    public void setSdCashRefund(Integer sdCashRefund) {
        this.sdCashRefund = sdCashRefund;
    }

    public boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
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
