use ums;
 CREATE TABLE `access_control` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pattern` varchar(300) CHARACTER SET latin1 NOT NULL,
  `roles` varchar(500) CHARACTER SET latin1 DEFAULT NULL,
  `feature` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pattern` (`pattern`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

insert into access_control(pattern,roles,feature,link,created,updated) values ('/admin/taskAdmin','SUPER,product','Manage Task Admin','/admin/taskAdmin',now(),now());
insert into access_control(pattern,roles,feature,link,created,updated) values ('/admin/userAdmin','SUPER,admin','Manage User Admin','/admin/userAdmin',now(),now());
insert into access_control(pattern,roles,feature,link,created,updated) values ('/admin/addAccessControl','SUPER,admin','Manage Access Control','/admin/addAccessControl',now(),now());
insert into access_control(pattern,roles,feature,link,created,updated) values ('/admin/marketing','SUPER,marketing','Affiliate Subscription','/admin/marketing/affiliateSubscription',now(),now());
insert into access_control(pattern,roles,feature,link,created,updated) values ('/admin/roleAdmin','SUPER,admin','Create New  Roles','/admin/roleAdmin',now(),now());

