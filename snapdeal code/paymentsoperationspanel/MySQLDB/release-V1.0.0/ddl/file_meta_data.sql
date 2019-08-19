
DROP TABLE IF EXISTS `file_meta_data`;

CREATE TABLE `file_meta_data` (
  `userId` varchar(255) NOT NULL DEFAULT '',
  `fileName` varchar(255) NOT NULL DEFAULT '',
  `totalRows` bigint(12) DEFAULT NULL,
  `totalMoney` decimal(18,2) DEFAULT NULL,
  `successRowsNum` bigint(12) DEFAULT NULL,
  `totalSuccessMoney` decimal(18,2) DEFAULT NULL,
  `uploadTime` timestamp NULL DEFAULT NULL,
  `templateId` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `idempotencyId` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`,`fileName`)
);
