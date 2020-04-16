package com.mengxuegu.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableZuulProxy //开启zuul 网关的功能
@EnableEurekaClient // 分布式客户端
@SpringBootApplication
public class ZuulServer_7001 {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServer_7001.class,args);
    }

}
