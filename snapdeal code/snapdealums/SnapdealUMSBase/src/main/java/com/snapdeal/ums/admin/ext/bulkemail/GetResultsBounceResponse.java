
package com.snapdeal.ums.admin.ext.bulkemail;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetResultsBounceResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5692045864893097264L;
	@Tag(5)
    private  List<Object> getResultsBounce = new ArrayList<Object>();

    public GetResultsBounceResponse() {
    }

    public GetResultsBounceResponse( List<Object> getResultsBounce) {
        super();
        this.getResultsBounce = getResultsBounce;
    }

    public  List<Object> getGetResultsBounce() {
        return getResultsBounce;
    }

    public void setGetResultsBounce( List<Object> getResultsBounce) {
        this.getResultsBounce = getResultsBounce;
    }

}
