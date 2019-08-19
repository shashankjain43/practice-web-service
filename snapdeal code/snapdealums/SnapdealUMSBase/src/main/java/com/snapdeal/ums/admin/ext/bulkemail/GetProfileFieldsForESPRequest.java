
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetProfileFieldsForESPRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2647996775238240516L;
	@Tag(3)
    private int espId;

    public GetProfileFieldsForESPRequest() {
    }

    public int getEspId() {
        return espId;
    }

    public void setEspId(int espId) {
        this.espId = espId;
    }

    public GetProfileFieldsForESPRequest(int espId) {
        this.espId = espId;
    }

}
