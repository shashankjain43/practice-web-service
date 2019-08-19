package com.snapdeal.merchant.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MpanelConfig {
	
	@Value("${aws.s3.accesskey}")
	private  String s3AccessKey;
	 
	@Value("${aws.s3.secretkey}")
	private  String s3SecretKey;
	
	@Value("${aws.s3.bucketname}")
	private  String s3BucketName;
	
	@Value("${aws.s3.prefix}")
	private  String s3Prefix;
	
	@Value("${aws.s3.endpoint}")
	private  String s3EndPoint;
	
	@Value("${aws.s3.url.expirytime}")
	private  int s3UrlExpiryTime;
	
	@Value("${payments.mob.clientname}")
	private  String mobClientName;
	
	@Value("${payments.mob.ip}")
	private  String mobIP;
	
	@Value("${payments.mob.port}")
	private  String mobPort;
	
	@Value("${payments.mob.apitimeout}")
	private  int mobApiTimeout;
	
	@Value("${payments.mv.ip}")
	private  String mvIP;
	
	@Value("${payments.mv.port}")
	private  String mvPort;
	
	@Value("${payments.mv.apitimeout}")
	private  int mvApiTimeOut;
	
	@Value("${payments.mv.clientid}")
	private  String mvClientId;
	
	@Value("${payments.mv.clientkey}")
	private  String mvClientKey;
	
	@Value("${payments.rms.ip}")
	private  String rmsIP;
	
	@Value("${payments.rms.port}")
	private  String rmsPort;
	
	@Value("${payments.rms.apitimeout}")
	private  int rmsApiTimeout;
	
	@Value("${payments.ag.ip}")
	private  String aggIP;
	
	@Value("${payments.ag.port}")
	private  String aggPort;
	
	@Value("${payments.ag.apitimeout}")
	private  int aggApiTimeout;
	
	@Value("${payments.ag.clientid}")
	private  String aggClientId;
	
	@Value("${payments.ag.clientkey}")
	private  String aggClientKey;
	
	@Value("${mpanel.roles}")
	private List<String> roles;
	
	@Value("${temp.file.path}")
	private  String tempFilePath ;
	
	@Value("${mpanel.api.retrycount}")
	private  int apiRetryCount;
	
	@Value("${txn.export.count}")
	private int txnExportCount;
			
	@Value("${mpanel.checkout.sandbox.url}")
	private String sandboxUrl;
	
	@Value("${mpanel.checkout.production.url}")
	private String prodUrl;
	
	@Value("${refund.templ.file}")
	private String refundTemplateFile;
	
	@Value("${payments.sr.url}")
	private String srHostURL;
	
	@Value("${payments.sr.clientname}")
	private String srClientId;
	
	@Value("${payments.sr.clientkey}")
	private String srClientKey;
				
	@Value("${payments.fcnotifier.host}")
	private String fcNotifierHost;
	
	@Value("${payments.fcnotifier.port}")
	private String fcNotifierPort;
	
	@Value("${payments.fcnotifier.apitimeout}")
	private int fcNotifierApiTimeout;

	@Value("${mpanel.contactus.fromemailid}")
	private String fromEmail;
	
	@Value("${mapnel.contactus.replytoemailid}")
	private String replyToEmail;
			
	@Value("${mpanel.task.execution.time}")	
	private long taskExecutionTime;
	
	@Value("${mapnel.task.retry.wait.time}")
	private long taskRetryWaitTime;
	
	@Value("${mpanel.task.execution.stop.time}")
	private long taskExecutionStopTime;
	
	@Value("${mpanel.task.retry.limit}")
	private int taskRetryLimit;

	@Value("${mpanel.limit.loop.count}")
	public int getLoopCount;
	
	@Value("${mpanel.superadmin.loginname}")
	private String adminLoginName;
	
	@Value("${mpanel.superadmin.password}")
	private String adminPassword;
	
	@Value("${mapnel.merchantfee.value}")
	private String merchantFee;
	
	@Value("${mpanel.fixedfee.value}")
	private String fixedFee;
	
	@Value("${mpanel.generaluser.contactus.templatekey}")
	private String generalUserContactusTmplKey;
	
	@Value("${mpanel.merchantuser.contactus.templatekey}")
	private String merchantUserContactusTmplKey;
	
	@Value("${mpanel.contactus.toemailid}")
	private String merchantSupportEmailId;
		
/*	@Value("${mpanel.createmerchant.toemailids}")
	private List<String> createMerchantToEmails;*/
	
	@Value("${mpanel.createmerchant.offline.toemailids}")
	private List<String> createOfflineMerchantToEmails ;
	
	@Value("${mpanel.createmerchant.online.toemailids}")
	private List<String> createOnlineMerchantToEmails ;
	
	@Value("${mpanel.createmerchant.templatekey}")
	private String createMerchantTemplateKey ;
	
}
