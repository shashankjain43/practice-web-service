use ums;
set @localityweburl:=(select value from ums_property where name = 'catalog.web.service.url');
insert into ums_property (name,value) values ('locality.web.service.url', @localityweburl);
