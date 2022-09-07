package com.netflixclone.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DBConfig {

@Value("jdbc:mysql://netflixtest.cl2sgnskcm6e.ap-south-1.rds.amazonaws.com")
private  String jdbcUrl;

    @Value("dbUsername")
    private String username;

    @Value("Password")
    private String password;

    @Bean(destroyMethod="close")
    @Primary
    DataSource getDataSource(){
    BasicDataSource dataSource=new BasicDataSource();
    dataSource.setUrl(jdbcUrl);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    return dataSource;
}


}
