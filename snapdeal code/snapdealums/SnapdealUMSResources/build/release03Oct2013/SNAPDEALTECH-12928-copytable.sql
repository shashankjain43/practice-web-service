use ums;
alter table email_template drop FOREIGN KEY FK_channel_id;
alter table email_template add index `channel_id` (`email_channel_id`);
delete from snapdeal_property where name not in ('oms.web.service.url','use.oms.web.service','catalog.web.service.url','ipms.web.service.url','shipping.web.service.url','use.shipping.web.service','reload.cache.reference.time','reload.cache.reference.time.format','reload.cache.self.enabled','reload.cache.check.enabled','reload.cache.check.interval','system.configuration.file.path','system.configuration.dir.path','memcached.server.list','reload.cache.interval','static.resources.path','content.path','context.path','static.root.directory.path','finance.upload.refund.email','reload.cache.manual.password','responsetime.tracing.threshold','default.shipping.time','default.esp.id');
insert into snapdeal_property (name,value) values ('s4.web.service.url','');
DROP TABLE IF EXISTS `ums_property`;
RENAME TABLE snapdeal_property TO ums_property;
