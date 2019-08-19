package com.snapdeal.payments.view.taskhandlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskHandlerFactory {

	@Autowired
	private List<BaseTaskHandler>  baseTaskHandlerList ;
	
	private Map<String ,BaseTaskHandler>  baseTaskHandlerMap = new HashMap<String, BaseTaskHandler>();
	
	@PostConstruct
	public void setBaseTaskHandlerMap(){
		for(BaseTaskHandler baseTask :baseTaskHandlerList){
			baseTaskHandlerMap.put(baseTask.getClass().getName(), baseTask) ;
		}
	}
	@SuppressWarnings("unchecked")
	public <T extends BaseTaskHandler> T getBaseTask(Class<T> classname){
		return (T)baseTaskHandlerMap.get(classname.getName()) ;
	}
}

