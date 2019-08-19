/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 18-Oct-2012
 *  @author ghanshyam
 */

package com.snapdeal.ums.creator;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class SchemaCreator implements InitializingBean {

	private String schema;
	private DataSource dataSource;

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void afterPropertiesSet() throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("CREATE SCHEMA " + schema + " AUTHORIZATION DBA");
		jdbcTemplate.execute("SET SCHEMA " + schema);

	}
}
