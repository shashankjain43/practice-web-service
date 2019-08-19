package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.dto.UserDetailsDTO;

/**
 * Response object used for all the fetch calls for user details.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class GetUserResponse extends AbstractResponse {

	private static final long serialVersionUID = 8095844468545750000L;

	private UserDetailsDTO userDetails;
}