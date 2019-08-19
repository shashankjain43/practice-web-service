package com.snapdeal.payments.view.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Slf4j
@Configuration
@MapperScan("com.snapdeal.payments.view.mapper")
public class DatabaseConfiguration {

	@Value("#{'${snapdeal.customerview.database.url.list}'.split(',')}")
	private List<String> databaseUrlList;

	@Value("${snapdeal.customerview.database.driverClassName}")
	private String driverClassName;

	@Value("#{'${snapdeal.customerview.database.credentials.list}'.split(',')}")
	private List<String> credentials;

	@Value("${snapdeal.customerview.default.database.credential}")
	private String defaultCredentials;

	@Value("#{'${snapdeal.customerview.databases.shard.relation}'.split(',')}")
	private List<String> databasesWithShardCount;

	private String prefixForDatabaseMap = "db";

	@Value("${snapdeal.customerview.default.database.url}")
	private String defaultDatabaseUrl;

	@Value("${snapdeal.customerview.database.maxactive}")
	private Integer maxactive;

	@Value("${snapdeal.customerview.database.maxIdle}")
	private Integer maxIdle;

	@Value("${snapdeal.customerview.database.inititalsize}")
	private Integer inititialSize;

	@Value("${snapdeal.customerview.database.minIdle}")
	private Integer minIdle;

	@Value("${snapdeal.customerview.database.timebetweenevictionrunsmillis}")
	private Integer timeBetweEnevictionRunsMillis;

	@Value("${snapdeal.customerview.database.minevictableidletimemillis}")
	private Integer minEvictableIdleTimeMillis;

	@Value("${snapdeal.customerview.database.maxwait}")
	private Integer maxWait;

	private ApplicationContext appContext;

	@Bean(name = "dataSource")
	@Autowired
	@Primary
	@Scope(value = BeanDefinition.SCOPE_SINGLETON)
	public PaymentsViewRoutingDataSource getDataSourceList() {
		Map<Object, Object> databaseSouceMap = new HashMap<>();
		int count = 0;
		//============ hack for dropping customer_view db =============
		Integer shardCount_custDB = new Integer(databasesWithShardCount.get(0).split(":")[1]);
		//============ hack for dropping customer_view db =============
		BasicDataSource defaultTargetDataSource = null;
		for (String url : databaseUrlList) {
			try {
				BasicDataSource datasource = getDatasource();
				if (url.equals(defaultDatabaseUrl)) {
					defaultTargetDataSource = datasource;
				}
				datasource.setUrl(url);
				datasource.setUsername(credentials.get(count).split(":")[0]);
				datasource.setPassword(credentials.get(count).split(":")[1]);
				count++;
				//============ hack for dropping customer_view db =============
				int effCount=count+shardCount_custDB;
				//============ hack for dropping customer_view db =============
				databaseSouceMap.put(prefixForDatabaseMap + effCount, datasource);
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		PaymentsViewRoutingDataSource dataSouceRouter = new PaymentsViewRoutingDataSource();
		dataSouceRouter.setTargetDataSources(databaseSouceMap);
		if (defaultTargetDataSource == null) {
			defaultTargetDataSource = getDatasource();
			defaultTargetDataSource.setUrl(defaultDatabaseUrl);
			defaultTargetDataSource
					.setUsername(defaultCredentials.split(":")[0]);
			defaultTargetDataSource
					.setPassword(defaultCredentials.split(":")[1]);
		}
		dataSouceRouter.setDefaultTargetDataSource(defaultTargetDataSource);
		/*//print datasources map
		Set<Object> keys = databaseSouceMap.keySet();
		for (Iterator<Object> i = keys.iterator(); i.hasNext(); ) {
			String key =  (String) i.next();
			BasicDataSource value = (BasicDataSource) databaseSouceMap.get(key);
		    log.info("databaseSouceMap key: "+ key+"==="+value.getUrl());
		}
		//print datasources map*/
		return dataSouceRouter;

	}

	@Bean
	@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
	public BasicDataSource getDatasource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setInitialSize(inititialSize);
		dataSource.setMaxActive(maxactive);
		dataSource.setMaxWait(maxWait);
		dataSource.setMaxIdle(maxIdle);
		minIdle = 10;
		dataSource.setMinIdle(minIdle);
		dataSource
				.setTimeBetweenEvictionRunsMillis(timeBetweEnevictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

		return dataSource;
	}

	@Bean(name = "databaseShardRelationMap")
	public DataBaseShardRelationMap databaseShardMap() {

		DataBaseShardRelationMap shardRelation = new DataBaseShardRelationMap();
		Map<String, List<String>> databaseShardRelationMap = new HashMap<>();
		int count = 1;
		Integer shardCount_custDB=0;
		for (String databaseWithShardCount : databasesWithShardCount) {
			String databaseName = databaseWithShardCount.split(":")[0];
			//============ hack for dropping customer_view db =============
			if(databaseName.equalsIgnoreCase("customer_view")){
				shardCount_custDB= new Integer(databaseWithShardCount.split(":")[1]);
				count=count+shardCount_custDB;
				continue;
			}
			//============ hack for dropping customer_view db =============
			
			Integer shardCount = new Integer(
					databaseWithShardCount.split(":")[1]);
			if (shardCount <= 0){
				continue;
			}
			List<String> shardList = new ArrayList<>();
			while (shardCount-- > 0) {
				shardList.add(prefixForDatabaseMap + count++);
			}
			databaseShardRelationMap.put(databaseName, shardList);

		}
		shardRelation.setDataBaseShardRelationMap(databaseShardRelationMap);
		/*Set<String> keys = databaseShardRelationMap.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext(); ) {
			String key =  i.next();
		    List<String> value = databaseShardRelationMap.get(key);
		    log.info("key: "+ key+"==="+value);
		}*/
		return shardRelation;

	}

	@DependsOn(value = "sqlSessionFactoryMybatis")
	@Bean(name = "myBatisIMSDB")
	public SqlSessionTemplate getSqlSessionTemplat() throws Exception {
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(
				getSqlsSessionFactory(), ExecutorType.REUSE);
		return sqlSessionTemplate;
	}

	@DependsOn(value = "dataSource")
	@Bean(name = "sqlSessionFactoryMybatis")
	public SqlSessionFactory getSqlsSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		appContext = new ClassPathXmlApplicationContext();
		Resource resource = appContext
				.getResource("classpath:myBatis/sqlmap-config.xml");
		sqlSessionFactoryBean.setConfigLocation(resource);
		sqlSessionFactoryBean.setDataSource(getDataSourceList());
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		return sqlSessionFactory;
	}

	@DependsOn(value = "dataSource")
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager getDataSourceTransactionManager() {
		DataSourceTransactionManager dataSourcetransactionMannager = new DataSourceTransactionManager();
		dataSourcetransactionMannager.setDataSource(getDataSourceList());
		return dataSourcetransactionMannager;
	}

}
