/**
 * 
 */
package com.snapdeal.ums.core.entity.facebook;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Defines the <code>Facebook</code> profile information for the user.
 * 
 * @author fanendra
 *
 */
@Embeddable
public class FacebookProfile {
	/**
	 * User's first name on facebook.
	 */
	private String 						firstName;
	/**
	 * User's middle name on facebook.
	 */
	private String 						middleName;
	/**
	 * User's last name on facebook.
	 */
	private String 						lastName;
	/**
	 * User's about me text on facebook.
	 */
	private String 						aboutMe;

	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name="middle_name")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name="about_me")
	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
}
