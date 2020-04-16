package com.mengxuegu.oauth2.sso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;


@Configuration
@EnableOAuth2Sso // 开启单点登录
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 当客户端要请求资源服务器中的资源时，我们需要带上令牌给资源服务器，由于我们使用了 @EnableOAuth2Sso
     * 注解，SpringBoot 会在请求上下文中添加一个 OAuth2ClientContext 对象，而我们只要在配置类中向容器中添加
     * 一个 OAuth2RestTemplate 对象，请求的资源服务器时就会把令牌带上转发过去。
     */
    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //首页所有人可以访问
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()// 其他要认证后才可以登录
             // 要退出登录 必须跳转到认证服务器退出和关闭域防护
            .and()
//            .logout().logoutSuccessUrl("http://localhost:8090/auth/logout") //认证服务器
            .logout().logoutSuccessUrl("http://localhost:7001/auth/logout") //网关
            .and().csrf().disable()
        ;
    }
}
