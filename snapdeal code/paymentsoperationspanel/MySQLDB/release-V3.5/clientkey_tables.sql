
DROP TABLE IF EXISTS `target_api_mapper`;

CREATE TABLE `target_api_mapper` (
  `target_application` varchar(255) NOT NULL,
  `api_id` varchar(255) NOT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  `active_status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`target_application`,`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `target_api_mapper` WRITE;
/*!40000 ALTER TABLE `target_api_mapper` DISABLE KEYS */;
INSERT INTO `target_api_mapper` VALUES ('WALLET','addBankDetails','addBankDetails',1),('WALLET','cancelTransaction','cancelTransaction',1),('WALLET','createAccount','createAccount',1),('WALLET','createCorporateAccount','createCorporateAccount',1),('WALLET','createMerchantAccount','createMerchantAccount',1),('WALLET','createPromotionalCorpAccount','createPromotionalCorpAccount',1),('WALLET','creditGeneralBalance','creditGeneralBalance',1),('WALLET','creditGeneralBalanceToUser','creditGeneralBalanceToUser',1),('WALLET','creditGeneralToUserMobile','creditGeneralToUserMobile',1),('WALLET','creditVoucherBalance','creditVoucherBalance',1),('WALLET','creditVoucherByUserEmail','creditVoucherByUserEmail',1),('WALLET','creditVoucherToUserMobile','creditVoucherToUserMobile',1),('WALLET','debitBalance','debitBalance',1),('WALLET','disableCorpAccount','disableCorpAccount',1),('WALLET','doesWalletExist','doesWalletExist',1),('WALLET','enableCorpAccount','enableCorpAccount',1),('WALLET','enablePromotionalAccount','enablePromotionalAccount',1),('WALLET','enableSDMoneyAccount','enableSDMoneyAccount',1),('WALLET','getAccountBalance','getAccountBalance',1),('WALLET','getAccountBalanceDetails','getAccountBalanceDetails',1),('WALLET','getBankDetails','getBankDetails',1),('WALLET','getCancelledVoucherDetails','getCancelledVoucherDetails',1),('WALLET','getCorpAccount','getCorpAccount',1),('WALLET','getCorpAccountBalancecancelTransaction','getCorpAccountBalancecancelTransaction',1),('WALLET','getCorpAccountBalanceOnTimestamp','getCorpAccountBalanceOnTimestamp',1),('WALLET','getCorpAccountInfoById','getCorpAccountInfoById',1),('WALLET','getCorpAccountsForEntity','getCorpAccountsForEntity',1),('WALLET','getCorpAccountsForEntityAndType','getCorpAccountsForEntityAndType',1),('WALLET','getDefaultCorpAccountForEntityAndType','getDefaultCorpAccountForEntityAndType',1),('WALLET','getDefaultCorpAccountForType','getDefaultCorpAccountForType',1),('WALLET','getExpiredVoucherDetails','getExpiredVoucherDetails',1),('WALLET','getManualReconTransactions','getManualReconTransactions',1),('WALLET','getMirrorAccount','getMirrorAccount',1),('WALLET','getMoneyOutHistory','getMoneyOutHistory',1),('WALLET','getMoneyOutStatus','getMoneyOutStatus',1),('WALLET','getMoneyOutStatusFromBank','getMoneyOutStatusFromBank',1),('WALLET','getParkedUserBalance','getParkedUserBalance',1),('WALLET','getParkedVoucherBalanceDetails','getParkedVoucherBalanceDetails',1),('WALLET','getParkedVoucherBalanceForEmail','getParkedVoucherBalanceForEmail',1),('WALLET','getPendingGeneralBalanceLimits','getPendingGeneralBalanceLimits',1),('WALLET','getPendingGeneralBalanceLimitsForUser','getPendingGeneralBalanceLimitsForUser',1),('WALLET','getSDMoneyAccount','getSDMoneyAccount',1),('WALLET','getTransactionById','getTransactionById',1),('WALLET','getTransactionByIdempotencyId','getTransactionByIdempotencyId',1),('WALLET','getTransactionByReference','getTransactionByReference',1),('WALLET','getTransactions','getTransactions',1),('WALLET','getTransactionsForUser','getTransactionsForUser',1),('WALLET','getUserBankDetails','getUserBankDetails',1),('WALLET','getVoucherBalanceDetails','getVoucherBalanceDetails',1),('WALLET','getVoucherDetails','getVoucherDetails',1),('WALLET','getVouchersForTransaction','getVouchersForTransaction',1),('WALLET','getVoucherTransactions','getVoucherTransactions',1),('WALLET','loadMerchantCorpAccount','loadMerchantCorpAccount',1),('WALLET','loadMerchantRiskReserveCorpAccount','loadMerchantRiskReserveCorpAccount',1),('WALLET','loadMoney','loadMoney',1),('WALLET','loadPGSettleCorpAccount','loadPGSettleCorpAccount',1),('WALLET','modifyBankDetailsStatus','modifyBankDetailsStatus',1),('WALLET','moneyOut','moneyOut',1),('WALLET','reconcileWithdraw','reconcileWithdraw',1),('WALLET','refundBalance','refundBalance',1),('WALLET','reverseLoadMoney','reverseLoadMoney',1),('WALLET','reverseRefundTransaction','reverseRefundTransaction',1),('WALLET','reverseTransaction','reverseTransaction',1),('WALLET','setBankDetailsVerified','setBankDetailsVerified',1),('WALLET','setMoneyOutStatus','setMoneyOutStatus',1),('WALLET','setMoneyOutStatusForSuccess','setMoneyOutStatusForSuccess',1),('WALLET','settleCorpAccountBalance','settleCorpAccountBalance',1),('WALLET','suspendSDMoneyAccount','suspendSDMoneyAccount',1),('WALLET','updateCorpAccountBalance','updateCorpAccountBalance',1),('WALLET','updateUnverifiedBankDetails','updateUnverifiedBankDetails',1),('WALLET','updateUserMigrationStatus','updateUserMigrationStatus',1),('WALLET','withdrawGeneralBalance','withdrawGeneralBalance',1);
/*!40000 ALTER TABLE `target_api_mapper` ENABLE KEYS */;
UNLOCK TABLES;



DROP TABLE IF EXISTS `client_key_mapper`;

CREATE TABLE `client_key_mapper` (
  `user_id` varchar(255) NOT NULL DEFAULT '',
  `client_name` varchar(255) NOT NULL DEFAULT '',
  `source_application` varchar(255) DEFAULT '',
  `target_application` varchar(255) NOT NULL,
  `client_context` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `remarks` text DEFAULT NULL,
  PRIMARY KEY (`user_id`,`client_name`,`target_application`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



