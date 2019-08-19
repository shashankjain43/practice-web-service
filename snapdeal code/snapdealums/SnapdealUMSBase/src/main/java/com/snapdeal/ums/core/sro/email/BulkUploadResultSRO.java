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
import com.snapdeal.core.dto.BulkUploadResultDTO;

public class BulkUploadResultSRO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6151332190437682995L;
	@Tag(1)
	private int id;
	@Tag(2)
	private String name;
	@Tag(3)
	private String vendorCode;
	@Tag(4)
	private String vendorSku;
	@Tag(5)
	private String supc;
	@Tag(6)
	private String pageUrl;
	@Tag(7)
	private Date startDate;
	@Tag(8)
	private Date endDate;
	@Tag(9)
	private Date createdDate;
	@Tag(10)
	private String comments;
	@Tag(11)
	private boolean valid;

	public BulkUploadResultSRO(BulkUploadResultDTO resultDTO) {
	    this.id=resultDTO.getId();
	    this.name=resultDTO.getName();
	    this.vendorCode=resultDTO.getVendorCode();
	    this.vendorSku=resultDTO.getVendorSku();
	    this.supc=resultDTO.getSupc();
	    this.pageUrl=resultDTO.getPageUrl();
	    this.startDate=resultDTO.getStartDate();
	    this.endDate=resultDTO.getEndDate();
	    this.createdDate=resultDTO.getCreatedDate();
	    this.comments=resultDTO.getComments();
	    this.valid=resultDTO.isValid();
	    
	}

    public String getSupc() {
		return supc;
	}

	public void setSupc(String supc) {
		this.supc = supc;
	}

	

	public String getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(String code) {
		this.vendorCode = code;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(Boolean isValid) {
		this.valid = isValid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendorSku() {
		return vendorSku;
	}

	public void setVendorSku(String vendorSku) {
		this.vendorSku = vendorSku;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// return *isValid* for display in bulk upload task result email
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder();
		if (this.isValid()) {
			resultBuilder.append("Result: ").append("SUCCESS");
		} else {
			resultBuilder.append("Result: ").append("FAIL");
		}
		return resultBuilder.toString();
	}


}

 