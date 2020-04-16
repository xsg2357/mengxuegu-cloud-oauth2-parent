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

### 生成密钥证书文件
  keytool -genkeypair -alias oauth2 -keyalg RSA -keypass oauth2 -keystore oauth2.jks -storepass oauth2
  
### 获取公钥命令
  keytool -list -rfc --keystore oauth2.jks | openssl x509 -inform pem -pubkey  
  
### 允许表单登录源码分析
获取token时需要客户端在Form表单中带上客户端的client_id和client_secret，
此时的ClientCredentialsTokenEndpointFilter 会去检查client_id和client_secret的合法性，
如果不合法抛出的异常由其自己在filter内部实例化的OAuth2AuthenticationEntryPoint来处理该异常，
所以上面定义的BootOAuth2WebResponseExceptionTranslator 捕获不到该异常；看如下源码分析
```java
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {
	
	// filter
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult;

		try {
			// 调用子类的 attemptAuthentication(request, response) 方法，这里是调用ClientCredentialsTokenEndpointFilter 的attemptAuthentication方法
			authResult = attemptAuthentication(request, response);
			if (authResult == null) {
				// return immediately as subclass has indicated that it hasn't completed
				// authentication
				return;
			}
			sessionStrategy.onAuthentication(authResult, request, response);
		}
		// 客户端信息不合法（client_id不存在或client_secret不正确）抛出的异常，调用unsuccessfulAuthentication方法处理
		catch (InternalAuthenticationServiceException failed) {
			logger.error(
					"An internal error occurred while trying to authenticate the user.",
					failed);
			unsuccessfulAuthentication(request, response, failed);

			return;
		}
		catch (AuthenticationException failed) {
			// Authentication failed
			unsuccessfulAuthentication(request, response, failed);

			return;
		}

		// Authentication success
		if (continueChainBeforeSuccessfulAuthentication) {
			chain.doFilter(request, response);
		}

		successfulAuthentication(request, response, chain, authResult);
	}

	
	
	
	public abstract Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException, IOException,
			ServletException;

	// 登录失败处理
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString(), failed);
			logger.debug("Updated SecurityContextHolder to contain null Authentication");
			logger.debug("Delegating to authentication failure handler " + failureHandler);
		}

		rememberMeServices.loginFail(request, response);
		/**
		* 调用其子类 ClientCredentialsTokenEndpointFilter 的afterPropertiesSet()方法中的设置的onAuthenticationFailure方法,这个地方有点绕，
		* 自己跑几遍源码看看就能理解了，接下来就是去看ClientCredentialsTokenEndpointFilter 中的实现
		* */	
		failureHandler.onAuthenticationFailure(request, response, failed);
	}

}
```
```java
public class ClientCredentialsTokenEndpointFilter extends AbstractAuthenticationProcessingFilter {
	// 异常处理
	private AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
	private boolean allowOnlyPost = false;
	public ClientCredentialsTokenEndpointFilter() {
		this("/oauth/token");
	}

	public ClientCredentialsTokenEndpointFilter(String path) {
		super(path);
		setRequiresAuthenticationRequestMatcher(new ClientCredentialsRequestMatcher(path));
		// If authentication fails the type is "Form"
		((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setTypeName("Form");
	}

	public void setAllowOnlyPost(boolean allowOnlyPost) {
		this.allowOnlyPost = allowOnlyPost;
	}

	/**
	 * @param authenticationEntryPoint the authentication entry point to set
	 */
	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	// 这个方法在bean初始化时调用
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				if (exception instanceof BadCredentialsException) {
					exception = new BadCredentialsException(exception.getMessage(), new BadClientCredentialsException());
				}
				authenticationEntryPoint.commence(request, response, exception);
			}
		});
		setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				// no-op - just allow filter chain to continue to token endpoint
			}
		});
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (allowOnlyPost && !"POST".equalsIgnoreCase(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" });
		}
		String clientId = request.getParameter("client_id");
		String clientSecret = request.getParameter("client_secret");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication;
		}
		if (clientId == null) {
			throw new BadCredentialsException("No client credentials presented");
		}
		if (clientSecret == null) {
			clientSecret = "";
		}
		clientId = clientId.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(clientId,
				clientSecret);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	protected static class ClientCredentialsRequestMatcher implements RequestMatcher {
		private String path;
		public ClientCredentialsRequestMatcher(String path) {
			this.path = path;
		}
		@Override
		public boolean matches(HttpServletRequest request) {
			String uri = request.getRequestURI();
			int pathParamIndex = uri.indexOf(';');
			if (pathParamIndex > 0) {
				// strip everything after the first semi-colon
				uri = uri.substring(0, pathParamIndex);
			}
			String clientId = request.getParameter("client_id");
			if (clientId == null) {
				// Give basic auth a chance to work instead (it's preferred anyway)
				return false;
			}
			if ("".equals(request.getContextPath())) {
				return uri.endsWith(path);
			}
			return uri.endsWith(request.getContextPath() + path);
		}
	}
}
```