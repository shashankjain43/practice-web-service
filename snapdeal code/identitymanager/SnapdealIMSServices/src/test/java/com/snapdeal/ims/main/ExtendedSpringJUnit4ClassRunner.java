package com.snapdeal.ims.main;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class ExtendedSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	public ExtendedSpringJUnit4ClassRunner(Class<?> clazz)
			throws InitializationError {
		super(clazz);
		System.setProperty("config.path", "src/main/resources/conf");
	}

}
