package com.snapdeal.core;

import java.io.Serializable;

import javax.persistence.Column;

import com.dyuproject.protostuff.Tag;


public class DisabledURLSRO implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3993880131458208630L;
	
	@Tag(1)
    private Integer id;
	

	@Tag(2)
	private String url;
	
//	@Tag(3)
//	private boolean available;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
//	public boolean isAvailable() {
//		return available;
//	}
//
//	public void setAvailable(boolean available) {
//		this.available = available;
//	}
	
	public DisabledURLSRO(String url) {
		super();
		this.url = url;
	}

	public DisabledURLSRO() {
		
	}
	
	

	public DisabledURLSRO(Integer id, String url) {
		super();
		this.id = id;
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DisabledURLSRO)) {
			return false;
		}
		DisabledURLSRO other = (DisabledURLSRO) obj;
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
//		Date disabledSInce = null;
//		if (this.disabledSince != null) {
//			disabledSInce = (Date) this.disabledSince.clone();
//		}
		return new DisabledURLSRO(this.id,this.url);

	}
	

}
