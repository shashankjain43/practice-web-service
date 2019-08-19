///*
// *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
// *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
// *  
// *  @version     1.0, 10-Apr-2012
// *  @author shishir
// */
//package com.snapdeal.ums.mao.subscribercitymigration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Repository;
//
//import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;
//
//@Repository("umsSubscriberCityMigrationMao")
//public class SubscriberCityMigrationMaoImpl implements ISubscriberCityMigrationMao {
//
//    @Autowired
//    @Qualifier("mongoTemplate")
//    private MongoOperations     mongoOperations;
//
//    private static final Logger LOG = LoggerFactory.getLogger(SubscriberCityMigrationMaoImpl.class);
//
//    @Async
//    @Override
//    public void saveSubscriberCityMigration(SubscriberCityMigration subscriberCityMigration) {
//        LOG.info("Inserting SZM for " + subscriberCityMigration.getEmailId() + "-" + subscriberCityMigration.getOldCity());
//        mongoOperations.save(subscriberCityMigration);
//    }
//
//    @Override
//    public SubscriberCityMigration getSubscriberCityMigration(String emailId, String city) {
//        LOG.info("Getting SZM for " + emailId);
//        return mongoOperations.findOne(new Query(Criteria.where("emailId").is(emailId).and("oldCity").is(city)), SubscriberCityMigration.class);
//    }
//
//}
