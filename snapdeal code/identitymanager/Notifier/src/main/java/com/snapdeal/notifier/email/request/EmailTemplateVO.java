package com.snapdeal.notifier.email.request;


import com.snapdeal.base.velocity.Template;

import java.util.List;

import lombok.Data;

public @Data class EmailTemplateVO {
   
   private String       name;
   
   private Template     bodyTemplate;
   
   private Template     plainTextBodyTemplate;
   
   private Template     subjectTemplate;
   
   private List<String> to;
   
   private List<String> cc;
   
   private List<String> bcc;
   
   private String       from;
   
   private String       replyTo;
   
   private int          emailChannelId;
}
