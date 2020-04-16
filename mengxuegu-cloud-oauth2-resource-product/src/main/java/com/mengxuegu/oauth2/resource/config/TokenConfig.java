package com.mengxuegu.oauth2.resource.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class TokenConfig {


    // JWT 签名秘钥
    private static final String SIGNING_KEY = "mengxuegu-key";

    @Bean // 在 JwtAccessTokenConverter 中定义 Jwt 签名密码
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称密钥来签署我们的令牌，资源服务器也将使用此秘钥来验证准码性
//        converter.setSigningKey(SIGNING_KEY);
        // 非对称加密：私钥
        ClassPathResource classPathResource = new ClassPathResource("public.txt");
        String publicKey = null;
        try {
//            publicKey = IOUtils.toString(classPathResource.getInputStream(), StandardCharsets.UTF_8);
            publicKey = IOUtils.toString(classPathResource.getInputStream(), "UTF-8");
            System.out.println("publicKey:" + publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}
