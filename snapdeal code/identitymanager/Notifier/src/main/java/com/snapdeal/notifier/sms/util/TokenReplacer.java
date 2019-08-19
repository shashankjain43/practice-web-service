package com.snapdeal.notifier.sms.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.snapdeal.notifier.errorcodes.ExceptionCodes;
import com.snapdeal.notifier.exception.InternalServerException;

@Slf4j
@UtilityClass
public class TokenReplacer {

	public static String evaluate(Map<String, String> tokenmap,
			String templateString) throws InternalServerException {
		String template = templateString;
		VelocityContext context = new VelocityContext();
		for (Entry<String, String> entry : tokenmap.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		StringWriter outWriter = new StringWriter();
		try {
			Velocity.evaluate(context, outWriter, "", template);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new InternalServerException(
					ExceptionCodes.ERROR_PARSING_XML.errCode(),
					ExceptionCodes.ERROR_PARSING_XML.errMsg());
		}
		return outWriter.toString();
	}
}
