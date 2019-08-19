
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class UpdateSubscriberProfileRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 6625152587857918151L;
    @Tag(3)
    private SubscriberProfileSRO profile;
    @Tag(4)
    private String name;
    @Tag(5)
    private String displayName;
    @Tag(6)
    private String gender;
    @Tag(7)
    private Date birthday;
    @Tag(8)
    private Integer localityId;
    @Tag(9)
    private Integer zoneId;

    public UpdateSubscriberProfileRequest2() {
    }
    
    public UpdateSubscriberProfileRequest2(SubscriberProfileSRO profile, String name, String displayName, String gender, Date birthday, Integer localityId, Integer zone) {
        super();
        this.profile = profile;
        this.name = name;
        this.displayName = displayName;
        this.gender = gender;
        this.birthday = birthday;
        this.localityId = localityId;
        this.zoneId = zone;
    }

    public SubscriberProfileSRO getProfile() {
		return profile;
	}

	public void setProfile(SubscriberProfileSRO profile) {
		this.profile = profile;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    public Integer getZone() {
        return zoneId;
    }

    public void setZone(Integer zone) {
        this.zoneId = zone;
    }

}
