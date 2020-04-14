package com.mengxuegu.oauth2.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 资源服务器相关配置
 *
 */
@Configuration
@EnableResourceServer // 标识为资源服务器, 所有发往当前服务的请求，都会去请求头里找token，找不到或验证不通过不允许访问
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级权限控制
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //配置当前资源服务器的ID
    public static final String RESOURCE_ID = "product-server";



    /**
     * 当前资源服务器的一些配置, 如 资源服务器ID
     *
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)// 配置当前资源服务器的ID, 会在认证服务器验证(客户端表的配置了就可以访问这个服务)
                    .tokenServices(tokenService()); // 实现令牌服务, ResourceServerTokenServices实例
    }

    /**
     * 配置资源服务器如何验证token有效性
     * 1. DefaultTokenServices
     * 如果认证服务器和资源服务器同一服务时,则直接采用此默认服务验证即可
     * 2. RemoteTokenServices (当前采用这个)
     * 当认证服务器和资源服务器不是同一服务时, 要使用此服务去远程认证服务器验证
     */
    @Bean
    public ResourceServerTokenServices tokenService() {
        // 资源服务器去远程认证服务器验证 token 是否有效
        RemoteTokenServices service = new RemoteTokenServices();
        // 请求认证服务器验证URL，注意：默认这个端点是拒绝访问的，要设置认证后可访问
        service.setCheckTokenEndpointUrl("http://localhost:8090/auth/oauth/check_token");
        // 在认证服务器配置的客户端id
        service.setClientId("mengxuegu-pc");
        // 在认证服务器配置的客户端密码
        service.setClientSecret("mengxuegu-secret");
        return service;
    }


}
