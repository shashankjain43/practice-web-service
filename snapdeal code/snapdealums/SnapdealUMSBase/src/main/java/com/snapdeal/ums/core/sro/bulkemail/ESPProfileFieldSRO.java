 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 22-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.bulkemail;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class ESPProfileFieldSRO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6748469032512549532L;
	@Tag(1)
	private Integer              id;
	@Tag(2)
    private String               fieldName;
	@Tag(3)
    private Integer              espId;
	@Tag(4)
    private String               espFieldName;
    
	public ESPProfileFieldSRO(){
	    super();
	}
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Integer getEspId() {
		return espId;
	}
	public void setEspId(Integer espId) {
		this.espId = espId;
	}
	public String getEspFieldName() {
		return espFieldName;
	}
	public void setEspFieldName(String espFieldName) {
		this.espFieldName = espFieldName;
	}
    
    
}

 