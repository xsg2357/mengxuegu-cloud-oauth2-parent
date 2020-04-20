package com.mengxuegu.oauth2.resources.config;

import com.mengxuegu.oauth2.resource.config.SecurityConfig;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Order(90) //项目中有多处extends WebSecurityConfigurerAdapte 需要声明Order
@EnableOAuth2Sso
@Configuration
public class UserSecurityConfig extends SecurityConfig {

    /**
     * 当客户端要请求资源服务器中的资源时，我们需要带上令牌给资源服务器，由于我们使用了 @EnableOAuth2Sso
     * 注解，SpringBoot 会在请求上下文中添加一个 OAuth2ClientContext 对象，而我们只要在配置类中向容器中添加
     * 一个 OAuth2RestTemplate 对象，请求的资源服务器时就会把令牌带上转发过去。
     */
    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }

}
