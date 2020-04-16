package com.mengxuegu.oauth2.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求资源前, 先通过此过滤器进行用户令牌解析与校验、转发
 */
@Component //不可缺少
public class AuthenticationFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        // pre 请求路由前调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        //过滤器执行顺序, 数值越小优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // true 执行下面run方法
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        // 获取从Security上下文中获取认证信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        // JWT令牌有效，则会解析用户信息封装到OAuth2Authentication对象中
        if (!(authentication instanceof OAuth2Authentication)) {
            return null;
        }
        logger.error("网关获取到的认证对象："+authentication);
        // 只是用户名, 用户表中手机号，邮箱等都没有
        Object principal = authentication.getPrincipal();
        // 此用户拥有权限
        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();
        Set<String> authoritySet = AuthorityUtils.authorityListToSet(authorities);
        //请求详情
        Object details = authentication.getDetails();
        //封装传输的数据
        Map<String, Object> result = new HashMap<>();
        result.put("principal", principal);
        result.put("details", details);
        result.put("authorities", authoritySet);
        try {
            // 获取当前请求上下文
            RequestContext context = RequestContext.getCurrentContext();
            // 将用户信息和权限信息转成Json 在用Base64 进行编码
            String base64 = Base64Utils.encodeToString(JSON.toJSONString(result).getBytes("UTF-8"));
            // 添加到请求头
            context.addZuulRequestHeader("auth-token", base64);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
