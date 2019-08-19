package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity object representing Email Type
 * 
 * @author lovey
 * 
 **/

@Entity
@Table(name = "email_type", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "type_name"))
public class EmailTypeDO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3600549869835037887L;
	
	private Integer id;
	@NotEmpty
	private String typeName;
	
	//private String requestParameterClass;
	
    private List<EmailTemplate> listOfEmailTemplate= new ArrayList<EmailTemplate>();
    
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	  @Column(name = "type_name", unique = true, nullable = false, length = 100)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


//	@Column(name = "request_parameter_class", length = 45)
//	public String getRequestParameterClass() {
//		return requestParameterClass;
//	}
//
//	public void setRequestParameterClass(String requestParameterClass) {
//		this.requestParameterClass = requestParameterClass;
//	}


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "email_type_template_mapping", catalog = "ums", joinColumns = { 
			@JoinColumn(name = "email_type_id", referencedColumnName="id",nullable = false)}, 
			inverseJoinColumns = { @JoinColumn(name = "email_template_id", referencedColumnName="id",nullable = false)})
	
	/**
	 * NOTE- This service should not be used for loading email templates. Instead rely on the cache.
	 * @return list of email templates
	 */
	public List<EmailTemplate> getListOfEmailTemplate() {
		return listOfEmailTemplate;
	}

	public void setListOfEmailTemplate(List<EmailTemplate> listOfEmailTemplate) {
		this.listOfEmailTemplate = listOfEmailTemplate;
	}

	
}
