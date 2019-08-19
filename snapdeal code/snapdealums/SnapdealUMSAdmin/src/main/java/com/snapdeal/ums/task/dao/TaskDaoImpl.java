package com.snapdeal.ums.task.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.UmsTask;


@SuppressWarnings("unchecked")
@Repository("taskDao")
public class TaskDaoImpl implements ITaskDao{

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UmsTask> getTasks() {
        Query q = sessionFactory.getCurrentSession().createQuery("from UmsTask where enabled=1");
        return q.list();
    }

    @Override
    public List<UmsTask> getAllTasks() {
        Query q = sessionFactory.getCurrentSession().createQuery("from UmsTask ");
        return q.list();
    }
    
   
    @Override
    public UmsTask getRecurrentTaskById(Integer taskId) {
        Query q = sessionFactory.getCurrentSession().createQuery("from UmsTask where id=:taskId");
        q.setParameter("taskId", taskId);
        return (UmsTask) q.uniqueResult();
    }

    @Override
    public UmsTask updateTask(UmsTask task) {
        return (UmsTask) sessionFactory.getCurrentSession().merge(task);
    }

    @Override
    public UmsTask getRecurrentTaskByName(String taskname) {
        Query q = sessionFactory.getCurrentSession().createQuery("from UmsTask where name=:taskname");
        q.setParameter("taskname", taskname);
        return (UmsTask) q.uniqueResult();
    }

}
