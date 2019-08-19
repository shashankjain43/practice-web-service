package com.snapdeal.ims.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to push update notification information to sns topic
 * 
 * @author abhishek.jajoria
 *
 */
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class SNSNotificationDTO {
	private String userID;
	private String event;
	private List<String> updatedFields;
	private List<String> oldValues;
	private List<String> newValues;
	private String updatedTime;

	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(this);
		return jsonInString;
	}
}
