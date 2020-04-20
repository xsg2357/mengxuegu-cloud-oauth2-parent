package com.mengxuegu.oauth2.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * 资源服务器相关配置
 */

@Configuration
public class ProductResourceServerConfig extends ResourceServerConfig {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // hasAuthority("sys:user:list") 这里权限一定要与校验的接口里面的权限一定要一致
        //        super.configure(http);
        http.sessionManagement()
                // SpringSecurity 不会创建也不会使用 HttpSession
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 资源授权规则
                .antMatchers("/product/**").hasAuthority("sys:user:list")
//                .antMatchers("/product/**").hasAuthority("product")
                // 所有的请求对应访问的用户都要有 all 范围权限
                .antMatchers("/**").access("#oauth2.hasScope('all')")
        ;
    }
}

