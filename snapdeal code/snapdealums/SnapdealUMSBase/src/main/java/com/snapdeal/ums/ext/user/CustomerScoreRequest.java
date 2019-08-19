package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.CustomerScoreSRO;

public class CustomerScoreRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -1382073132564728116L;
    @Tag(3)
    private String            searchString;

    @Tag(4)
    private CustomerScoreSRO  customerScoreSRO;

    public CustomerScoreRequest() {

    }

    public CustomerScoreRequest(String searchString) {
        super();
        this.searchString = searchString;
    }

    public CustomerScoreRequest(CustomerScoreSRO customerScoreSRO) {
        super();
        this.customerScoreSRO = customerScoreSRO;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public CustomerScoreSRO getCustomerScoreSRO() {
        return customerScoreSRO;
    }

    public void setCustomerScoreSRO(CustomerScoreSRO customerScoreSRO) {
        this.customerScoreSRO = customerScoreSRO;
    }

}
