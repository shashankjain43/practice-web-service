package com.snapdeal.ims.dao.impl;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.snapdeal.ims.dbmapper.IConfigDetailsMapper;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;

@ContextConfiguration(locations = { "classpath:/spring/application-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigDetailsDaoTest {

	@Autowired
	private IConfigDetailsMapper configDetailsMapper;

	@Autowired
	ConfigDetailsDao configDetailsDao;

	@Before
	public void insertTestData() {

		ConfigDetails configDetails1 = new ConfigDetails();
		configDetails1.setConfigKey("default.tokengeneration.service.version");
		configDetails1.setConfigType("global");
		configDetails1.setConfigValue("V01");
		configDetails1
				.setDescription("Token Generation Service version in use.");

		ConfigDetails configDetails2 = new ConfigDetails();
		configDetails2.setConfigKey("default.tokengeneration.service.version2");
		configDetails2.setConfigType("global");
		configDetails2.setConfigValue("V02");
		configDetails2
				.setDescription("Token Generation Service 2 version in use.");

		configDetailsMapper.createConfig(configDetails1);
		configDetailsMapper.createConfig(configDetails2);

	}

	@After
	public void cleanupTestData() {

		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version", "global");
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version2", "global");

	}

	@Test
	public void testGetAllConfigsSuccess() {

		List<ConfigDetails> configDetailsList = configDetailsDao
				.getAllConfigs();
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 2);
	}

	@Test
	public void testGetAllConfigsWithoutData() {

		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version", "global");
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version2", "global");
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getAllConfigs();
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 0);
	}

	@Test
	public void testGetConfigSuccess() {

		ConfigDetails configDetails = configDetailsDao.getConfig(
				"default.tokengeneration.service.version2", "global");
		Assert.assertEquals("Wrong Config selected",
				configDetails.getConfigValue(), "V02");
		Assert.assertEquals("Wrong Config selected",
				configDetails.getDescription(),
				"Token Generation Service 2 version in use.");

	}

	@Test
	public void testGetConfigByWrongKey() {

		ConfigDetails configDetails = configDetailsDao.getConfig(
				"default.tokengeneration.service.version1001", "global");
		Assert.assertNull("No Config selected", configDetails);

	}

	@Test
	public void testGetConfigByWrongType() {

		ConfigDetails configDetails = configDetailsDao.getConfig(
				"default.tokengeneration.service.version2", "notglobal");
		Assert.assertNull("No Config selected", configDetails);
	}

	@Test
	public void testGetConfigWithoutData() {

		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version", "global");
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version2", "global");
		ConfigDetails configDetails = configDetailsDao.getConfig(
				"default.tokengeneration.service.version2", "notglobal");
		Assert.assertNull("No Config selected", configDetails);
	}

	@Test
	public void testGetConfigsByKeySuccess() {
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByKey("default.tokengeneration.service.version2");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 1);
	}

	@Test
	public void testGetConfigsByKeyWithWrongKey() {
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByKey("default.tokengeneration.service.version20001");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 0);
	}

	@Test
	public void testGetConfigsByKeyWithoutData() {
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version", "global");
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version2", "global");
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByKey("default.tokengeneration.service.version2");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 0);
	}

	@Test
	public void testGetConfigsByTypeSuccess() {
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByType("global");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 2);
	}

	@Test
	public void testGetConfigsByTypeWithWrongType() {
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByType("notglobal");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 0);
	}

	@Test
	public void testGetConfigsByTypeWithoutData() {
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version", "global");
		configDetailsMapper.deleteConfig(
				"default.tokengeneration.service.version2", "global");
		List<ConfigDetails> configDetailsList = configDetailsDao
				.getConfigsByType("global");
		Assert.assertTrue("Config details are not fetched properly",
				configDetailsList.size() == 0);
	}

}
