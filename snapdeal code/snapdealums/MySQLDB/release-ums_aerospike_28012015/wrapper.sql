insert into ums_property (name, value) VALUES ('ums.cache.enabled','true');

/* Aerospike Server Properties */
insert into ums_property (name, value) VALUES ('aerospike.ip','54.165.247.54');
insert into ums_property (name, value) VALUES ('aerospike.port','3000');
insert into ums_property (name, value) VALUES ('aerospike.cluster','54.165.247.54:3000,54.174.162.7:3000');

/* Aerospike Client Properties */
insert into ums_property (name, value) VALUES ('aerospike.maxSocketIdle','');
insert into ums_property (name, value) VALUES ('aerospike.maxThreads','300');
insert into ums_property (name, value) VALUES ('aerospike.sharedThreadPool','false');
insert into ums_property (name, value) VALUES ('aerospike.connection.timeout','');

/* Aerospike default read Properties */
insert into ums_property (name, value) VALUES ('aerospike.default.read.maxRetries','2');
insert into ums_property (name, value) VALUES ('aerospike.default.read.sleepBetweenRetries','3');
insert into ums_property (name, value) VALUES ('aerospike.default.read.timeout','20');

/* To uniquely identify each server */
INSERT INTO `ums`.`ums_property`(`name`,`value`)VALUES('server.id','UMS-Web');

/* Table to store keys to be evicted from cache */
CREATE TABLE `cache_key_to_be_evicted` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `namespace` varchar(45) NOT NULL,
  `set_name` varchar(45) NOT NULL,
  `key_to_be_evicted` varchar(60) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique` (`namespace`,`set_name`,`key_to_be_evicted`),
  KEY `index2` (`namespace`,`set_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/* Aerospike key eviction Properties */
insert into ums_property (name, value) VALUES ('aerospike.inconsistent.key.eviction.enabled','true');
insert into ums_property (name, value) VALUES ('aerospike.eviction.transaction.size','1001');
insert into ums_property (name, value) VALUES ('aerospike.eviction.transaction.iteration.limit','999');

/* Is Graphite enabled ? */
insert into ums_property (name, value) VALUES ('graphite.enabled','true');

