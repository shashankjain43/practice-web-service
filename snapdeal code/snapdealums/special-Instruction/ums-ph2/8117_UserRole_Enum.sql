use ums;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `description` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

insert into ums.role values 
(null,"unverified","unverified"),
(null,"registered","registered"),
(null,"admin","admin"),
(null, "bd","bd"),
(null, "content","content"),
(null, "SUPER","SUPER"),
(null, "vendor","vendor"),
(null, "affiliate","affiliate"),
(null, "ccmanager","ccmanager"),
(null, "marketing","marketing"),
(null, "getaways-bd","getaways-bd"),
(null, "product","product"),
(null, "campaignmgr","campaignmgr"),
(null, "ccexecutive","ccexecutive"),
(null, "finance","finance"),
(null, "shipprovider","shipprovider"),
(null, "mis","mis"),
(null, "audit","audit"),
(null, "seo","seo"),
(null, "ContentMgr","ContentMgr"),
(null, "amchief","amchief"),
(null, "am","am"),
(null, "designchief","designchief"),
(null, "design","design"),
(null, "contentchief","contentchief"),
(null, "qachief","qachief"),
(null, "qa","qa"),
(null, "hr","hr"),
(null, "csadmin","csadmin"),
(null, "productsourcing","productsourcing"),
(null, "logistics","logistics"),
(null, "mobile","mobile"),
(null, "deletion","deletion"),
(null, "canAppLogistics","canAppLogistics"),
(null, "categoryAdmin","categoryAdmin"),
(null, "storefrontSuperAdmin","storefrontSuperAdmin"),
(null, "financemgr","financemgr"),
(null, "pcexecutive","pcexecutive"),
(null, "pcmanager","pcmanager"),
(null, "codverification","codverification"),
(null, "ccexecutiveoutgoing","ccexecutiveoutgoing"),
(null, "category","category"),
(null, "marketingsfadmin","marketingsfadmin");

ALTER TABLE ums.user_role ADD COLUMN `role_code` varchar(64) NOT NULL;
UPDATE ums.user_role set role_code = role;
ALTER TABLE ums.user_role DROP COLUMN role;
ALTER TABLE ums.user_role ADD CONSTRAINT `FK_role_user_role` FOREIGN KEY (`role_code`) REFERENCES `role` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;