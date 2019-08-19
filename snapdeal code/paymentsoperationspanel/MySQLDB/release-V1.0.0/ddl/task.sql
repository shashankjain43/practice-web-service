
DROP TABLE IF EXISTS `task`;

CREATE table task(
id varchar(250) NOT NULL,
task_status enum('OPEN','CLOSED') DEFAULT 'OPEN' NOT NULL,
task_meta_data blob,
original_schedule_time datetime NOT NULL,
current_schedule_time datetime NOT NULL,
created_time datetime NOT NULL,
updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);
