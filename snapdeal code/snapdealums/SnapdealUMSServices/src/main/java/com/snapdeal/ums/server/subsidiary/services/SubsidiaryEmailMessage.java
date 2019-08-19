package com.snapdeal.ums.server.subsidiary.services;

	

	import java.io.Serializable;
	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;

	import com.snapdeal.base.audit.annotation.AuditableClass;
	import com.snapdeal.base.audit.annotation.AuditableField;

	@AuditableClass
	public class SubsidiaryEmailMessage implements Serializable{
	    
	    
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1485697410;
	    @AuditableField
	    private String              from;
	    private String              replyTo;
	    @AuditableField
	    private List<String>        to;

	    private List<String>        cc;
	    private List<String>        bcc;
	    @AuditableField
	    private String              templateName;
	    private String              mailHTML;
	    private String 				password;
	    @AuditableField
	    private Map<String, Object> templateParams = new HashMap<String, Object>();

	    
	    public SubsidiaryEmailMessage()
	    {

	    }

	    public SubsidiaryEmailMessage(String templateName) {
	        this.templateName = templateName;
	        this.to = new ArrayList<String>();
	    }

	    public SubsidiaryEmailMessage(List<String> to, String templateName) {
	        this.to = to;
	        this.templateName = templateName;
	    }

	    public SubsidiaryEmailMessage(List<String> to, String from, String replyTo, String templateName) {
	        this.from = from;
	        this.replyTo = replyTo;
	        this.to = to;
	        this.templateName = templateName;
	    }

	    public SubsidiaryEmailMessage(String to, String from, String replyTo, String templateName) {
	        this.from = from;
	        this.replyTo = replyTo;
	        this.to = new ArrayList<String>(1);
	        this.to.add(to);
	        this.templateName = templateName;
	    }

	    public SubsidiaryEmailMessage(String to, String templateName) {
	        this.to = new ArrayList<String>(1);
	        this.to.add(to);
	        this.templateName = templateName;
	    }

	    public void addRecepient(String to) {
	        this.to.add(to);
	    }

	    public void addRecepients(Collection<String> tos) {
	        this.to.addAll(tos);
	    }

	    public void addCC(String cc) {
	        if (this.cc == null) {
	            this.cc = new ArrayList<String>(1);
	        }
	        this.cc.add(cc);
	    }

	    public void addCCs(Collection<String> ccs) {
	        if (this.cc == null) {
	            this.cc = new ArrayList<String>(ccs.size());
	        }
	        this.cc.addAll(ccs);
	    }

	    public void addBCC(String bcc) {
	        if (this.bcc == null) {
	            this.bcc = new ArrayList<String>(1);
	        }
	        this.bcc.add(bcc);
	    }

	    public void addBCCs(Collection<String> bccs) {
	        if (this.bcc == null) {
	            this.bcc = new ArrayList<String>(bccs.size());
	        }
	        this.bcc.addAll(bccs);
	    }

	    public void addTemplateParam(String name, Object value) {
	        templateParams.put(name, value);
	    }

	    public String getFrom() {
	        return from;
	    }

	    public void setFrom(String from) {
	        this.from = from;
	    }

	    public String getReplyTo() {
	        return replyTo;
	    }

	    public List<String> getTo() {
	        return to;
	    }

	    public List<String> getCc() {
	        return cc;
	    }

	    public List<String> getBcc() {
	        return bcc;
	    }

	    public String getTemplateName() {
	        return templateName;
	    }

	    public Map<String, Object> getTemplateParams() {
	        return templateParams;
	    }

	    public void setMailHTML(String mailHTML) {
	        this.mailHTML = mailHTML;
	    }

	    public String getMailHTML() {
	        return mailHTML;
	    }

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

