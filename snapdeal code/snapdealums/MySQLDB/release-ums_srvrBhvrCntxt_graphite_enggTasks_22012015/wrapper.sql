INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'defaultserver.behaviour.context',
'defaultServerBehaviourContext'
);

CREATE TABLE `disabled_service_urls` (
  `id` int(11)  unsigned NOT NULL AUTO_INCREMENT,
  `disabled_url` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `disabled_url_UNIQUE` (`disabled_url`)
);



CREATE TABLE `server_behaviour_context` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
);


CREATE TABLE `serverBehaviour_disabledUrl_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `serverBehaviourContext_id` int(11) unsigned NOT NULL,
  `disabled_url_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`serverBehaviourContext_id`) REFERENCES `server_behaviour_context` (`id`),
  FOREIGN KEY (`disabled_url_id`) REFERENCES `disabled_service_urls` (`id`)
);

INSERT INTO `ums`.`server_behaviour_context`
(
`name`)
VALUES
(

'defaultServerBehaviourContext'
);


INSERT INTO `ums`.`disabled_service_urls`
(
`disabled_url`)
VALUES
(

'/service/ums/email/sendFeedbackMail'
);


INSERT INTO `ums`.`disabled_service_urls`
(
`disabled_url`)
VALUES
(
'/service/ums/subscription/getEmailAllSubscribersIncrementalByDateAndId'
);



INSERT INTO `ums`.`serverBehaviour_disabledUrl_mapping`
(
`serverBehaviourContext_id`,
`disabled_url_id`)
VALUES
(

(SELECT `server_behaviour_context`.`id`
 from `ums`.`server_behaviour_context` where `server_behaviour_context`.`name`="defaultServerBehaviourContext"),
(SELECT `disabled_service_urls`.`id`
 from `ums`.`disabled_service_urls` where `disabled_service_urls`.`disabled_url`="/service/ums/email/sendFeedbackMail")
);



INSERT INTO `ums`.`serverBehaviour_disabledUrl_mapping`
(
`serverBehaviourContext_id`,
`disabled_url_id`)
VALUES
(

(SELECT `server_behaviour_context`.`id`
 from `ums`.`server_behaviour_context` where `server_behaviour_context`.`name`="defaultServerBehaviourContext"),
(SELECT `disabled_service_urls`.`id`
 from `ums`.`disabled_service_urls` where `disabled_service_urls`.`disabled_url`="/service/ums/subscription/getEmailAllSubscribersIncrementalByDateAndId")
);





INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('sdcash.proactive.limit', '100000');
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('sdwallet.record.fetch.limit', '3000');

INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'graphite.ip',
'54.179.177.140'
);

INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'graphite.port',
'2000'
);

INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'graphite.reporting.interval',
'60'
);


INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'monitoring.env',
'UMSProd'
);



INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'monitoring.env.server',
'UMSWebServer'
);

INSERT INTO `ums`.`ums_property`
(`name`,
`value`)
VALUES
(
'monitoring.repo',
'com.snapdeal.ums'
);

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`, `reply_to`, `email_channel_id`, `enabled`) VALUES ('UMSCheckpointBreachNotification', 'Checkpoint breach @ UMS production', '$msg', 'Snapdeal <noreply@snapdeals.co.in>', 'tech.ums@snapdeal.com,ums-qa@snapdeal.com,sayan.maiti@snapdeal.com', 'noreply@snapdeal.com', 2, 1);

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`, `reply_to`, `email_channel_id`, `enabled`) VALUES ('externalSystemAvailabilityNotification', 'External system availability @ UMS production', '$msg', 'Snapdeal <noreply@snapdeals.co.in>', 'tech.ums@snapdeal.com,ums-qa@snapdeal.com,prateek.kukreja@snapdeal.com,pankaj.bajeli@snapdeal.com', 'noreply@snapdeal.com', 2, 1);

