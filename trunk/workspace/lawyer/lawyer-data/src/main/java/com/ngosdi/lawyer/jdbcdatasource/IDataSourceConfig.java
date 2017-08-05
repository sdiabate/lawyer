package com.ngosdi.lawyer.jdbcdatasource;

import java.util.Properties;

import javax.sql.DataSource;

public interface IDataSourceConfig {
	DataSource getDataSource();
	Properties getServerConnectionProperties();
}
