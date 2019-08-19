package com.snapdeal.ims.service.impl;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class AbstractUserServiceImplTest {

   @InjectMocks
   UserServiceImpl userService;

   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
   }
}
