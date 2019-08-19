
DROP TABLE IF EXISTS `task_execution`;

CREATE table task_execution(
task_id varchar(250) NOT NULL,
run_no int(10) unsigned NOT NULL DEFAULT 1,
start_time datetime,
complete_time datetime,
completion_status enum('SUCCESS','FAILURE','RETRY'), 
completion_log blob,
created_time datetime NOT NULL,
updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
CONSTRAINT FK_taskId FOREIGN KEY (task_id) REFERENCES task(id),
CONSTRAINT UK_id_run UNIQUE (task_id,run_no)
);
