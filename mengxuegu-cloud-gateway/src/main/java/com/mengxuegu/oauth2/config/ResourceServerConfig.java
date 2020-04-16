package com.mengxuegu.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 当前类用于管理所有的资源：  认证服务器资源 产品服务器资源等
 * 网关也被认为是资源服务器, 因为要访问每个微服务资源，都要经过网关进行拦截判断是否允许访问。
 * 下面就要配置每个微服务的权限规则，实现客户端权限拦截，这样只有当客户端拥有了对应权限才可以访问到对应
 * 微服务。
 * 配置步骤：
 * 1. 认证服务器相关配置所有请求全部放行 .antMatchers("/**").permitAll()
 * 2. 商品资源服务器配置  /product/** 相关请求，对接入的客户端的 scope 要有 PRODUCT_API 范围
 * 技术总结：有多少个资源服务器 就要有多少个资源配置内部类
 */
@Configuration
public class ResourceServerConfig {

    //配置当前资源服务器的ID
    private static final String RESOURCE_ID = "product-server";

    @Autowired
    private TokenStore tokenStore;


    // 认证服务器资源拦截
    @Configuration
    @EnableResourceServer
    public class AuthResourceServerConfig extends ResourceServerConfigurerAdapter {
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 认证服务器相关资源全部放行，用于处理认证
            http.authorizeRequests().anyRequest().permitAll();
        }
    }


    // 商品资源服务器资源拦截
    @Configuration
    @EnableResourceServer
    public class ProductResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // 客户端要有 PRODUCT_API 范围才可访问
            http.authorizeRequests()
                    .antMatchers("/product/**")
                    .access("#oauth2.hasScope('PRODUCT_API')");
        }
    }

}
