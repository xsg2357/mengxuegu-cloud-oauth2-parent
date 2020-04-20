package com.mengxuegu.oauth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mengxuegu.base.result.MengxueguResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * zuul错误过滤器
 */
@Slf4j
@Component
public class ZuulErrorFilter extends ZuulFilter {

    //    private AccessLogService accessLogService;
//
//    public ZuulErrorFilter(AccessLogService accessLogService) {
//        this.accessLogService = accessLogService;
//    }

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        // 代理错误日志记录
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        Exception exception = new Exception(ctx.getThrowable());
        Object rateLimitExceeded = ctx.get("rateLimitExceeded");
        if(rateLimitExceeded != null && !StringUtils.isBlank(rateLimitExceeded.toString())){
            exception = new Exception(HttpStatus.TOO_MANY_REQUESTS.name());
        }
//        accessLogService.sendLog(request, response,exception);
//        ResultBody resultBody = OpenGlobalExceptionHandler.resolveException(exception,request.getRequestURI());
//        WebUtils.writeJson(response, resultBody);

        MengxueguResult mengxueguResult = MengxueguResult.build(504,exception.getMessage());

        response.setContentType("application/json,charset=utf-8");
        response.setStatus(mengxueguResult.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getOutputStream(),mengxueguResult);
        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;
    }
}
