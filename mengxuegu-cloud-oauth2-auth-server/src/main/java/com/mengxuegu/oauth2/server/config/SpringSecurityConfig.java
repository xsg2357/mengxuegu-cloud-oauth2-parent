package com.mengxuegu.oauth2.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 包含了@Configuration注解
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 在 SpringSecurityBean 添加到容器了
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserDetailsService customUserDetailsService;

    /**
     * 开启密码模式需要注入  AuthenticationManager
     * password 密码模式要使用此认证管理器
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(passwordEncoder.encode("1234"))
//                .authorities("product");
        auth.userDetailsService(customUserDetailsService);
    }
}
