
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;

public class GetSubscriberCityMigrationResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -4533994654779043275L;
    @Tag(5)
    private SubscriberCityMigration getSubscriberCityMigration;

    public GetSubscriberCityMigrationResponse() {
    }

    public GetSubscriberCityMigrationResponse(SubscriberCityMigration getSubscriberCityMigration) {
        super();
        this.getSubscriberCityMigration = getSubscriberCityMigration;
    }

    public SubscriberCityMigration getGetSubscriberCityMigration() {
        return getSubscriberCityMigration;
    }

    public void setGetSubscriberCityMigration(SubscriberCityMigration getSubscriberCityMigration) {
        this.getSubscriberCityMigration = getSubscriberCityMigration;
    }

}
