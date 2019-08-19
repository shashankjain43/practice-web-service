package com.snapdeal.ums.mao.subscribercitymigration;

import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;

public interface ISubscriberCityMigrationMao {

    public void saveSubscriberCityMigration(SubscriberCityMigration subscriberCityMigration);

    public SubscriberCityMigration getSubscriberCityMigration(String emailId, String city);

}
