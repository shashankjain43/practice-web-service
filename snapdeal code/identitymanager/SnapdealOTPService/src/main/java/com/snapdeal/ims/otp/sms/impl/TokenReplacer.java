package com.snapdeal.ims.otp.sms.impl;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.springframework.beans.MethodInvocationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;

@Slf4j
@UtilityClass
@Deprecated
public class TokenReplacer {

	public static String evaluate(Map<String, String> tokenmap,
			String templateString) {
		String template = templateString;
		VelocityContext context = new VelocityContext();
		for (Entry<String, String> entry : tokenmap.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		StringWriter outWriter = new StringWriter();
		try {
			Velocity.evaluate(context, outWriter, "", template);
		} catch (ParseErrorException | MethodInvocationException
				| ResourceNotFoundException e) {
			log.error(e.getMessage());
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errCode(),
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errMsg());
		}
		return outWriter.toString();
	}
}
