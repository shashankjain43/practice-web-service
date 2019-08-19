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
import com.snapdeal.core.entity.Audit;

public class AuditSRO implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 992068052597056233L;
	@Tag(1)
	private Integer id;
	@Tag(2)
    private int     entityId;
	@Tag(3)
    private String  entityType;
	@Tag(4)
    private String  changeLog;
	@Tag(5)
    private int     modifiedBy;
	@Tag(6)
    private String  comments;
	@Tag(7)
    private Date    modifiedTime;
	
	public AuditSRO(){}
	
	
    
    public AuditSRO(Audit audit) {
        this.id=audit.getId();
        this.entityId=audit.getEntityId();
        this.entityType=audit.getEntityType();
        this.changeLog=audit.getChangeLog();
        this.modifiedBy=audit.getModifiedBy();
        this.comments=audit.getComments();
        this.modifiedTime=audit.getModifiedTime();
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getEntityId() {
		return entityId;
	}
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getChangeLog() {
		return changeLog;
	}
	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
    
    
}

 