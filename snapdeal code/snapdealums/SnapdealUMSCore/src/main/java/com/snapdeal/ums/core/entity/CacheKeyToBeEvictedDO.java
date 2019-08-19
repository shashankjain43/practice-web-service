package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity object represents a key to be evicted from Cache
 * 
 * @author ashish
 * 
 */

@Entity
@Table(name = "cache_key_to_be_evicted", catalog = "ums")
public class CacheKeyToBeEvictedDO implements Serializable {

	private static final long serialVersionUID = 7100396703499877865L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "namespace", nullable = false)
	private String namespace;

	@Column(name = "set_name", nullable = false)
	private String set;

	@Column(name = "key_to_be_evicted", nullable = false)
	private String key;

	@Column(name = "created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CacheKeyToBeEvictedDO(String namespace, String set, String key,
			Date created) {
		super();
		this.namespace = namespace;
		this.set = set;
		this.key = key;
		this.created = created;
	}

	public CacheKeyToBeEvictedDO() {
		super();
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
