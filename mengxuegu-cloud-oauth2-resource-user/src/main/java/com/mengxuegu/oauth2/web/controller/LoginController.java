package com.mengxuegu.oauth2.web.controller;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.oauth2.resource.properties.CloudClientDetails;
import com.mengxuegu.oauth2.resource.properties.CloudProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RequestMapping("/user")
@RestController
public class LoginController {

    @Autowired
    private CloudProperties cloudProperties;

    @Autowired
    private OAuth2RestTemplate restTemplate;



    @PostMapping("/login")
    public Object getLoginToken(@RequestParam String username, @RequestParam String password) throws Exception {

        CloudClientDetails clientDetails = cloudProperties.getCloudClientDetail();
//        String url = HttpUtils.getServerUrl(HttpUtils.getHttpServletRequest()) + "/oauth/token";
        String url =  "http://localhost:8090/auth/oauth/token";
//        String url =  "http://localhost:7001/auth/oauth/token";

        // 构造 post的body内容（要post的内容，按需定义）
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.set("grant_type", "password");
        paramsMap.set("username", username);
        paramsMap.set("password", password);

        // 构造头部信息(若有需要)
        HttpHeaders headers = new HttpHeaders();
        String psKey = clientDetails.getClientId() +":"+clientDetails.getClientSecret();
        System.out.println(Base64.getEncoder().encodeToString(psKey.getBytes()));
        headers.add("Authorization", "Basic "+Base64.getEncoder().encodeToString(psKey.getBytes()));
        // 设置类型 "application/json;charset=UTF-8"
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造请求的实体。包含body和headers的内容
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramsMap, headers);

        // 声明 restTemplateAuth（用作请求）
//        RestTemplate restTemplateAuth = new RestTemplate();
        // 进行请求，并返回数据
        String authInfo = restTemplate.postForObject(url, request, String.class);

        return MengxueguResult.ok(authInfo);
    }


}
