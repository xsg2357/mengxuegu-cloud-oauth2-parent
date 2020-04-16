package com.mengxuegu.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer // 开启分布式服务注册中心
@SpringBootApplication
public class EurekaServer_6001 {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer_6001.class,args);
    }

}
