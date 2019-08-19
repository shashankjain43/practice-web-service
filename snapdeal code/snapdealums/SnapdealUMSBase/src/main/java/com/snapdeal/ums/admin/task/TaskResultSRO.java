package com.snapdeal.ums.admin.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.utils.MapEntryUtil;

public class TaskResultSRO implements Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Tag(1)
    private String              taskName;
    @Tag(2)
    private boolean             failed;
    @Tag(3)
    private String              message;
    @Tag(4)
    private List<MapEntryUtil>  resultItems;
    
    
    public TaskResultSRO(String taskName, boolean failed, String message) {
        super();
        this.taskName = taskName;
        this.failed = failed;
        this.message = message;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public boolean isFailed() {
        return failed;
    }
    public void setFailed(boolean failed) {
        this.failed = failed;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public List<MapEntryUtil> getResultItems() {
        return resultItems;
    }
    public void setResultItems(Map<String, String> results) {
        if(resultItems == null)
            return ;
        this.resultItems = new ArrayList<MapEntryUtil>();
        for(Entry<String, String> pair : results.entrySet())
            resultItems.add(new MapEntryUtil(pair.getKey(), pair.getValue()));
    }
    
    @Override
    public String toString() {
        return "TaskResultSRO [taskName=" + taskName + ", failed=" + failed + ", message=" + message + "]";
    }
    
}
