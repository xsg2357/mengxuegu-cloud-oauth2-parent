package com.mengxuegu.oauth2.resource.exception;

import com.mengxuegu.base.result.MengxueguResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.NOT_EXTENDED;

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), NOT_EXTENDED);
    }

    @ExceptionHandler(value = Exception.class)
    public MengxueguResult jsonHandler(HttpServletRequest request, Exception e) throws Exception {

        return new MengxueguResult(404,"出错请求"+request.getRequestURI()+",原因"+e.getMessage());
    }


    @ExceptionHandler(CustomException.class)
    public MengxueguResult passwordMistake(CustomException e){
        log.error("错误信息："+e.getMessage());
        return new MengxueguResult(e.getCode(),e.getMsg());
    }

}
