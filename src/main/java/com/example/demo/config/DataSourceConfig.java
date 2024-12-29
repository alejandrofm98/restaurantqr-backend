package com.example.demo.config;

import com.example.demo.exception.Exceptions;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev|pro")
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
      throw new Exceptions("Fallo al crear el ssh tunnel para conectar a la bd");
    }
  }

  @Bean
  public DataSource getDataSource() {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(driverClassName);
    dataSourceBuilder.url(url);
    dataSourceBuilder.username(username);
    dataSourceBuilder.password(password);
    return dataSourceBuilder.build();
  }
}


