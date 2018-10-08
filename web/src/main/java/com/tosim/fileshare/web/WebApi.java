package com.tosim.fileshare.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.tosim.fileshare.common.mapper"})
@ComponentScan(basePackages = {"com.tosim.fileshare.web","com.tosim.fileshare.common"})
public class WebApi {
    public static void main(String[] args) {
        SpringApplication.run(WebApi.class, args);
    }
}
