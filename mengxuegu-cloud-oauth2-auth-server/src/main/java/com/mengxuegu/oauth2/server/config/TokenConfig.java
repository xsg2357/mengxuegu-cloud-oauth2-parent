package com.mengxuegu.oauth2.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
public class TokenConfig {

    /**
     * Redis 管理令牌
     * 1. 启动 redis 服务器
     * 2. 添加 redis 相关依赖
     * 3. 添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
     */
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    /**
     * JDBC 管理令牌
     * 1. 创建相关数据表
     * 2. 添加 jdbc 相关依赖
     * 3. 配置数据源信息
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    // JWT 签名秘钥
    private static final String SIGNING_KEY = "mengxuegu-key";

    @Bean // 在 JwtAccessTokenConverter 中定义 Jwt 签名密码
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称密钥来签署我们的令牌，资源服务器也将使用此秘钥来验证准码性
//        converter.setSigningKey(SIGNING_KEY);
        // 读取 oauth2.jks 文件中的私钥，第2个参数是口令 oauth2
        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"),
                "oauth2".toCharArray());
        // 别名 oauth2
        converter.setKeyPair(keyFactory.getKeyPair("oauth2"));
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        // Redis 管理令牌
//        return new RedisTokenStore(redisConnectionFactory);
        // JDBC 管理令牌
//        return new JdbcTokenStore(dataSource());
        // JDBC 管理令牌
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}
