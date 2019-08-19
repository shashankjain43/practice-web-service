package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user_brand_preference", catalog = "ums")
public class UserBrand implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	
	private String brand_id;

	//private int user_id;
	 private User              user;

	public UserBrand() {
		super();

	}

	public UserBrand(String brand_id, User user) {
		super();
		this.brand_id = brand_id;
		this.user = user;
	}

	  @Id
	    @GeneratedValue(strategy = IDENTITY)
	    @Column(name = "id", unique = true, nullable = false)
	    public Integer getId() {
	        return this.id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }
	

	@Column(name = "brand_id", nullable = false)
	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    public User getUser() {
	        return this.user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", nullable = false)
//	public int getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}

}
