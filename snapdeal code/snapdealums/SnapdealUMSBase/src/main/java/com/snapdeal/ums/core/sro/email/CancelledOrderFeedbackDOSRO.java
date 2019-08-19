 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 01-Nov-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.ums.email.ext.email.SendAutoCaptureStatusEmailRequest.MapEntryUtil;

public class CancelledOrderFeedbackDOSRO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 6437264120822447298L;
    @Tag(1)
    private String            orderCode;
    @Tag(2)
    private Integer              orderId;
    @Tag(3)
    private List<MapEntryUtil> cancelledSuborders = new ArrayList<CancelledOrderFeedbackDOSRO.MapEntryUtil>();
    @Tag(4)
    private String            email;
    @Tag(5)
    private String            userName;
    

    public CancelledOrderFeedbackDOSRO(String orderCode, Integer orderId, String email, String userName) {
        super();
        this.orderCode = orderCode;
        this.orderId = orderId;
        this.email = email;
        this.userName = userName;
    }

    public CancelledOrderFeedbackDOSRO(CancelledOrderFeedbackDO cancelledOrderFeedbackDTO) {
       this.orderCode = cancelledOrderFeedbackDTO.getOrderCode();
       this.orderId = cancelledOrderFeedbackDTO.getOrderId();
       this.setCancelledSuborders(cancelledOrderFeedbackDTO.getCancelledSuborders());
       this.email=cancelledOrderFeedbackDTO.getEmail();
       this.userName = cancelledOrderFeedbackDTO.getUserName();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<MapEntryUtil> getCancelledSuborders() {
        return cancelledSuborders;
    }

    public void setCancelledSuborders(Map<Integer, String> cancelledSuborders) {
        if (cancelledSuborders == null)
            return;
        for (Entry<Integer, String> entry : cancelledSuborders.entrySet())
            this.cancelledSuborders.add(new MapEntryUtil(entry.getKey(), entry.getValue()));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public class MapEntryUtil implements Serializable {
        /**
     * 
     */
        private static final long serialVersionUID = 2135114339986122029L;
        @Tag(1)
        Integer                    Key;
        @Tag(2)
        String                    value;

        public MapEntryUtil(Integer key, String value) {
            super();
            this.Key = key;
            this.value = value;
        }

        public Integer getKey() {
            return Key;
        }

        public void setKey(Integer key) {
            Key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

