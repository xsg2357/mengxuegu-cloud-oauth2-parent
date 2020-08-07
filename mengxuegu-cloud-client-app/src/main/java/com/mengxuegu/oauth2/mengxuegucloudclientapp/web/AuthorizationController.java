package com.mengxuegu.oauth2.mengxuegucloudclientapp.web;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Controller
public class AuthorizationController {

//    @Value("${messages.base-uri}")
//    private String messagesBaseUri;
//
//    @Autowired
//    @Qualifier("messagingClientAuthCodeRestTemplate")
//    private OAuth2RestTemplate messagingClientAuthCodeRestTemplate;
//
//    @Autowired
//    @Qualifier("messagingClientClientCredsRestTemplate")
//    private OAuth2RestTemplate messagingClientClientCredsRestTemplate;
//
//    @Autowired
//    @Qualifier("messagingClientPasswordRestTemplate")
//    private OAuth2RestTemplate messagingClientPasswordRestTemplate;
//
//
//    @GetMapping(value = "/authorize", params = "grant_type=authorization_code")
//    public String authorization_code_grant(Model model) {
//        String[] messages = this.messagingClientAuthCodeRestTemplate.getForObject(this.messagesBaseUri, String[].class);
//        model.addAttribute("messages", messages);
//        return "index";
//    }
//
//    @GetMapping("/authorized")		// registered redirect_uri for authorization_code
//    public String authorized(Model model) {
//        String[] messages = this.messagingClientAuthCodeRestTemplate.getForObject(this.messagesBaseUri, String[].class);
//        model.addAttribute("messages", messages);
//        return "index";
//    }
//
//    @GetMapping(value = "/authorize", params = "grant_type=client_credentials")
//    public String client_credentials_grant(Model model) {
//        String[] messages = this.messagingClientClientCredsRestTemplate.getForObject(this.messagesBaseUri, String[].class);
//        model.addAttribute("messages", messages);
//        return "index";
//    }
//
//    @PostMapping(value = "/authorize", params = "grant_type=password")
//    public String password_grant(Model model, HttpServletRequest request) {
//        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
//                (ResourceOwnerPasswordResourceDetails) this.messagingClientPasswordRestTemplate.getResource();
//        passwordResourceDetails.setUsername(request.getParameter("username"));
//        passwordResourceDetails.setPassword(request.getParameter("password"));
//
//        String[] messages = this.messagingClientPasswordRestTemplate.getForObject(this.messagesBaseUri, String[].class);
//        model.addAttribute("messages", messages);
//
//        // Never store the user's credentials
//        passwordResourceDetails.setUsername(null);
//        passwordResourceDetails.setPassword(null);
//
//        return "index";
//    }
//
//
//    @PostMapping(value = "/login", params = "grant_type=password")
//    public String passwordGrant( HttpServletRequest request) {
//        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
//                (ResourceOwnerPasswordResourceDetails) this.messagingClientPasswordRestTemplate.getResource();
//        passwordResourceDetails.setUsername(request.getParameter("username"));
//        passwordResourceDetails.setPassword(request.getParameter("password"));
//
//        String[] messages = this.messagingClientPasswordRestTemplate.getForObject(this.messagesBaseUri, String[].class);
//        System.out.println(messages);
//        //        model.addAttribute("messages", messages);
//
//        // Never store the user's credentials
////        passwordResourceDetails.setUsername(null);
////        passwordResourceDetails.setPassword(null);
//
//        return "index";
//    }

    @ResponseBody
    @PostMapping("/user/login")
    public  MengxueguResult Login(@RequestParam("username") String username, @RequestParam("password") String password){

        // 构造 post的body内容（要post的内容，按需定义）
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.set("grant_type", "password");
        paramsMap.set("username", username);
        paramsMap.set("password", password);

//        ResourceOwnerPasswordResourceDetails passwordResourceDetails =
//                (ResourceOwnerPasswordResourceDetails) this.messagingClientPasswordRestTemplate.getResource();
//        passwordResourceDetails.setUsername(username);
//        passwordResourceDetails.setPassword(password);
//
//        String[] messages = this.messagingClientPasswordRestTemplate.getForObject(this.messagesBaseUri, String[].class);

        // 构造头部信息(若有需要)
        HttpHeaders headers = new HttpHeaders();
        String psKey = "mengxuegu-pc:mengxuegu-secret";
        headers.add("Authorization", "Basic "+ Base64.getEncoder().encodeToString(psKey.getBytes()));
        // 设置类型 "application/json;charset=UTF-8"
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 构造请求的实体。包含body和headers的内容
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(paramsMap, headers);

        // 声明 restTemplateAuth（用作请求）
           RestTemplate restTemplateAuth = new RestTemplate();
        // 进行请求，并返回数据
        String authInfo = restTemplateAuth.postForObject("http://localhost:8090/auth/oauth/token", request, String.class);


        return MengxueguResult.ok(authInfo);


    }

}
