package com.mengxuegu.oauth2.filter;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义AccessDeniedHandler 处理权限相关异常
 */
@Component("bootAccessDeniedHandler")
public class BootAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        HttpUtils.writerError(new MengxueguResult(HttpStatus.UNAUTHORIZED.value(),
                e.getMessage()),response);
    }
}
