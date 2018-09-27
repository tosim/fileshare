package com.tosim.fileshare.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.tosim.fileshare.web.mapper"})
@ComponentScan(basePackages = {"com.tosim.fileshare.web","com.tosim.fileshare"})
public class WebApi {
}
