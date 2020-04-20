package com.mengxuegu.oauth2.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.oauth2.resource.properties.CloudClientDetails;
import com.mengxuegu.oauth2.resource.properties.CloudProperties;
import com.mengxuegu.oauth2.resource.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class LoginController {

    @Autowired
    private CloudProperties clientProperties;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @PostMapping("/login")
    public Object getLoginToken(@RequestParam String username, @RequestParam String password,
                                @RequestHeader HttpHeaders headers) throws Exception {


        CloudClientDetails clientDetails = clientProperties.getCloudClientDetails();
        String url = HttpUtils.getServerUrl(HttpUtils.getHttpServletRequest()) + "/oauth/token";
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", username);
        postParameters.add("password", password);
        postParameters.add("client_id", clientDetails.getClientId());
        postParameters.add("client_secret", clientDetails.getClientSecret());
        postParameters.add("grant_type", "password");
        // 添加参数区分,第三方登录
//        postParameters.add("login_type", type);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);

        //        Map result = getToken(username, password, null,httpHeaders);
//        if (result.containsKey("access_token")) {
//            return ResultBody.ok().data(result);
//        } else {
//            return result;
//        }
        return MengxueguResult.ok(result);
    }


}
