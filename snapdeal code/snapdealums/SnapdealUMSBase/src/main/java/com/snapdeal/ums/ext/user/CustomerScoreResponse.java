package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.CustomerScoreSRO;

public class CustomerScoreResponse extends ServiceResponse {

    public enum RiskLevel {
        NO("No Risk"), HIGH("High Risk"), LOW("Low Risk"), FRAUD("Fraud"), SAFE("Safe");
        private String code;

        private RiskLevel(String code) {
            this.code = code;
        }

        public String code() {
            return code;
        }
    }
    /**
     * 
     */
    private static final long serialVersionUID = -3744497355630119022L;

    @Tag(5)
    private CustomerScoreSRO  customerScoreSRO;

    public CustomerScoreResponse() {

    }

    public CustomerScoreResponse(CustomerScoreSRO customerScoreSRO) {
        super();
        this.customerScoreSRO = customerScoreSRO;
    }

    public void setCustomerScoreSRO(CustomerScoreSRO customerScoreSRO) {
        this.customerScoreSRO = customerScoreSRO;
    }

    public CustomerScoreSRO getCustomerScoreSRO() {
        return customerScoreSRO;
    }
}
