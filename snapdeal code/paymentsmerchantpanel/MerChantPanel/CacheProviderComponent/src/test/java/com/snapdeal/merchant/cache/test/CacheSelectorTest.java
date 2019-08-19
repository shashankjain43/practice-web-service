package com.snapdeal.merchant.cache.test;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.merchant.cache.provider.ICacheProvider;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.util.RequestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class CacheSelectorTest {
	
	@Autowired
	private ICacheProvider provider;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws MerchantException {
		System.out.println("this is working");
		provider.put("testkey", "testvalue",RequestContext.EMAIL_TOKEN_LIST_CACHE);
		Serializable value = provider.get("testkey", RequestContext.EMAIL_TOKEN_LIST_CACHE);
		System.out.println(value);
	}

}
