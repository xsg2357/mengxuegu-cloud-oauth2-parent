package com.mengxuegu.oauth2.server.config;

import com.mengxuegu.oauth2.server.mobile.SmsCodeSender;
import com.mengxuegu.oauth2.server.mobile.SmsSend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityBean {

    @Bean // 加密方式
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * @ConditionalOnMissingBean(SmsSend.class) 默认采用SmsCodeSender实例 ，
     * 但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
     * * 默认情况下，采用的是SmsCodeSender实例 ，
     * *   * 但是如果容器当中有其他的SmsSend类型的实例，
     * *   * 那当前的这个SmsCodeSender就失效 了
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

}
