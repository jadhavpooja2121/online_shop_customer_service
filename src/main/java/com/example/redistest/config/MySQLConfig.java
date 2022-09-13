package com.example.redistest.config;

import javax.annotation.PostConstruct;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.fantasy.clash.framework.configuration.Configurator;
import com.fantasy.clash.framework.sql.SQLConfiguration;
import com.example.redistest.constants.PropertyConstants;
import com.example.redistest.mapper.OnlineUserMapper;
import com.example.redistest.mapper.UserMapper;
import com.example.redistest.constants.*;;
@Configuration
public class MySQLConfig {
  private Logger logger = LoggerFactory.getLogger(MySQLConfig.class);

  @Autowired
  private SQLConfiguration sqlConfiguration;

  @Autowired
  private Configurator configurator;

  private SqlSessionTemplate sqlSessionTemplateMaster;
  private SqlSessionTemplate sqlSessionTemplateSlave;

  private Class<?>[] mappers = {UserMapper.class, OnlineUserMapper.class};

  @PostConstruct
  public void init() throws Exception {
    logger.info("MySQL Configuration");
    sqlSessionTemplateMaster = sqlConfiguration.getSessionTemp(MySQLConstants.MYSQL_MASTER_ALIAS,
        configurator.getString(PropertyConstants.MYSQL_DRIVER_NAME),
        configurator.getString(PropertyConstants.MYSQL_MASTER_USERNAME),
        configurator.getString(PropertyConstants.MYSQL_MASTER_PASSWORD),
        configurator.getString(PropertyConstants.MYSQL_MASTER_DB_URI),
        configurator.getInt(PropertyConstants.MYSQL_MASTER_MAX_ACTIVE_CONN,
            PropertyConstants.DEFAULT_MYSQL_MASTER_MAX_ACTIVE_CONN),
        configurator.getInt(PropertyConstants.MYSQL_MASTER_MAX_IDLE_CONN,
            PropertyConstants.DEFAULT_MYSQL_MASTER_MAX_ACTIVE_CONN));
    // Logging for Master
    logger.info("username: {}, active connections: {}, idle connections: {})",
        configurator.getString(PropertyConstants.MYSQL_MASTER_USERNAME),
        configurator.getString(PropertyConstants.MYSQL_MASTER_MAX_ACTIVE_CONN),
        configurator.getString(PropertyConstants.MYSQL_MASTER_MAX_IDLE_CONN));

    registerMappers(sqlSessionTemplateMaster);
    logger.info("Mappers registered for Master");
    sqlSessionTemplateSlave = sqlConfiguration.getSessionTemp(MySQLConstants.MYSQL_MASTER_ALIAS,
        configurator.getString(PropertyConstants.MYSQL_DRIVER_NAME),
        configurator.getString(PropertyConstants.MYSQL_SLAVE_USERNAME),
        configurator.getString(PropertyConstants.MYSQL_SLAVE_PASSWORD),
        configurator.getString(PropertyConstants.MYSQL_SLAVE_DB_URI),
        configurator.getInt(PropertyConstants.MYSQL_SLAVE_MAX_ACTIVE_CONN,
            PropertyConstants.DEFAULT_MYSQL_MASTER_MAX_ACTIVE_CONN),
        configurator.getInt(PropertyConstants.MYSQL_SLAVE_MAX_IDLE_CONN,
            PropertyConstants.DEFAULT_MYSQL_MASTER_MAX_ACTIVE_CONN));
    // Logging for Slave
    logger.info("username: {}, active connections: {}, idle connections: {})",
        configurator.getString(PropertyConstants.MYSQL_SLAVE_USERNAME),
        configurator.getString(PropertyConstants.MYSQL_SLAVE_MAX_ACTIVE_CONN),
        configurator.getString(PropertyConstants.MYSQL_SLAVE_MAX_IDLE_CONN));
    registerMappers(sqlSessionTemplateSlave);
    logger.info("Mappers registered for Slave.");
  }

  private void registerMappers(SqlSessionTemplate sqlSessionTemplate) {
    // Parse through the mapper classes and add it to Session Template
    for (Class<?> cls : mappers) {
      sqlSessionTemplate.getConfiguration().addMapper(cls);
    }
  }

  @Bean(name = "sqlSessionTemplateMaster")
  public SqlSessionTemplate getSqlSessionTemplateMaster() {
    return sqlSessionTemplateMaster;
  }

  @Bean(name = "sqlSessionTemplateSlave")
  public SqlSessionTemplate getSqlSessionTemplateSlave() {
    return sqlSessionTemplateSlave;
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(
        sqlConfiguration.getDataSource(MySQLConstants.MYSQL_MASTER_ALIAS));
  }
}
