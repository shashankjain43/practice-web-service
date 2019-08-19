/**
 * 
 */
package com.snapdeal.ums.core.entity.facebook;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.snapdeal.ums.core.entity.User;


/**
 * @author fanendra
 * 
 */
@Entity
@Table(name="facebook_user", catalog="ums")
public class FacebookUser {
	
	private Long 							id;
	/**
	 * Defines the fb id for the user. It should be stored so that later on it
	 * can be used to refresh the user's data.
	 */
	private Long 							facebookId;
	/**
	 * Email id for the user. Can be used for faster retrieval of user data.
	 */
	private String 							emailId;
	/**
	 * Snapdeal user mapping for this user.
	 */
	private User 							user;
	/**
	 * Facebook profile details for the user.
	 */
	private FacebookProfile 				fbProfile;
	/**
	 * Facebook pages that has been liked by the user.
	 */
	private Set<FacebookLike> 				fbLikes = new HashSet<FacebookLike>();
	/**
	 * Time of creation for this entity
	 */
	private Date							created;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="fb_id", unique=true)
	public Long getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}

	@Column(name="email", unique=true, nullable=false)
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
		for (FacebookLike fbLike : fbLikes) {
			fbLike.setEmail(emailId);
		}
	}

	@OneToOne(targetEntity=User.class)
	@JoinColumn(name="snapdeal_user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Embedded
	public FacebookProfile getFbProfile() {
		return fbProfile;
	}

	public void setFbProfile(FacebookProfile fbProfile) {
		this.fbProfile = fbProfile;
	}

	@OneToMany(targetEntity=FacebookLike.class, cascade=CascadeType.ALL, mappedBy="fbUser")
	public Set<FacebookLike> getFbLikes() {
		return fbLikes;
	}

	public void setFbLikes(Set<FacebookLike> fbLikes) {
		this.fbLikes = fbLikes;
	}
	
	@Column(name="created")
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public void setSnapdealUser(User user){
		this.user = user;
		for (FacebookLike fbLike : fbLikes) {
			fbLike.setUser(user);
		}
	}
	
}
