package com.snapdeal.ims.otp.internal.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailInfo {
	private String subject;
	private String from;
	private String replyTo;
	private String to;
	private List<String> cc;
	private List<String> bcc;
	private String templateName;
	private String mailHTML;
	private String OTP;
	private String userId;
	private Map<String, Object> templateParams = new HashMap<String, Object>();

	// public void addRecepient(String to) {
	// this.to.add(to);
	// }
	//
	// public void addRecepients(Collection<String> tos) {
	// this.to.addAll(tos);
	// }
	//
	// public void addCC(String cc) {
	// if (this.cc == null) {
	// this.cc = new ArrayList<String>(1);
	// }
	// this.cc.add(cc);
	// }
	//
	// public void addCCs(Collection<String> ccs) {
	// if (this.cc == null) {
	// this.cc = new ArrayList<String>(ccs.size());
	// }
	// this.cc.addAll(ccs);
	// }
	//
	// public void addBCC(String bcc) {
	// if (this.bcc == null) {
	// this.bcc = new ArrayList<String>(1);
	// }
	// this.bcc.add(bcc);
	// }
	//
	// public void addBCCs(Collection<String> bccs) {
	// if (this.bcc == null) {
	// this.bcc = new ArrayList<String>(bccs.size());
	// }
	// this.bcc.addAll(bccs);
	// }
	//
	// public void addTemplateParam(String name, Object value) {
	// templateParams.put(name, value);
	// }
}
