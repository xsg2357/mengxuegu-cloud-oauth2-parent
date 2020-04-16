package com.mengxuegu.oauth2.sso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableOAuth2Sso // 开启单点登录
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //首页所有人可以访问
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()// 其他要认证后才可以登录
             // 要退出登录 必须跳转到认证服务器退出和关闭域防护
            .and()
            .logout().logoutSuccessUrl("http://localhost:8090/auth/logout")
            .and().csrf().disable()
        ;
    }
}
