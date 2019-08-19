/**
 * 
 */
package com.snapdeal.ums.core.entity.facebook;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.snapdeal.ums.core.entity.User;


/**
 * @author fanendra
 *
 */
@Entity
@Table(name="facebook_user_likes", catalog="ums")
public class FacebookLike {
	private Long 					id;
	
	//Add basic like information that can be used for profiling
	/**
	 * Page id (Facebook id) for the liked object
	 */
	private Long 					pageId;
	/**
	 * Name for the liked object
	 */
	private String 					pageName;
	/**
	 * Category(Facebook category) for the liked object
	 */
	private String 					pageCategory;
	/**
	 * facebook timestamp for the like event
	 */
	private	Date					likeTime;
	/**
	 * Facebook user details
	 */
	private FacebookUser 			fbUser;
	/**
	 * Snapdeal user details object
	 */
	private User 					user;
	/**
	 * Email for the user
	 */
	private String 					email;
	/**
	 * Timestamp for this event creation
	 */
	private Date					createdAt;
	/**
	 * Timestamp for this row updation(if any)
	 */
	private Date					updatedAt;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="page_id")
	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	@Column(name="page_name", length=255)
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Column(name="page_category")
	public String getPageCategory() {
		return pageCategory;
	}

	public void setPageCategory(String pageCategory) {
		this.pageCategory = pageCategory;
	}

	@Column(name="likedAt")
	public Date getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}

	@ManyToOne(targetEntity=FacebookUser.class)
	@JoinColumn(name="fb_user_id")
	public FacebookUser getFbUser() {
		return fbUser;
	}

	public void setFbUser(FacebookUser fbUser) {
		this.fbUser = fbUser;
	}

	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="created")
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Column(name="updated")
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
