ALTER TABLE task CHANGE COLUMN task_status task_status ENUM('OPEN','CLOSED','BLOCKED') NOT NULL ;
ALTER TABLE task_fc CHANGE COLUMN task_status task_status ENUM('OPEN','CLOSED','BLOCKED') NOT NULL ;
ALTER TABLE task ADD retry_limit int(10) DEFAULT 0 NOT NULL;
ALTER TABLE task_fc ADD retry_limit int(10) DEFAULT 0 NOT NULL;
