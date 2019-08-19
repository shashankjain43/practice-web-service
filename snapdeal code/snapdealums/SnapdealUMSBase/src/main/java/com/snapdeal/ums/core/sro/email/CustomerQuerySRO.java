 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 22-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.entity.CustomerQuery;

public class CustomerQuerySRO implements Serializable{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = -4139524244361509629L;
	@Tag(1)
	private Integer id;
	@Tag(2)
    private String  fullName;
	@Tag(3)
    private String  city;
	@Tag(4)
    private String  mobile;
	@Tag(5)
    private String  email;
	@Tag(6)
    private String  subject;
	@Tag(7)
    private String  comments;
	@Tag(8)
    private Date created;
    
	public CustomerQuerySRO(CustomerQuery customerQuery) {
       this.id=customerQuery.getId();
       this.fullName=customerQuery.getFullName();
       this.city=customerQuery.getCity();
       this.mobile=customerQuery.getMobile();
       this.email=customerQuery.getEmail();
       this.subject=customerQuery.getSubject();
       this.comments=customerQuery.getComments();
       this.created=customerQuery.getCreated();
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
}

 