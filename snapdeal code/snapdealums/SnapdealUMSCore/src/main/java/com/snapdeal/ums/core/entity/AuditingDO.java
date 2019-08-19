package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ums_auditing_data", catalog = "ums")
public class AuditingDO implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 6052815445629847092L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "class", nullable = false)
	private String className;

	@Column(name = "property", nullable = false)
	private String property;

	@Column(name = "value", nullable = true)
	private String value;
	
	@Column(name = "property_binding_reference", nullable = false)
	private int propertyBindingReference;
	
	

	@Column(name = "created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public AuditingDO(String className, String property, String value,int propertyBindingReference,
			Date created) {
		super();
		this.className = className;
		this.property = property;
		this.value = value;
		this.created = created;
		this.propertyBindingReference= propertyBindingReference;
	}

	public AuditingDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getPropertyBindingReference() {
		return propertyBindingReference;
	}

	public void setPropertyBindingReference(int propertyBindingReference) {
		this.propertyBindingReference = propertyBindingReference;
	}
	
	
	

}