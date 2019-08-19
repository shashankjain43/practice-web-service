insert into configuration values('global','verification.upgrade.flow.link','www.freecharge.com/user/link?verificationCode=${upgradeFlowCode}&email=${upgradeFlowEmail}','');
insert into configuration values('global','verification.upgrade.flow.complete','www.freecharge.com/user/complete?verificationCode=${upgradeFlowCode}&email=${upgradeFlowEmail}','');
insert into configuration values('global','verification.upgrade.flow.parking','www.snapdeal.com/parking?verificationCode=${upgradeFlowCode}&email=${upgradeFlowEmail}','');

insert into configuration values('global','enable.new.global.token.generation.logic', 'false', '');