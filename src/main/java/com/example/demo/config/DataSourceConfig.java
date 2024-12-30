package com.example.demo.config;

import com.example.demo.exception.Exceptions;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev|pro")
@Log4j2
@Configuration
public class DataSourceConfig {

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  @Value("${private.key.ssh}")
  private String privateKeyPath;

  @Value("${ssh.username}")
  private String sshUsername;

  @Value("${ssh.host}")
  private String sshHost;

  @Value("${ssh.port}")
  private int sshPort;

  @Value("${database.port}")
  private int databasePort;

  @Value("${spring.datasource.url.docker}")
  private String dockerUrl;

  @PostConstruct
  public void setupSSHTunnel() {
    Session session;
    JSch jsch = new JSch();
    try {
      jsch.addIdentity(privateKeyPath);
      session = jsch.getSession(sshUsername, sshHost, sshPort);
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();
      session.setPortForwardingL(databasePort, sshHost, databasePort);

    } catch (JSchException e) {
      log.info("Fallo al crear el ssh tunnel para conectar a la bd");
    }
  }

  @Bean
  public DataSource getDataSource() {
    return getDB(driverClassName, url, username, password, dockerUrl);
  }

  static DataSource getDB(String driverClassName, String url, String username,
      String password, String dockerUrl) {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(driverClassName);
    dataSourceBuilder.url(url);
    dataSourceBuilder.username(username);
    dataSourceBuilder.password(password);
    try {
      dataSourceBuilder.build().getConnection();
    } catch (SQLException e) {
      log.error(e);
      dataSourceBuilder.url(dockerUrl);
    }
    return dataSourceBuilder.build();
  }
}


