package com.snapdeal.merchant.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.aero.test.init.AeroInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class AeroConnectTest {
	
	@Autowired
	AeroInitializer init;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() throws MPAerospikeException {
		
		try {
			init.init();
		}catch (MPAerospikeException ex) {
			fail("failed to connect");
			throw ex;
		}
	}

}
