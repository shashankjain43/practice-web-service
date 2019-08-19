package com.snapdeal.ums.admin.task;

import java.util.List;

import com.snapdeal.task.exception.TaskException;
import com.snapdeal.ums.core.entity.UmsTask;



public interface ITaskService {

    public void loadTasks() throws Exception;

    public void startTasks() throws Exception;

    List<UmsTask> getAllTasks();

    void runRecurrentTask(Integer taskId);

    UmsTask getRecurrentTaskById(Integer taskId);

    UmsTask updateTask(UmsTask task);

    void updateTaskScheduler(UmsTask task) throws TaskException;

    List<UmsTask> getTasks();

    public UmsTask getRecurrentTaskByName(String taskname);
}
