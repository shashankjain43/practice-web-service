package com.snapdeal.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.dyuproject.protostuff.Tag;

/**
 *  Service response object for server behaviour context
 */

public class ServerBehaviourContextSRO implements Serializable, Cloneable{

	private static final long serialVersionUID = -4797966957243464597L;
	
	@Tag(1)
	private Integer id;

    @Tag(2)
	private String name;
    
    @Tag(3)
    private Set<DisabledURLSRO> disbaledURLs;


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
	
	
	
	public ServerBehaviourContextSRO() {
		super();
		
	}

	public ServerBehaviourContextSRO(Integer id, String name,
			Set<DisabledURLSRO> disbaledURLs) {
		super();
		this.id = id;
		this.name = name;
		this.disbaledURLs = disbaledURLs;
	}

	public ServerBehaviourContextSRO(Set<DisabledURLSRO> disbaledURLs) {
		super();
		this.disbaledURLs = disbaledURLs;
	}
	
	
	
	public ServerBehaviourContextSRO(String name) {
		super();
		this.name = name;
	}

	public ServerBehaviourContextSRO(String name,
			Set<DisabledURLSRO> disbaledURLs) {
		super();
		this.name = name;
		this.disbaledURLs = disbaledURLs;
	}

	public Set<DisabledURLSRO> getDisbaledURLs() {
		
		return disbaledURLs;
	
	}

	public void setDisbaledURLs(Set<DisabledURLSRO> disbaledURLs) {
		this.disbaledURLs = disbaledURLs;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Set<DisabledURLSRO> deepClonedDisbaledURLs = null;
		if (this.disbaledURLs != null) {
			deepClonedDisbaledURLs = new HashSet<DisabledURLSRO>();

			for (DisabledURLSRO disabledURL : this.disbaledURLs) {
				deepClonedDisbaledURLs.add((DisabledURLSRO) disabledURL.clone());
			}
		}

		return new ServerBehaviourContextSRO(this.id,this.name,deepClonedDisbaledURLs);

	}

	public void addDisbaledURLs(DisabledURLSRO disbaledURL) {
		if (this.disbaledURLs == null) {
			this.disbaledURLs = new HashSet<DisabledURLSRO>();
		}
		this.disbaledURLs.add(disbaledURL);
	}

	
	

}
