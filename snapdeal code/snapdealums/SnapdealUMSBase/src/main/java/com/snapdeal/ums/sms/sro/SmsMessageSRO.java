package com.snapdeal.ums.sms.sro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.notification.sms.SmsMessage;
import com.snapdeal.core.sro.email.TemplateParam;

public class SmsMessageSRO implements Serializable{

    
    private static final long     serialVersionUID = 1995142201L;
    @Tag(1)
    protected String              mobile;
    @Tag(2)
    protected String              templateName;
    @Tag(3)
    protected String              message;
    @Tag(4)
    private boolean               isDndActive;
    @Tag(5)
    private List<TemplateParam<? extends Serializable>> templateParams   = new ArrayList<TemplateParam<? extends Serializable>>();

    public SmsMessageSRO(SmsMessage message){
        this.mobile = message.getMobile();
        this.templateName = message.getTemplateName();
        this.message = message.getMessage();
        this.isDndActive = message.isDndActive();
        
    }
    
    
    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getTemplateName() {
        return templateName;
    }


    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isDndActive() {
        return isDndActive;
    }


    public void setDndActive(boolean isDndActive) {
        this.isDndActive = isDndActive;
    }


    public List<TemplateParam<? extends Serializable>> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<TemplateParam<? extends Serializable>> templateParams) {
        if (templateParams == null)
            return;
            this.templateParams = templateParams;
    }
    
}
