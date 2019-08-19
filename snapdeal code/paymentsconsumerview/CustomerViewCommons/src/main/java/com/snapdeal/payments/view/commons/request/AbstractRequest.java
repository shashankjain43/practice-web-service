package com.snapdeal.payments.view.commons.request;

import java.io.IOException;
import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapdeal.payments.view.commons.exception.constants.PaymentsViewExceptionConstants;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@JsonPropertyOrder(alphabetic=true)
@JsonIgnoreProperties({"userMachineIdentifier","userAgent","clientConfig"})
public abstract class AbstractRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@Size( max = 127 ,message = PaymentsViewExceptionConstants.USER_AGENT_MAX_LENGTH)
	private String userAgent ;
	@JsonIgnore
	@Size( max = 127 , message = PaymentsViewExceptionConstants.MACHINE_IDENTIFIER_MAX_LENGTH)
	protected String userMachineIdentifier;

	
	@JsonIgnore
	private ClientConfig clientConfig ;
	
	private String token ;
	
	@JsonIgnore
	public String getHashGenerationString() {
		return getHashGenerationString(this);
	}

	/*x
	 * Hash String calculation must be implemented by every request class.
	 * Strictly, Implementation of hash should not be changed. All field in hash
	 * generation should be in alphabetical order.
	 */
	public String getHashGenerationString(Object request) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonEquivalentStr;
		try {
		   mapper.setSerializationInclusion(Include.NON_NULL);
			jsonEquivalentStr = mapper.writeValueAsString(request);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return jsonEquivalentStr;
	}

}
