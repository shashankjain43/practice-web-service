
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAutoAccountConfirmationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5862102396788563783L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String password;
    @Tag(5)
    private String name;
    @Tag(6)
    private String contextPath;
    @Tag(7)
    private String contentPath;
    @Tag(8)
    private String confirmationLink;

    public SendAutoAccountConfirmationEmailRequest() {
    }
    
    

    public SendAutoAccountConfirmationEmailRequest(String email, String password, String name, String contextPath, String contentPath, String confirmationLink) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
        this.confirmationLink = confirmationLink;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getConfirmationLink() {
        return confirmationLink;
    }

    public void setConfirmationLink(String confirmationLink) {
        this.confirmationLink = confirmationLink;
    }

    @Override
    public String toString() {
        return "SendAutoAccountConfirmationEmailRequest [email=" + email + ", password=" + password + ", name=" + name + ", contextPath=" + contextPath + ", contentPath="
                + contentPath + ", confirmationLink=" + confirmationLink + "]";
    }

}
