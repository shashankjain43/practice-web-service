
package com.snapdeal.ums.admin.ext.bulkemail;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetResultsMauResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5827796324768660681L;
	@Tag(5)
    private  List<Object[]>  getResultsMau = new ArrayList<Object[]>();

    public GetResultsMauResponse() {
    }

    public GetResultsMauResponse( List<Object[]>  getResultsMau) {
        super();
        this.getResultsMau = getResultsMau;
    }

    public  List<Object[]>  getGetResultsMau() {
        return getResultsMau;
    }

    public void setGetResultsMau( List<Object[]>  getResultsMau) {
        this.getResultsMau = getResultsMau;
    }

}
