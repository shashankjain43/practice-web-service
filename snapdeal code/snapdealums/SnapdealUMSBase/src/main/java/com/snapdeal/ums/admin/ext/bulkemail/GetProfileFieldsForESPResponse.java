
package com.snapdeal.ums.admin.ext.bulkemail;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.ESPProfileFieldSRO;

public class GetProfileFieldsForESPResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1988197873021393937L;
	@Tag(5)
	private List<ESPProfileFieldSRO> getProfileFieldsForESP = new ArrayList<ESPProfileFieldSRO>();

    public GetProfileFieldsForESPResponse() {
    }

    public GetProfileFieldsForESPResponse(List<ESPProfileFieldSRO> getProfileFieldsForESP) {
        super();
        this.getProfileFieldsForESP = getProfileFieldsForESP;
    }

    public List<ESPProfileFieldSRO> getGetProfileFieldsForESP() {
        return getProfileFieldsForESP;
    }

    public void setGetProfileFieldsForESP(List<ESPProfileFieldSRO> getProfileFieldsForESP) {
        this.getProfileFieldsForESP = getProfileFieldsForESP;
    }

}
