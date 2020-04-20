package com.mengxuegu.oauth2.resources.config;

import com.mengxuegu.oauth2.resource.config.ResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class UserResourceServerConfig extends ResourceServerConfig {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/user/**").permitAll();
    }
}
