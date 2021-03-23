package com.xh.uService.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xh.uService.mapper")
@ComponentScan(basePackages = {"com.xh"})
@EnableDiscoveryClient
public class UConfig {
}
