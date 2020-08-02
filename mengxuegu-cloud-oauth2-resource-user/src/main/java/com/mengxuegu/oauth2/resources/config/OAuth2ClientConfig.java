package com.mengxuegu.oauth2.resources.config;

import com.mengxuegu.oauth2.resource.properties.CloudProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {

//    @Autowired
//    private CloudProperties cloudProperties;


//    @Bean
//    protected OAuth2ProtectedResourceDetails resource() {
//        ResourceOwnerPasswordResourceDetails resource;
//        resource = new ResourceOwnerPasswordResourceDetails();
//
//        List<String> scopes = new ArrayList<>(2);
//        scopes.add("write");
//        scopes.add("read");
//        resource.setAccessTokenUri("http://localhost:8090/auth/oauth/token");
//        resource.setClientId(cloudProperties.getCloudClientDetail().getClientId());
//        resource.setClientSecret(cloudProperties.getCloudClientDetail().getClientSecret());
//        resource.setGrantType("password");
//        resource.setScope(scopes);
//        resource.setUsername("**USERNAME**");
//        resource.setPassword("**PASSWORD**");
//        return resource;
//    }

//    @Bean
//    public OAuth2RestTemplate restTemplate() {
//        AccessTokenRequest atr = new DefaultAccessTokenRequest();
//        return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
//    }

    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }


}
