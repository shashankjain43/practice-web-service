package com.snapdeal.ums.auditing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditingThreadPoolExecutor extends ThreadPoolExecutor
{
    
    public AuditingThreadPoolExecutor(){
        this(20, 40,60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public AuditingThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue)
    {

        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    @Transactional
    public void execute(Runnable command)
    {
    
        // TODO Auto-generated method stub
        super.execute(command);
    }

}
