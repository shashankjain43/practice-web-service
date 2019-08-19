package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;



/**
 * The class represents the server behavior context.
 * 
 * @author ashish.saxena@snapdeal.com
 */


@Entity
@Table(name = "server_behaviour_context", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ServerBehaviourContext implements java.io.Serializable, Cloneable {

	
	private static final long serialVersionUID = -6290348531404480037L;

	private Integer id;

	@NotEmpty
	private String name;

	private Set<DisabledURL> disbaledURLs;

	public ServerBehaviourContext() {
		super();
	}

	public ServerBehaviourContext(Set<DisabledURL> disbaledURLs) {
		super();
		this.disbaledURLs = disbaledURLs;
	}

	public ServerBehaviourContext(String name) {
		this.name=name;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false,length=11)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "serverBehaviour_disabledUrl_mapping", catalog = "ums", joinColumns = { @JoinColumn(name = "serverBehaviourContext_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "disabled_url_id", referencedColumnName = "id", nullable = false) })
	public Set<DisabledURL> getDisbaledURLs() {
		
			return disbaledURLs;
		}


	public void setDisbaledURLs(Set<DisabledURL> disbaledURLs) {
		this.disbaledURLs = disbaledURLs;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Set<DisabledURL> deepClonedDisbaledURLs = null;
		if (this.disbaledURLs != null) {
			deepClonedDisbaledURLs = new HashSet<DisabledURL>();

			for (DisabledURL disabledURL : this.disbaledURLs) {
				deepClonedDisbaledURLs.add((DisabledURL) disabledURL.clone());
			}
		}

		return new ServerBehaviourContext(deepClonedDisbaledURLs);

	}

	public void addDisbaledURLs(DisabledURL disbaledURL) {
		if (this.disbaledURLs == null) {
			this.disbaledURLs = new HashSet<DisabledURL>();
		}
		this.disbaledURLs.add(disbaledURL);
	}

}
