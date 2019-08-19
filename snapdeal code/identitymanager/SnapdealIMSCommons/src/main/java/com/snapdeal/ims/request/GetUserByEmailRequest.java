/**
 * 
 */
package com.snapdeal.ims.request;

import com.snapdeal.ims.validator.annotation.Email;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author shachi
 *
 */

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonPropertyOrder(alphabetic=true)
public class GetUserByEmailRequest extends AbstractRequest {

	private static final long serialVersionUID = -5406360913076159190L;
	@Email
	private String emailId;

}
