package com.snapdeal.opspanel.promotion.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.opspanel.promotion.Response.GenericResponse;


public class OPSUtils {

	public static String responseHeader="User Id, Amount , Campaign, Upload Time Stamp , Transaction TimeStamp , Transaction ID/ErrorCode, Details, Status\n";

	public static final String RMS_URI="/rms";
	
	public static final String CREATE_ROLE_URI="/createRole";
	
	public static final String UPDATE_ROLE="/updateRole";
	
	public static final String CREATE_USER="/createUser";
	
	public static final String UPDATE_USER="/updateUser";
	
	public static final String GET_USER_BY_USER_NAME="/getUserByUserName";
	
	public static final String GET_USER_BY_ID="/getUserById";
	
	public static final String GET_ALL_PERMISSIONS="/getAllPermissions";
	
	public static final String GET_ALL_PERMISSIONS_AS_APPS="/getAllPermissionsForApps";
	
	public static final String SOCIAL_LOGIN="/socialLogin";
	
	public static final String GET_ALL_USERS="/getAllUsers";
	
	public static final String GET_USERS_FOR_APP="/getusersforapp";
	
	public static final String LOGIN="/login";
	
	public static final String DELETE="/deleteUser";

	public static final String GENERATE_OTP="/generateOTP";

	public static final String RESEND_OTP="/resendOTP";

	public static final String VERIFY_OTP="/verifyOTP";

	public static final String VERIFY_CODE_AND_SET_PASSWORD = "/verifyCodeAndSetPassword";
	
	public static final String GET_USERS_BY_CRITERIA = "/getUsersByCriteria";

	public static GenericResponse getGenericResponse(Object response) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(response);
		return genericResponse;
	}
	
	public static Object constructMock(Class<?> objectClass){
		
		return constructObject(objectClass,null,null,false,0);
	}
	
	public static String getConcatenateEnum(Class<?> fieldClass){

/*		StringBuilder concatenatedEnums = new StringBuilder();
		Boolean first = true;
		
		for(Object obj :fieldClass.getEnumConstants()){
			if(!first) {
				concatenatedEnums.append(",");
				first = false;
			}
			concatenatedEnums.append(obj.toString());
		}
		
		return concatenatedEnums.toString();
*/
		return "ENUM-NA";
	}

	public static List<Field> getDeclaredFieldsWithExtendedClasses(List<Field> fields, Class<?> objectClass, Boolean extendedFieldSwitch, Integer level, Integer parentlevel){
		
		if(level == parentlevel && extendedFieldSwitch) 
			return fields;
		
		fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
		
		if(!extendedFieldSwitch)
			
			return fields;
		
		if(objectClass.getSuperclass() != null)
			fields = getDeclaredFieldsWithExtendedClasses(fields,objectClass.getSuperclass(),extendedFieldSwitch,level + 1,parentlevel);
		
		return fields;		
	}
	
	public static Object constructObject(Class<?> objectClass, ParameterizedType type, Field currentField, Boolean extendedFieldSwitch, Integer parentlevel) {

		/*
		 * Added support for List < List < List ... > > type variable
		 * 
		 * Syntax:
		 * constructObject(type,null,null,extendedFieldSwitch,parentlevel);
		 * where:
		 * 
		 * type == Class<?> (a variable with type class)
		 * 
		 * extendedFieldSwitch == Boolean (true or false)
		 * true for getting fields of extended parent classes till parentlevel  
		 * false for skipping extended parent classes
		 * 
		 * parentlevel == Integer
		 * the level of extended parents for fetching the fields of parent
		 * this is mandatory if extendedFieldSwitch is true
		 * 
		 * */
		
		Map<Object, Object> map = new HashMap<Object, Object>();

		if(type != null){
			Object parameterizedObject = type.getActualTypeArguments()[0];

			if(parameterizedObject instanceof ParameterizedType){
				List<Object> list = new ArrayList<Object>();
				list.add(constructObject(null,(ParameterizedType) parameterizedObject,null,extendedFieldSwitch,parentlevel));
				
				return list;
			}
			else{
				List<Object> list = new ArrayList<Object>();
				list.add(constructObject((Class<?>) parameterizedObject,null,null,extendedFieldSwitch,parentlevel));
				
				return list;
			}
		}
		
		// Check if the object is array
		if (Collection.class.isAssignableFrom(objectClass)) {
			
			ParameterizedType parameterizedType = (ParameterizedType) currentField.getGenericType();
			Object parameterizedObject = parameterizedType.getActualTypeArguments()[0];
			
			if(parameterizedObject instanceof ParameterizedType){
				List<Object> list = new ArrayList<Object>();
				list.add(constructObject(null,(ParameterizedType) parameterizedObject,null,extendedFieldSwitch,parentlevel));
				
				return list;
			}
			
			Class<?> innerType = (Class<?>) parameterizedObject;
			
			if(innerType.getProtectionDomain().getCodeSource() == null){
				
				List<Object> list = new ArrayList<Object>();
				list.add("LIST-NA");

				return list;
			}else if(innerType.isEnum()){

				List<Object> list = new ArrayList<Object>();
				list.add(getConcatenateEnum(innerType));

				return list;
			}else{
				
				List<Object> list = new ArrayList<Object>();
				list.add(constructObject(innerType,null,null,extendedFieldSwitch,parentlevel));

				return list;
			}
		}
		// Check if the object is user defined class
		else if (objectClass.getProtectionDomain().getCodeSource() != null) {

			if (!objectClass.isEnum()) {
				
				for (Field field : getDeclaredFieldsWithExtendedClasses(new ArrayList<Field>(),objectClass,extendedFieldSwitch,0,parentlevel)) {
					map.put(field.getName(), constructObject(field.getType(),null,field,extendedFieldSwitch,parentlevel));
				}
			} else {
				return getConcatenateEnum(objectClass);
			}
		}
		// Check If the object is primitive data type
		else {
			return objectClass.getSimpleName();
		}

		return map;
	}

}
