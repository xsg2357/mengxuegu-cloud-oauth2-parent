package com.mengxuegu.oauth2.resource.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtils {

    public static String getServerUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + request.getContextPath();
    }

    public static HttpServletRequest getHttpServletRequest() {
        try {
            return RequestContextHolder.getRequestAttributes() != null ?
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public  static  void writerError(MengxueguResult mengxueguResult, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(mengxueguResult.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),mengxueguResult);
    }

}
