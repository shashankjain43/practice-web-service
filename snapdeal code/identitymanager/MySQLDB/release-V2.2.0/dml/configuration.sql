insert into configuration values('FREECHARGE','global.token.expiry.time','3','For FREECHARGE, expiry time is 2 days or 48 hrs.');
insert into configuration values('FREECHARGE','days.to.renew.token','1','For FREECHARGE, renew expiry time is 1 days.');

/* Transfer token for V02 */
insert into configuration values('global','default.transfertokengeneration.service.V02','com.snapdeal.ims.token.service.impl.TransferTokenGenerationServiceV02Impl','Transfertoken v02 implementation.');

insert into configuration (config_type, config_key, config_value, description) values('glboal','user.migration.status.notify.enabled','false','');

/*
update configuration set config_value='V02' where config_key= 'default.transfertokengeneration.service.version';
*/

insert into configuration (config_type, config_key, config_value, description) values('global','apis.whiteList.enabled','false','');

