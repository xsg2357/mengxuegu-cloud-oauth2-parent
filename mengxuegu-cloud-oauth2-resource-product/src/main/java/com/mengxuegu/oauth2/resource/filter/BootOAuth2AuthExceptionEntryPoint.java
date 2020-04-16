package com.mengxuegu.oauth2.resource.filter;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.oauth2.resource.utils.HttpUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口处理Token相关的异常
 */
@Component("bootOAuth2AuthExceptionEntryPoint")
public class BootOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        HttpUtils.writerError(new MengxueguResult(HttpStatus.UNAUTHORIZED.value(),
                e.getMessage()),response);
    }
}
