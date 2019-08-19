DROP TABLE IF EXISTS `lock_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lock_user` (
  `user_id` varchar(127) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `login_attempts` tinyint(4) NOT NULL,
  `status` varchar(127) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)--ENGINE=InnoDB DEFAULT CHARSET=latin1;
--LOCK TABLES `lock_user` WRITE;

--INSERT INTO `lock_user` VALUES ('567259','2015-06-05 14:22:52',5,'','2015-06-05 14:22:14');

--UNLOCK TABLES;

