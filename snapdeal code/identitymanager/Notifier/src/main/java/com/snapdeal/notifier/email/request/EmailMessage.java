package com.snapdeal.notifier.email.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

public @Data class EmailMessage implements Serializable{
   
   private static final long serialVersionUID = 1L;
   
   private String              subject;
   
   private String              from;
   
   private String              replyTo;
   
   private List<String>        to;

   private List<String>        cc;
   
   private String              templateKey;
   
   private String              requestId;

   private Map<String, String> tags = new HashMap<String, String>();
   
   private String 			    taskId;
   
   private String				emailSendBy;

   public void addRecepients(List<String> to) {
      this.to = to;
   }

   public void addCCs(List<String> cc) {
     this.cc = cc;
   }
}
