
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;

public class SaveSubscriberCityMigrationRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 3240351981643622764L;
    @Tag(3)
    private SubscriberCityMigration subscriberCityMigration;

    public SaveSubscriberCityMigrationRequest() {
    }
    
    public SaveSubscriberCityMigrationRequest(SubscriberCityMigration subscriberCityMigration) {
        super();
        this.subscriberCityMigration = subscriberCityMigration;
    }

    public SubscriberCityMigration getSubscriberCityMigration() {
        return subscriberCityMigration;
    }

    public void setSubscriberCityMigration(SubscriberCityMigration subscriberCityMigration) {
        this.subscriberCityMigration = subscriberCityMigration;
    }

}
