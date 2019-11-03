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
  UNIQUE KEY `servers_profile_UNIQUE` (`name`)
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




