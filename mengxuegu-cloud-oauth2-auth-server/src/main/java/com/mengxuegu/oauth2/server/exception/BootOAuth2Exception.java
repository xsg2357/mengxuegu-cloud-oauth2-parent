package com.mengxuegu.oauth2.server.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mengxuegu.oauth2.server.util.BootOAuthExceptionJacksonSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 定义自己的OAuth2Exception
 */
@JsonSerialize(using = BootOAuthExceptionJacksonSerializer.class)
public class BootOAuth2Exception  extends OAuth2Exception {

    public BootOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public BootOAuth2Exception(String msg) {
        super(msg);
    }

}
