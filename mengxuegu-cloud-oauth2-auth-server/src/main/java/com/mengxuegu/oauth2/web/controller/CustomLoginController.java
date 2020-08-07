package com.mengxuegu.oauth2.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.mengxuegu.base.result.MengxueguResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomLoginController {

    Logger logger = LoggerFactory.getLogger(getClass());
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

//    @Autowired
//    @Qualifier("messagingClientPasswordRestTemplate")
//    private OAuth2RestTemplate messagingClientPasswordRestTemplate;


    @RequestMapping("/login/page")
    public String toLogin() {

        return "login"; //classpath:\resources\templates\login.html
    }


    @Value("${messages.base-uri}")
    private String messagesBaseUri;

    @ResponseBody
    @PostMapping("/user/login")
    public MengxueguResult Login(@RequestParam("username") String username, @RequestParam("password") String password){

        // 构造 post的body内容（要post的内容，按需定义）
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.set("grant_type", "password");
        paramsMap.set("username", username);
        paramsMap.set("password", password);

        // 构造头部信息(若有需要)
        HttpHeaders headers = new HttpHeaders();
        String psKey = "mengxuegu-pc:mengxuegu-secret";
        headers.add("Authorization", "Basic "+ Base64.getEncoder().encodeToString(psKey.getBytes()));
        // 设置类型 "application/json;charset=UTF-8"
        // 设置类型 "application/x-www-form-urlencoded"
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 构造请求的实体。包含body和headers的内容
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramsMap, headers);

        // 声明 restTemplateAuth（用作请求）
        RestTemplate restTemplateAuth = new RestTemplate();
        // 进行请求，并返回数据
        String authInfo = restTemplateAuth.postForObject("http://localhost:8090/auth/oauth/token", request, String.class);
        Map<String,Object> resultBody = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(authInfo);
            resultBody.put("access_token",jsonObject.getString("access_token"));
            resultBody.put("token_type",jsonObject.getString("token_type"));
            resultBody.put("refresh_token",jsonObject.getString("refresh_token"));
            resultBody.put("expires_in",jsonObject.getString("expires_in"));
        } catch (JSONException e) {
            e.printStackTrace();
            return  MengxueguResult.ok(e.getMessage());
        }

        System.out.println(authInfo);

        return MengxueguResult.ok(resultBody);


    }


    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 获取验证码字符串
        String code = defaultKaptcha.createText();
        logger.info("生成的图形验证码是：" + code);
        // 2. 字符串把它放到session中
        request.getSession().setAttribute(SESSION_KEY, code);
        // 3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        // 4. 将验证码图片把它写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

    }


}
