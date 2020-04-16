package com.mengxuegu.oauth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mengxuegu.base.result.MengxueguResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtils {


    public  static  void writerError(MengxueguResult mengxueguResult, HttpServletResponse response) throws IOException {
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(mengxueguResult.getCode());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),mengxueguResult);
    }
}
