
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreateSubscriberProfileRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 2528212808496112767L;
    @Tag(3)
    private String email;
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

    public CreateSubscriberProfileRequest2() {
    }
    
    public CreateSubscriberProfileRequest2(String email, String name, String displayName, String gender, Date birthday, Integer localityId, Integer zone) {
        super();
        this.email = email;
        this.name = name;
        this.displayName = displayName;
        this.gender = gender;
        this.birthday = birthday;
        this.localityId = localityId;
        this.zoneId = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
