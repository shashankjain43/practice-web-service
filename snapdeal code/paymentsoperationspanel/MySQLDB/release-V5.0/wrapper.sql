use `payments_ops_panel`;

CREATE TABLE `ops_merchants` (
  `merchant_id` varchar(255) NOT NULL,
  `merchant_email` varchar(255) DEFAULT NULL,
  `merchant_type` varchar(50) DEFAULT NULL,
  `kyc_status` varchar(100) DEFAULT NULL,
  `mail_count` int(11) DEFAULT NULL,
  `call_count` int(11) DEFAULT NULL,
  `last_call_status` varchar(100) DEFAULT NULL,
  `last_call_time` datetime DEFAULT NULL,
  PRIMARY KEY (`merchant_id`)
);

CREATE TABLE `ops_merchant_calls` (
  `merchant_id` varchar(255) DEFAULT NULL,
  `contact_type` varchar(100) DEFAULT NULL,
  `contact_status` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `contact_path` varchar(500) DEFAULT NULL,
  `contact_time` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  KEY `merchant_id` (`merchant_id`),
  CONSTRAINT  FOREIGN KEY (`merchant_id`) REFERENCES `ops_merchants` (`merchant_id`)
);


alter table file_meta_data add processingRow int(11);
alter table file_meta_data add completionTime timestamp null;
