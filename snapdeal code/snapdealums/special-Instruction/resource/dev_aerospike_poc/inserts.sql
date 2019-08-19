insert into ums_property (name, value) VALUES ('ums.cache.enabled','true');

/* Aerospike Server Properties */
insert into ums_property (name, value) VALUES ('aerospike.ip','54.165.247.54');
insert into ums_property (name, value) VALUES ('aerospike.port','3000');
insert into ums_property (name, value) VALUES ('aerospike.cluster','10.1.18.59:3000,10.1.22.8:3000');

/* Aerospike Client Properties */
insert into ums_property (name, value) VALUES ('aerospike.maxSocketIdle','');
insert into ums_property (name, value) VALUES ('aerospike.maxThreads','300');
insert into ums_property (name, value) VALUES ('aerospike.sharedThreadPool','false');
insert into ums_property (name, value) VALUES ('aerospike.connection.timeout','');

/* Aerospike default read Properties */
insert into ums_property (name, value) VALUES ('aerospike.default.read.maxRetries','2');
insert into ums_property (name, value) VALUES ('aerospike.default.read.sleepBetweenRetries','3');
insert into ums_property (name, value) VALUES ('aerospike.default.read.timeout','20');
