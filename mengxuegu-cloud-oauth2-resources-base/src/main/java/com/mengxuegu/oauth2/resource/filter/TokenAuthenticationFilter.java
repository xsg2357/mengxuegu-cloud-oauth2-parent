package com.mengxuegu.oauth2.resource.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微服务用户授权
 * 在微服务中接收到网关转发过来的 Token 后，需要我们构建一个 Authentication 对象来完成微服务认证与授权，
 * 这样这个微服务就可以根据用户所拥有的权限，来判断对应资源是否可以被用户访问
 */
@Component // 不可缺少
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private  Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. 从请求头中获取网关转发过来的明文 token
        String authToken = request.getHeader("auth-token");
        if (StringUtils.isNotEmpty(authToken)) {
            logger.error("商品资源服务器获取到的token值："+authToken);
            // Base64解码
            String authTokenJson = new String(Base64Utils.decodeFromString(authToken));
            // 转成json对象
            JSONObject jsonObject = JSON.parseObject(authTokenJson);
            // 用户权限 sys:user:add,sys:user:edit
            String authorities = ArrayUtils.toString(jsonObject.getJSONArray("authorities").toArray());
            // 构建一个Authentication对象, SpringSecurity 就会用于权限判断
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(
                    jsonObject.get("principal"),
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            // 请求详情
            authenticationToken.setDetails(jsonObject.get("details"));
            // 传递给安全上下文,这样服务可以进行获取对应数据、也会进行相应的权限判断
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
