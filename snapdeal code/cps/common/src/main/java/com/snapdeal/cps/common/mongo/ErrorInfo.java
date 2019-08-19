package com.snapdeal.cps.common.mongo;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "errorInfo")
public class ErrorInfo {

    @Id
    private String elementId;
    private String elementType;
    private String description;
    private Date createTs;

    public ErrorInfo(String elementType, String elementId) {
        this.elementId = elementId;
        this.elementType = elementType;
        this.createTs = Calendar.getInstance().getTime();
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

}
