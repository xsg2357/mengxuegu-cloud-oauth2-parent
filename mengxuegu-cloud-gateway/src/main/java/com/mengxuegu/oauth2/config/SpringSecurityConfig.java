package com.mengxuegu.oauth2.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 当前所有请求放行 交给资源配置类进行资源请求判断
     * 默认会拦截所有请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        // 让资源配置类处理请求，这里放行所有的即可
        http.authorizeRequests().anyRequest().permitAll();
    }
}
