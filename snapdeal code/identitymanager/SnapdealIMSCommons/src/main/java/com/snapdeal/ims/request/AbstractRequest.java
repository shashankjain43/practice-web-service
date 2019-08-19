package com.snapdeal.ims.request;

import java.io.IOException;
import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.snapdeal.ims.dto.ClientConfig;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionConstants;



@Setter
@Getter
@EqualsAndHashCode
@ToString
@JsonPropertyOrder(alphabetic=true)
public abstract class AbstractRequest implements Serializable {

	private static final long serialVersionUID = 5849748447128588332L;

	@JsonIgnore
	@Size( max = 127 , message = IMSRequestExceptionConstants.MACHINE_IDENTIFIER_MAX_LENGTH)
	protected String userMachineIdentifier;
	@JsonIgnore
	@Size( max = 127 ,message = IMSRequestExceptionConstants.USER_AGENT_MAX_LENGTH)
	protected String userAgent;
	
	@JsonIgnore
	protected ClientConfig clientConfig;

	@JsonIgnore
	public String getHashGenerationString() {
		return getHashGenerationString(this);
	}

	/*
	 * Hash String calculation must be implemented by every request class.
	 * Strictly, Implementation of hash should not be changed. All field in hash
	 * generation should be in alphabetical order.
	 */
	public String getHashGenerationString(Object request) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonEquivalentStr;
		try {
		   mapper.setSerializationInclusion(Inclusion.NON_NULL);
			jsonEquivalentStr = mapper.writeValueAsString(request);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		return jsonEquivalentStr;
	}

}