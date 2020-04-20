package com.mengxuegu.oauth2.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RequestMapping("/user")
@RestController
public class LoginController {

//    @Autowired
//    private CloudProperties clientProperties;

    @Autowired
    private OAuth2RestTemplate restTemplate;

//    @Value("${mengxuegu.cloud.cloudClientDetail.clientId}")
//    private String  client_id;
//    @Value("${mengxuegu.cloud.cloudClientDetail.clientSecret}")
//    private String  client_secret;


    @PostMapping("/login")
    public Object getLoginToken(@RequestParam String username, @RequestParam String password) throws Exception {


//        CloudClientDetails clientDetails = clientProperties.getCloudClientDetail();
//        String url = HttpUtils.getServerUrl(HttpUtils.getHttpServletRequest()) + "/oauth/token";
//        String url =  "http://localhost:7001/auth/oauth/token";
        String url =  "http://localhost:8090/auth/oauth/token";
        //Http Basic 验证
        String clientAndSecret =  "mengxuegu-pc:mengxuegu-secret" ;
        //这里需要注意为 Basic 而非 Bearer
        clientAndSecret = "Basic " + Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", clientAndSecret);
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", username);
        postParameters.add("password", password);
//        postParameters.add("client_id", clientDetails.getClientId());
//        postParameters.add("client_secret", clientDetails.getClientSecret());
//        System.out.println(client_id);
//        System.out.println(client_secret);
//        postParameters.add("client_id", "mengxuegu-pc");
//        postParameters.add("client_secret", "mengxuegu-secret");
        postParameters.add("grant_type", "password");
        // 添加参数区分,第三方登录
//        postParameters.add("login_type", type);
        // 使用客户端的请求头,发起请求
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        // 强制移除 原来的请求头,防止token失效
//        headers.remove(HttpHeaders.AUTHORIZATION);
//        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, httpHeaders);
//        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(postParameters, httpHeaders);
        //获取 Token
        ResponseEntity<OAuth2AccessToken> body = restTemplate.exchange(url, HttpMethod.POST, httpEntity, OAuth2AccessToken.class);
        OAuth2AccessToken oAuth2AccessToken = body.getBody();

        //        Map result = getToken(username, password, null,httpHeaders);
//        if (result.containsKey("access_token")) {
//            return ResultBody.ok().data(result);
//        } else {
//            return result;
//        }
        return MengxueguResult.ok(oAuth2AccessToken);
    }


}
