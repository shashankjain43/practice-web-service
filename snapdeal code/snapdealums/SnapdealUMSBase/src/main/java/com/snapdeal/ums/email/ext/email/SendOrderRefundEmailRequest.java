
package com.snapdeal.ums.email.ext.email;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderRefundEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2291124336264958580L;
	@Tag(3)
    private Integer orderId;
	@Tag(4)
	private String shippingMethodCode;
    @Tag(5)
    private  List<String> cancelledProducts;
    @Tag(6)
    private String contextPath;
    @Tag(7)
    private String contentPath;

    public SendOrderRefundEmailRequest() {
    }
    
    
    public SendOrderRefundEmailRequest(Integer orderId,String shippingMethodCode, List<String> cancelledProducts, String contextPath, String contentPath) {
        super();
        this.orderId = orderId;
        this.setShippingMethodCode(shippingMethodCode);
        this.cancelledProducts = cancelledProducts;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrder(Integer orderId) {
        this.orderId = orderId;
    }

    public  List<String> getCancelledProducts() {
        return cancelledProducts;
    }

    public void setCancelledProducts( List<String> cancelledProducts) {
        this.cancelledProducts = cancelledProducts;
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


    public void setShippingMethodCode(String shippingMethodCode) {
        this.shippingMethodCode = shippingMethodCode;
    }


    public String getShippingMethodCode() {
        return shippingMethodCode;
    }

}
