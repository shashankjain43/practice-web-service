/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 21, 2010
 *  @author rahul
 */
package com.snapdeal.ims.otp.sms.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.base.Preconditions;
import com.snapdeal.base.transport.http.HttpSender;
import com.snapdeal.base.transport.http.HttpTransportException;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.otp.sms.ISMSSender;
import com.snapdeal.ims.otp.util.OTPUtility;

/**
 * Only OTP notification implemented.
 *
 */

@Slf4j
@Service
@Deprecated
public class ValueFirstSMSSender implements ISMSSender {

	private String messageTemplate;

	@Autowired
	private OTPUtility otpUtlity;

	private DocumentBuilder docBuilder;

	@PostConstruct
	private void initialise() {
		this.docBuilder = docBuilderForParsing();
		this.messageTemplate = messageTemplateToString();

	}

	private DocumentBuilder docBuilderForParsing() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		// DocumentBuilder db = null;
		try {
			return docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			log.error("{}: XML parsing error ", e.getMessage());
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errCode(),
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errMsg());
		}
	}

	private String messageTemplateToString() {
		StringBuilder builder = new StringBuilder();
		Scanner scanner = null;
		try {
			// TODO Abhishek to improve this code
			// scanner = new Scanner(new
			// File("/valuefirstmessagetemplate.xml"));
			scanner = new Scanner(this.getClass().getResourceAsStream(
					"/valuefirstmessagetemplate.xml"));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
			}
			return builder.toString();
		} /*
		 * catch (FileNotFoundException e) { log.error("**** "+e.getMessage()+
		 * "****"); throw new IMSFileNotFoundException(
		 * IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errCode(),
		 * IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errMsg()); }
		 */finally {
			if (scanner != null)
				scanner.close();
		}
	}

	@Override
	public void send(String mobile, String message) {
		String response = null;

		Map<String, String> tokenmap = new HashMap<String, String>();
		tokenmap.put("messagetext", message);
		tokenmap.put("usermobile", mobile);
		message = TokenReplacer.evaluate(tokenmap, messageTemplate);

		HttpSender sender = new HttpSender("valueFirst");
		Map<String, String> params = new HashMap<String, String>();
		params.put("data", message);
		params.put("action", "send");
		try {
			response = sender.executePost(otpUtlity.getApiUrl(), params);
			log.info("Message: {}. Value first api response: {}", message,
					response);
		} catch (HttpTransportException e) {
			log.error("{}: unable to send sms :{}, to mobile:{}",
					this.getClass(), message, mobile);
				log.error("HTTP Transport Exception", e);
				throw new InternalServerException(
						IMSServiceExceptionCodes.SMS_NOTIFICATION_ERROR
								.errCode(),
								IMSServiceExceptionCodes.SMS_NOTIFICATION_ERROR
								.errMsg());
		}
		parseResponse(response);
	}

	private void parseResponse(String response) {

		// parse using builder to get DOM representation of the XML file

		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(response));

		Document dom = null;

		try {
			dom = docBuilder.parse(is);
		} catch (SAXException | IOException e) {
			log.error("SAXException: parsing error " + e.getMessage());
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errCode(),
					IMSInternalServerExceptionCodes.ERROR_PARSING_XML.errMsg());

		}

		Element elProp = dom.getDocumentElement();
		NodeList nodeList = elProp.getElementsByTagName("ERROR");
		Element el = null;

		try {
			Preconditions.checkNotNull(nodeList);
			el = (Element) nodeList.item(0);
			log.error("SMS notification error:{}", el.getAttribute("CODE"));
			throw new IMSServiceException(
					IMSServiceExceptionCodes.SMS_NOTIFICATION_ERROR.errCode(),
					IMSServiceExceptionCodes.SMS_NOTIFICATION_ERROR.errMsg());
		} catch (NullPointerException e) {
			log.info("SMS notification successfully sent");
		}
	}
}
