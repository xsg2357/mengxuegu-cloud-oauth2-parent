package com.mengxuegu.oauth2.resources.config;

import com.mengxuegu.oauth2.resource.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(90) //项目中有多处extends WebSecurityConfigurerAdapter 需要声明Order
@Configuration
public class UserSecurityConfig extends SecurityConfig {

    /**
     * 当客户端要请求资源服务器中的资源时，我们需要带上令牌给资源服务器，由于我们使用了 @EnableOAuth2Sso
     * 注解，SpringBoot 会在请求上下文中添加一个 OAuth2ClientContext 对象，而我们只要在配置类中向容器中添加
     * 一个 OAuth2RestTemplate 对象，请求的资源服务器时就会把令牌带上转发过去。
     */

//    @Bean
//    public OAuth2RestTemplate restTemplate(@Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext,
//                                           OAuth2ProtectedResourceDetails details) {
//        return new OAuth2RestTemplate(details,oauth2ClientContext);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();

    }
}
