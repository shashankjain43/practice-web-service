package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * This class related to a disabled URL which the running server must not
 * support.
 * 
 * @author ashish.saxena@snapdeal.com
 * 
 */
@Entity
@Table(name = "disabled_service_urls", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "disabled_url"))
public class DisabledURL implements Serializable, Cloneable {

	private static final long serialVersionUID = 7848814803401193001L;

	private Integer id;

	private String url;
	
	//private boolean available=false;

	// private final Date disabledSince;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DisabledURL() {
		super();
		
	}
	
	public DisabledURL(String url) {
		this.url = url;
		// this.disabledSince = disabledSince;
	}

	@Column(name = "disabled_url", unique = true,nullable = false, length = 100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// public Date getDisabledSince() {
	// return disabledSince;
	// }
//
//	@Column(name = "is_available", unique = true,nullable = false, length = 100)
//	public boolean isAvailable() {
//		return available;
//	}
//
//	public void setAvailable(boolean available) {
//		this.available = available;
//	}

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
		if (!(obj instanceof DisabledURL)) {
			return false;
		}
		DisabledURL other = (DisabledURL) obj;
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
		// Date disabledSInce = null;
		// if (this.disabledSince != null) {
		// disabledSInce = (Date) this.disabledSince.clone();
		// }
		return new DisabledURL(this.url);

	}

}
