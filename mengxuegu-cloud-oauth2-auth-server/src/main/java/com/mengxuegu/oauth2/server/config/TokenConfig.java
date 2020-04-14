package com.mengxuegu.oauth2.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class TokenConfig {

    /**
     * Redis 管理令牌
     * 1. 启动 redis 服务器
     * 2. 添加 redis 相关依赖
     * 3. 添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

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

    @Bean
    public TokenStore tokenStore() {
        // Redis 管理令牌
//        return new RedisTokenStore(redisConnectionFactory);
        // JDBC 管理令牌
        return new JdbcTokenStore(dataSource());
    }

}
