### 配置多个内存授权码模式如下
 ```java
        clients.withClient("mengxuegu-pc") // 客户端id
                        // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
                        .secret(passwordEncoder.encode("mengxuegu-secret"))
                        // 资源id, 如商品资源
                        .resourceIds("product-server")
                        // 授权类型, 可同时支持多种授权类型
                        .authorizedGrantTypes("authorization_code", "password",
                                "implicit", "client_credentials", "refresh_token")
                        // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                        .scopes("all")
                        // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                        .autoApprove(false)
                        .redirectUris("http://www.mengxuegu.com/")
                        .and()
        
                        .withClient("mengxuegu-pc") // 客户端id
                        // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
                        .secret(passwordEncoder.encode("mengxuegu-secret"))
                        // 资源id, 如商品资源
                        .resourceIds("product-server")
                        // 授权类型, 可同时支持多种授权类型
                        .authorizedGrantTypes("authorization_code", "password",
                                "implicit", "client_credentials", "refresh_token")
                        // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                        .scopes("all")
                        // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                        .autoApprove(false)
                        .redirectUris("http://www.mengxuegu.com/")
                        .and(）
                        ..........

```

###  Spring Security 对 OAuth2 默认提供了可直接访问端点，即URL
* /oauth/authorize ：申请授权码 code, 涉及的类  AuthorizationEndpoint  测试：http://localhost:8090/auth/oauth/authorize?client_id=mengxuegu-pc&response_type=code
* /oauth/token ：获取令牌 token, 涉及的类  TokenEndpoint
* /oauth/check_token ：用于资源服务器请求端点来检查令牌是否有效, 涉及的类  CheckTokenEndpoint
* /oauth/confirm_access ：用户确认授权提交, 涉及的类  WhitelabelApprovalEndpoint
* /oauth/error ：授权服务错误信息, 涉及的类   WhitelabelErrorEndpoint
* /oauth/token_key ：提供公有密匙的端点，使用 JWT 令牌时会使用 , 涉及的类  TokenKeyEndpoint
                
### 四种模式--grant_type
  * 授权码模式 authorization_code   
  * 密码模式 password
  * 简化模式 Implicit 
    测试用URL：http://localhost:8090/auth/oauth/authorize?client_id=mengxuegu-pc&response_type=token
    返回直接在浏览器中显示：http://www.mengxuegu.com/#access_token=6f5e9dc4-1998-4293-831e-f2ea3c3bb7ae&token_type=bearer&expires_in=37632&scope=all
  * 客户端模式 Client
  * refresh_token 用来获取Access_token 获取新令牌报错java.lang.IllegalStateException: UserDetailsService is required. 
    需要实现UserDetailsService
           
###   端点 403 不允许访问 
* /oauth/authorize ：申请授权码 code, 涉及的类 AuthorizationEndpoint
* /oauth/token ：获取令牌 token, 涉及的类  TokenEndpoint
* /oauth/check_token ：用于资源服务器请求端点来检查令牌是否有效, 涉及的类  CheckTokenEndpoint
* /oauth/confirm_access ：用户确认授权提交, 涉及的类  WhitelabelApprovalEndpoint
* /oauth/error ：授权服务错误信息, 涉及的类   WhitelabelErrorEndpoint
* /oauth/token_key ：提供公有密匙的端点，使用 JWT 令牌时会使用 , 涉及的类  TokenKeyEndpoint       