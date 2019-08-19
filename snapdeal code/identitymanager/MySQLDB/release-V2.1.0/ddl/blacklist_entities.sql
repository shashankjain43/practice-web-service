CREATE TABLE `blacklist_entities` (
  `entity_type` enum('DOMAIN','EMAIL') NOT NULL,
  `entity` varchar(255) NOT NULL,
  PRIMARY KEY (`entity_type`,`entity`)
);

