package com.tosim.fileshare.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.tosim.fileshare.manager.mapper"})
@ComponentScan(basePackages = {"com.tosim.fileshare.manager","com.tosim.fileshare.common"})
public class Manager {
}
