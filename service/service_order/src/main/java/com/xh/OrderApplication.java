package com.xh;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 *
 * nacos服务注册
 * 注意： 这个需要放在启动类上  不然报错
 */
@EnableDiscoveryClient
/**
 * Feign 服务调用
 */
@EnableFeignClients
/**
 * @author a3818
 */
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}
