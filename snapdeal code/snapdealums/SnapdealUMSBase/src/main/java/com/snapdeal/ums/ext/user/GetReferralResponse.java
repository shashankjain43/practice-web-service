
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserReferralSRO;

public class GetReferralResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3690738199808297056L;
	@Tag(5)
    private List<UserReferralSRO> referrals = new ArrayList<UserReferralSRO>();

    public GetReferralResponse() {
    }

    public GetReferralResponse(List<UserReferralSRO> referrals) {
        super();
        this.referrals = referrals;
    }

    public List<UserReferralSRO> getGetReferral() {
        return referrals;
    }

    public void setReferral(List<UserReferralSRO> referrals) {
        this.referrals = referrals;
    }

}
