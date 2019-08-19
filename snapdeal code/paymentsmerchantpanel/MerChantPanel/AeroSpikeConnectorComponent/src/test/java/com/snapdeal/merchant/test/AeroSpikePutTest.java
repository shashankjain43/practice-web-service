package com.snapdeal.merchant.test;

import static org.junit.Assert.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.aero.test.init.AeroInitializer;
import com.snapdeal.merchant.dao.IClientDao;
import com.snapdeal.merchant.util.RequestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class AeroSpikePutTest {

	@Autowired
	private AeroInitializer init;

	@Autowired
	private IClientDao<Serializable> dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@PostConstruct
	public void initAeroSpike() throws MPAerospikeException {
		init.init();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// init.init();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		try {
			dao.put("cachekey", "cachevalue",
					RequestContext.EMAIL_TOKEN_LIST_CACHE);

		} catch (MPAerospikeException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
