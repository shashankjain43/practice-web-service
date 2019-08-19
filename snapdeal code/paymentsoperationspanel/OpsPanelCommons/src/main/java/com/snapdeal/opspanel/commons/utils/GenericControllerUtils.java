package com.snapdeal.opspanel.commons.utils;

import org.springframework.validation.BindingResult;

import com.snapdeal.opspanel.commons.entity.GenericResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericControllerUtils {

	public static void checkBindingResult( BindingResult result, String methodIdentifier ) throws OpsPanelException {
		if (result.hasErrors() && result.getAllErrors() != null) {
			log.error("Invalid Request Exception while " + methodIdentifier + result.getFieldError().getField() +result.getFieldError().getDefaultMessage() );
			throw new OpsPanelException( "MT-3179", result.getFieldError().getField() +result.getFieldError().getDefaultMessage() , "InvalidRequest: " + methodIdentifier );
		}
	}

	public static GenericResponse getGenericResponse( Object obj ) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData( obj );
		return genericResponse;
	}
}
