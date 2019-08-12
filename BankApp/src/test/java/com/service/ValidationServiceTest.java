package com.service;

import com.exception.InvalidInputException;
import com.request.CreateProfileRequest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest
public class ValidationServiceTest {

    private static ValidationService service;

    @BeforeClass
    public static void prep(){
        service = new ValidationService();
    }

    @Test(expected = InvalidInputException.class)
    public void testCreateProfileRequest(){
        CreateProfileRequest request = new CreateProfileRequest();
        //request.setDoy("12345");
        request.setName("Assam");
        //request.setMobile("1234567890");
        service.validateRequest(request);
        /*try{

        } catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
