package com.snapdeal.ims.controller;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.main.ExtendedSpringJUnit4ClassRunner;
import com.snapdeal.ims.service.impl.CacheUpdater;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(ExtendedSpringJUnit4ClassRunner.class)
public class UtilityControllerTest {

	private MockMvc mockMvc;
	@InjectMocks
	private UtilityController utilityController;
	@Mock
	private CacheUpdater cacheUpdater;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		try {
			mockMvc = MockMvcBuilders.standaloneSetup(utilityController)
					.build();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReloadCacheSuccess() throws Exception {

		when(cacheUpdater.loadAll()).thenReturn("Cache Reloaded Successfully");

		MvcResult result = this.mockMvc
				.perform(
						get(RestURIConstants.UTILITY_URI
								+ RestURIConstants.CACHE_RELOAD))
				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();

		Assert.assertEquals("Unable to reload cache",
				"Cache Reloaded Successfully", content);
	}
}
