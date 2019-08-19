package com.snapdeal.opspanel.audit.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.audit.dao.GenericAuditDao;
import com.snapdeal.opspanel.audit.entity.AuditEntity;
import com.snapdeal.opspanel.rms.service.TokenService;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuditAspect {

	@Autowired
	GenericAuditDao auditDao;

	@Autowired
	HttpServletRequest servRequest; //Currently not used

	@Autowired
	private TokenService tokenService;
	
	final ObjectMapper mapper = new ObjectMapper(); 
	
	@Pointcut("@annotation(com.snapdeal.opspanel.audit.annotations.Audited)")
	public void annotatedMethodPointcut() {
		System.out.println("entering annotatedMethodPointcut in AuditAspect");
	}
	
	public Object filterJson(Set<String> skipPath, JSONObject jsonObject){
		
		for (String path : skipPath) {

			JSONObject referenceJSONObject = jsonObject;

			String[] keys_list = path.split("\\.");
			Boolean arrayFound = false;
			
			for (int step = 0; step < keys_list.length - 1;step++){
					String key = keys_list[step];
					Object object = referenceJSONObject.get(key);
					if(object instanceof JSONArray){
						if(referenceJSONObject.containsKey(key)) referenceJSONObject.remove(key);
						arrayFound = true;
						break;
					}else{
						referenceJSONObject = (JSONObject)object; 
					}
					
					if(referenceJSONObject == null){
						break;
					}
				}

			if(referenceJSONObject != null && arrayFound == false){
				String lastKey = keys_list[keys_list.length - 1];
				if(referenceJSONObject.containsKey(lastKey)) referenceJSONObject.remove(lastKey);
			}
		}

		return jsonObject;
	}

	public String getJsonValueAtPath(String path, JSONObject jsonObject){
		
		JSONObject referenceJSONObject = jsonObject;

		String[] keys_list = path.split("\\.");
		Boolean arrayFound = false;
			
		for (int step = 0; step < keys_list.length - 1;step++){
				String key = keys_list[step];
				Object object = referenceJSONObject.get(key);
				if(object instanceof JSONArray){
					arrayFound = true;
					break;
				}else{
					if(object instanceof JSONObject)
						referenceJSONObject = (JSONObject)object; 
					else{
						referenceJSONObject = null;
						break;
					}
				}
					
				if(referenceJSONObject == null){
					break;
				}
			}

		if(referenceJSONObject != null && arrayFound == false){
			String lastKey = keys_list[keys_list.length - 1];
			if(referenceJSONObject.containsKey(lastKey)) {
				if(referenceJSONObject.get(lastKey) != null)
					return referenceJSONObject.get(lastKey).toString();
				else
					return "not applicable";
			}
			else
				return "not applicable";
		}
		
		return "not applicable";
	}

/*	public Object filterJson(Set<String> skipKeys, JSONObject jsonObject, JSONArray jsonArray){

		if(jsonArray == null && jsonObject == null) return null;
		
		if(jsonArray != null){
			JSONArray filteredJSONArray = new JSONArray();
			
			for(int index = 0; index<jsonArray.size(); index++){

				Object newObject = jsonArray.get(index);
				if(newObject instanceof JSONArray){
					// It's an array
					Object filterResponse = filterJson(skipKeys,null,(JSONArray)newObject);				
					filteredJSONArray.add(index,filterResponse);
	
				}else if(newObject instanceof JSONObject){
					// It's an object
					Object filterResponse = filterJson(skipKeys,(JSONObject)newObject,null);
					filteredJSONArray.add(index,filterResponse);
				
				}else{
					// It's something else, like a string or number
					filteredJSONArray.add(newObject);
				}
			}
			
			return filteredJSONArray;			
		}
		else{
			JSONObject filteredJSONObject = new JSONObject();

			for(Object key:jsonObject.keySet()){
				
				if(!(skipKeys.contains((String)key))){
					Object newObject = jsonObject.get(key);
					if(newObject instanceof JSONArray){
						// It's an array
						Object filterResponse = filterJson(skipKeys,null,(JSONArray)newObject);				
						filteredJSONObject.put(key,filterResponse);
				
					}else if(newObject instanceof JSONObject){
						// It's an object
						Object filterResponse = filterJson(skipKeys,(JSONObject)newObject,null);
						filteredJSONObject.put(key,filterResponse);

					}else{
						filteredJSONObject.put(key,newObject);
						
					}
				}
				
			}

			return filteredJSONObject;
		}
			
	}
*/	


	void exceptionSetter(Exception ex, String alertLoggerMessage, AuditEntity auditData, String requestId, String IP, String emailId, String methodName, String requestData, String responseData, String timeStamp, String searchId, String reason, String context){

		String exceptionMessage = alertLoggerMessage + ex.getMessage() + " - Check Alert Log Table For Stack Trace";

		StringWriter sWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(sWriter));
		log.info(alertLoggerMessage + sWriter);
		
		auditData.setFailure(true);
		auditData.setException(exceptionMessage);

		log.info("requestId = " + requestId 
				+ "IP = " + IP
				+ "emailId = " + emailId 
				+ "failure = " + true 
				+ "exception = " + exceptionMessage
				+ "methodName = " + methodName
				+ "requestData = " + requestData 
				+ "responseData = " + responseData
				+ "timeStamp = " + timeStamp
				+ "searchId = " + searchId
				+ "reason = " + reason
				+ "context = " + context);
	}
	

	private JSONObject processRequest(Object[] request, Class<?>[] parameterTypes, String[] parameterNames) throws JsonGenerationException, JsonMappingException, ParseException, IOException, Exception{
		JSONObject object = new JSONObject();
		
		for(int parameterIndex = 0;parameterIndex < parameterTypes.length;parameterIndex++){
			if(parameterTypes[parameterIndex].getName().startsWith("org.springframework.web.multipart.MultipartFile") ||
			   parameterTypes[parameterIndex].getName().startsWith("javax.servlet.http.HttpServletRequest") ||
			   parameterTypes[parameterIndex].getName().startsWith("org.springframework.validation.BindingResult")){
				
				// skipping these parameter
			}else{
				String name = parameterNames[parameterIndex];
				Object value = request[parameterIndex];

				object.put(name, value);
			}
		}
		
		object = (JSONObject)new JSONParser().parse(new ObjectMapper().writeValueAsString(object));
		return object;
	}

	private JSONObject processResponse(Object response) throws JsonGenerationException, JsonMappingException, ParseException, IOException, Exception{

		JSONObject object = new JSONObject();
		object.put("response", response);

		object = (JSONObject)new JSONParser().parse(new ObjectMapper().writeValueAsString(object));
		return object;
	}

	@Around("annotatedMethodPointcut()")
	public Object makeAudit(ProceedingJoinPoint joinPoint) throws Throwable {
		
//		Logger alertLogger = LoggerFactory.getLogger("AlertLogger");
		
		Boolean logTrace = false; // True if you wish to trace logs at each step
		Boolean skipGet = false; // True if you wish to skip all the get methods

		if(logTrace) log.info("Getting httpServletRequest, request, parameterNames, requestMethods");

		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		Object[] request = joinPoint.getArgs();
		
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		String[] parameterNames = methodSignature.getParameterNames();
		
		RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();
		Audited auditedData = method.getAnnotation(Audited.class);

		Boolean isPost = false;

		for (int requestMethods_i = 0; requestMethods_i < requestMethods.length; requestMethods_i++) {
			String methodcode = requestMethods[requestMethods_i].toString().toLowerCase();
			if (methodcode.equals("post"))
				isPost = true;
		}

		if (!isPost && skipGet) {
			try {
				return joinPoint.proceed(request);
			} catch (Throwable ex) {
				log.info("Exception after calling the function " + ex.getMessage());
				throw ex;
			}
		}

		if(logTrace) log.info("Setting default values for audit attributes");

		AuditEntity auditData = new AuditEntity();
		auditData.setException("");
		auditData.setFailure(false);
				
		String emailId = null;
		String requestId = (String) httpServletRequest.getAttribute("requestId");
		String requestData = "";
		String responseData = "";
		String IP = "";
		String timeStamp = "";
		String methodName = "";
		String reason = "not applicable";
		String searchId = "not applicable";	
		String context = auditedData.context();
		int viewable = auditedData.viewable();
		
		try{
	
			IP = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (IP == null) {
				IP = httpServletRequest.getRemoteAddr();
			}
	
			timeStamp = new Timestamp(new Date().getTime()).toString();
	
			methodName = joinPoint.getSignature().getName();
			String token = httpServletRequest.getHeader("token");
			if(methodName.equals("login"))
			{
				Map<Object,Object> requestmap = mapper.convertValue(request[0], Map.class);
				emailId = (String) requestmap.get("username");
				
			} else if (methodName.equals("generateOTP")) {
				Map<Object,Object> requestmap = mapper.convertValue(request[0], Map.class);
				emailId = (String) requestmap.get("userName");
				token=null;
			}
			
			
			
			if(token != null)
				emailId = tokenService.getEmailFromToken(token);

			if(logTrace) log.info("handling the audit request");
			
			if (request.length > 0){
				
				MethodSignature signature = (MethodSignature)joinPoint.getSignature();
				Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();

				Set<String> skipRequestKeys = new HashSet<String>(Arrays.asList(auditedData.skipRequestKeys()));

				JSONObject object = processRequest(request,parameterTypes,parameterNames);
				
				if(!auditedData.reason().equals("not applicable")){
					reason = getJsonValueAtPath(auditedData.reason(),object);
				}
				
				if(!auditedData.searchId().equals("not applicable")){
					searchId = getJsonValueAtPath(auditedData.searchId(),object);
				}
				
				requestData = requestData + " " + filterJson(skipRequestKeys,object).toString();				
			}
		} 
		catch(Exception ex){

			String alertLoggerMessage = "Auditing Exception before calling proceed ";
			exceptionSetter(ex,alertLoggerMessage,auditData,requestId,IP,emailId,methodName,requestData,responseData,timeStamp,searchId,reason,context);
		}

		try{
			if(logTrace) log.info("makeAudit Status:Setting the audit data before function call");
			
			auditData.setEmailId(emailId);
			auditData.setIP(IP);
			auditData.setMethodName(methodName);
			auditData.setRequestId(requestId);
			auditData.setTimeStamp(timeStamp);
			auditData.setContext(context);
			auditData.setReason(reason);
			auditData.setSearchId(searchId);
			auditData.setViewable(viewable);
			
			String requestBlob = requestData.toString();
	
			auditData.setRequest(requestBlob);
			try {
				auditDao.setAuditEntry(auditData);
			} catch (Exception ex) {
				throw ex;
			}

			if(logTrace) log.info("makeAudit Status:Handling the response");

		}
		catch(Exception ex){

			String alertLoggerMessage = "Auditing Exception before calling proceed ";
			exceptionSetter(ex,alertLoggerMessage,auditData,requestId,IP,emailId,methodName,requestData,responseData,timeStamp,searchId,reason,context);
		}

		Object response = null;
		try {
			response = joinPoint.proceed(request);
		} catch (Exception ex) {
			try{
				StackTraceElement[] stackTrace = new StackTraceElement[0];
				ex.setStackTrace(stackTrace);

				auditData.setFailure(true);
				auditDao.updateAuditException(auditData);
				
				String exMessage = new ObjectMapper().writeValueAsString(ex);				

				auditData.setException(exMessage);	
				auditDao.updateAuditException(auditData);
	
				log.info("Exception on function call " + exMessage);
			} catch( Exception e ) {

				String alertLoggerMessage = "Auditing Exception while catching other exception from proceed ";
				exceptionSetter(ex,alertLoggerMessage,auditData,requestId,IP,emailId,methodName,requestData,responseData,timeStamp,searchId,reason,context);
			}
			throw ex;
		}

		try{
			Set<String> skipResponseKeys = new HashSet<String>(Arrays.asList(auditedData.skipResponseKeys()));
			
			JSONObject object = processResponse(response);
			responseData = filterJson(skipResponseKeys,object).toString();
			
			String responseBlob = responseData.toString();
			auditData.setResponse(responseBlob);
	
			if(logTrace) log.info("makeAudit Status:Setting the audit data after function call");
			try {
				auditDao.updateAuditResponse(auditData);
			} catch (Exception ex) {
				
				String alertLoggerMessage = "Auditing Exception while processing the response received from proceed";				
				exceptionSetter(ex,alertLoggerMessage,auditData,requestId,IP,emailId,methodName,requestData,responseData,timeStamp,searchId,reason,context);
				throw ex;
			}
		}
		catch(Exception ex){

			String alertLoggerMessage = "Exception after getting the response";
			exceptionSetter(ex,alertLoggerMessage,auditData,requestId,IP,emailId,methodName,requestData,responseData,timeStamp,searchId,reason,context);
		}

		return response;
	}

}