package com.xh.orderService.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author a3818
 * @author a3818
 */
@Configuration
@ComponentScan(basePackages = {"com.xh"})
@MapperScan("com.xh.orderService.mapper")
public class OrderConfig {
}
