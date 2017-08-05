package com.ngosdi.lawyer.app.web;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.Driver;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ngosdi.lawyer.jdbcdatasource.IDataSourceConfig;

@Configuration
@Profile("web_dev")
public class H2WebDataSourceConfig implements IDataSourceConfig {

	@Override
	public DataSource getDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:h2:~/lawyer_db");
		dataSource.setUsername("admin");
		dataSource.setPassword("admin");
		return dataSource;
	}

	@Override
	public Properties getServerConnectionProperties() {
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", H2Dialect.class.getName());
		return properties;
	}
}
