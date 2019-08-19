package com.snapdeal.cps.common.mongo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lastRunInfo")
public class LastRunInfo {

    private String processName;
    private Date lastRunTs;

    // Getters and Setters
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Date getLastRunTs() {
        return lastRunTs;
    }

    public void setLastRunTs(Date lastRunTs) {
        this.lastRunTs = lastRunTs;
    }

    @Override
    public String toString() {
        return "LastRunInfo [processName=" + processName + ", lastRunTs=" + lastRunTs + "]";
    }
}
