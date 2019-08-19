package com.snapdeal.ims.request;

import javax.validation.GroupSequence;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.snapdeal.ims.enums.First;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder(alphabetic=true)
@GroupSequence(value={First.class,ConfigurationDetailsRequest.class})
public class ConfigurationDetailsRequest extends AbstractRequest {

	private static final long serialVersionUID = -7638503547164124543L;

	protected String configurationType;

}
