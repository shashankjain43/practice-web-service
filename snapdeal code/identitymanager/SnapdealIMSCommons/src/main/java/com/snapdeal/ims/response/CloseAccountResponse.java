package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response object used for fetching closeAccount details.
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CloseAccountResponse extends AbstractResponse {

	private static final long serialVersionUID = 8095844468545750000L;

	private boolean status;

}