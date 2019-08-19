package com.snapdeal.ums.task.dao;

import java.util.List;

import com.snapdeal.ums.core.entity.UmsTask;

public interface ITaskDao {


    public List<UmsTask> getTasks();

    public UmsTask getRecurrentTaskById(Integer taskId);

    public UmsTask updateTask(UmsTask task);

    public List<UmsTask> getAllTasks();

    public UmsTask getRecurrentTaskByName(String taskname);
}
