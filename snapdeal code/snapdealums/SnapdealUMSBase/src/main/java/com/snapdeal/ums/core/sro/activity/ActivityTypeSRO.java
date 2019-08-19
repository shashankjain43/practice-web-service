 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 16-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.activity;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.expression.Expression;
import com.snapdeal.core.entity.ActivityType;

public class ActivityTypeSRO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2758096513009901652L;
	@Tag(1)
	private Integer           id;
	@Tag(2)
	private String            name;
	@Tag(3)
	private String            code;
	@Tag(4)
	private String            sdCash;
	@Tag(5)
	private boolean           async;
	@Tag(6)
	private boolean           enabled;
	@Tag(7)
	private Expression         expression;
	
	
	public ActivityTypeSRO(ActivityType activityType) {
	    this.setId(activityType.getId());
	    this.setName(activityType.getName());
	    this.setCode(activityType.getCode());
	    this.setSdCash(activityType.getCode());
	    this.setAsync(activityType.getAsync());
	    this.setEnabled(activityType.getEnabled());
	    this.setExpression(activityType.getExpression());
	}
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSdCash() {
		return sdCash;
	}
	public void setSdCash(String sdCash) {
		this.sdCash = sdCash;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    public Expression getExpression() {
        return expression;
    }
    public void setExpression(Expression expression) {
        this.expression = expression;
    }
	
	

}

 