package com.mengxuegu.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 资源服务器启动类
 */
@EnableEurekaClient // 表示它是一个Eureka的客户端，本服务启动后会自动注册进EurekaSever服务列表中
@SpringBootApplication
public class ProductResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductResourceApplication.class, args);
    }

}
