<<<<<<< HEAD
=======
CREATE TABLE task_fc (
id varchar(250) NOT NULL,
task_status enum('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
task_meta_data blob,
original_schedule_time datetime NOT NULL,
current_schedule_time datetime NOT NULL,
created_time datetime NOT NULL,
updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (id)
);

>>>>>>> origin/release-V2.1.2
CREATE TABLE task_execution_fc ( 
task_id varchar(250) NOT NULL, 
run_no int(10) unsigned NOT NULL DEFAULT '1', 
start_time datetime DEFAULT NULL, 
complete_time datetime DEFAULT NULL, 
completion_status enum('SUCCESS','FAILURE','RETRY') DEFAULT NULL, 
completion_log blob, 
created_time datetime NOT NULL,
updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
UNIQUE KEY UK_id_run (task_id,run_no), 
CONSTRAINT FK_taskId_fc FOREIGN KEY (task_id) REFERENCES task_fc (id));
