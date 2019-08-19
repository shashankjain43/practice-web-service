/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 24-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.bulkemail;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class EmailServiceProviderSRO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5675202378181819621L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private String name;
	@Tag(3)
	private String siteId;
	@Tag(4)
	private String implClass;
	
	public enum ESP {
	    
        LYRIS(1, "Lyris"), EPSILON(2, "Epsilon"), OCTANE(3, "Octane");

        private Integer id;
        private String  name;

        private ESP(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
	
	public EmailServiceProviderSRO() {
	    super();
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
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getImplClass() {
		return implClass;
	}
	public void setImplClass(String implClass) {
		this.implClass = implClass;
	}

	
}
