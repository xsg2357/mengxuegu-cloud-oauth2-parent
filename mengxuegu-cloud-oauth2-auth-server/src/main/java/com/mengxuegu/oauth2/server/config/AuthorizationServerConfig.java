package com.mengxuegu.oauth2.server.config;

import com.mengxuegu.oauth2.server.filter.BootBasicAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;


@Configuration
@EnableAuthorizationServer // 开启认证服务器功能
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired // 在 SpringSecurityBean 添加到容器了
    private PasswordEncoder passwordEncoder;

    @Autowired // SpringSecurityConfig添加到容器中了
    private AuthenticationManager authenticationManager;


    @Autowired // 刷新令牌
    private UserDetailsService customUserDetailsService;


    @Autowired // 令牌管理策略
    private TokenStore tokenStore;

    @Autowired // 数据源
    private DataSource dataSource;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired //自定义异常节点
    private WebResponseExceptionTranslator bootWebResponseExceptionTranslator;

    @Autowired //不能识别网页的过滤器
    private BootBasicAuthenticationFilter bootBasicAuthenticationFilter;

    @Bean // 注意:方法名为clientDetailsService 需要放在dataSource之后
    public ClientDetailsService jdbcClientDetailsService() {
        // 使用 JDBC 方式管理客户端信息
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 授权码管理策略
     */
    public AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        // JDBC方式保存授权码到 oauth_code 表中,
        // 意义不大，因为获取一次令牌后，授权码就失效了
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 配置被允许访问此认证服务器的客户端详情信息
     * * 方式1：内存方式管理
     * * 方式2：数据库管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);

        // 使用内存方式
        /*clients.inMemory()
                .withClient("mengxuegu-pc") // 客户端id
                // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
                .secret(passwordEncoder.encode("mengxuegu-secret"))
                // 资源id, 如商品资源
                .resourceIds("product-server")
                // 授权类型, 可同时支持多种授权类型
                .authorizedGrantTypes("authorization_code", "password",
                        "implicit", "client_credentials", "refresh_token")
//                .authorizedGrantTypes("authorization_code", "password",
//                        "implicit", "client_credentials") // 不指定refresh_token 密码模式、授权码模式返回体不会返回refresh_token值字段
                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                .autoApprove(false)
                .accessTokenValiditySeconds(60 * 60 * 8)// 访问令牌有效时长 默认 12小时
                .refreshTokenValiditySeconds(60 * 60 * 24 * 60) // 刷新令牌有效时长 默认30天
                .redirectUris("http://www.mengxuegu.com/"); // 客户端回调地址*/

        clients.withClientDetails(jdbcClientDetailsService());


    }




    /**
     * 认证服务器端点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
        // 密码模式要设置认证管理器
        endpoints.authenticationManager(authenticationManager);
        // TokenEndpoint  : Handling error: InvalidRequestException, Missing grant type
        // 刷新令牌的时候需要使用
        endpoints.userDetailsService(customUserDetailsService);

        // 处理 ExceptionTranslationFilter 抛出的异常
        endpoints.exceptionTranslator(bootWebResponseExceptionTranslator);
        // 令牌redis策略管理
//        endpoints.tokenStore(tokenStore);
        // 令牌策略管理 jwt转换器
        endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter);



        //// 授权码管理策略，针对授权码模式有效，会将授权码放到 auth_code 表，授权后就会删除它
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());



    }


    /**
     * 令牌端点安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //
        security.addTokenEndpointAuthenticationFilter(bootBasicAuthenticationFilter);
        // 允许表单登录
        security.allowFormAuthenticationForClients();
        //        super.configure(security);
        // 所有人可访问 /oauth/token_key 后面要获取公钥, 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 认证后可访问 /oauth/check_token , 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");

    }
}
