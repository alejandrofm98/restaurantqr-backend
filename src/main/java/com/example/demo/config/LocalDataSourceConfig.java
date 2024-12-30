package com.example.demo.config;

import static com.example.demo.config.DataSourceConfig.getDB;

import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!dev & !pro")
@Log4j2
@Configuration
public class LocalDataSourceConfig {

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.url.docker}")
  private String dockerUrl;


  @Bean
  public DataSource getDataSource() {
    return getDB(driverClassName, url, username, password, dockerUrl);
  }
}


