package com.mengxuegu.oauth2.server.config;

import com.mengxuegu.oauth2.server.authorize.CustomAuthorizeConfigurationManager;
import com.mengxuegu.oauth2.server.mobile.MobileAuthenticationConfig;
import com.mengxuegu.oauth2.server.mobile.MobileValidateFilter;
import com.mengxuegu.oauth2.server.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@EnableWebSecurity // 包含了@Configuration注解
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

//    @Autowired // 在 SpringSecurityBean 添加到容器了
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties sProperties;


    @Autowired
    private UserDetailsService customUserDetailsService;

    /**
     * 校验手机号是否存在 手机号认证
     */
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    /**
     * 手机验证码校验
     */
    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;
    /**
     * 退出清除缓存
     */
    @Autowired
    private LogoutHandler customLogoutHandler;

    /**
     * 管理了所有关于权限的配置
     */
    @Autowired
    private CustomAuthorizeConfigurationManager customAuthorizeConfigurationManager;

//    /**
//     * 图片验证
//     */
//    @Autowired
//    private ImageCodeValidateFilter imageCodeValidateFilter;


    /**
     * 为了解决退出重新登录的问题
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    //注入session失败策略
//    @Autowired
//    private InvalidSessionStrategy invalidSessionStrategy;
    //
//    @Autowired
//    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;


    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 是否启动时自动创建表，第一次启动创建就行，后面启动把这个注释掉,不然报错已存在表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 开启密码模式需要注入  AuthenticationManager
     * password 密码模式要使用此认证管理器
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(passwordEncoder.encode("1234"))
//                .authorities("product");
        auth.userDetailsService(customUserDetailsService);
    }

    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() //浏览器表现出弹窗

        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)//在验证码认证通过后进行用户名密码验证
                .formLogin() //浏览器表现出登录网页 表单登录
//                .loginPage("/login/page")  // ==>CustomLoginController ->toLogin 1.0
//                .loginPage(sProperties.getAuthentication().getLoginPage())  // ==>CustomLoginController ->toLogin
//                .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login 1.0
                .loginProcessingUrl(sProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理Url, 默认是 /login
//                .usernameParameter("name") // 默认用户名的属性名是 username 1.0
                .usernameParameter(sProperties.getAuthentication().getUsernameParameter()) // 默认用户名的属性名是 username
//                .passwordParameter("pwd") // 默认密码的属性名是 password 1.0
                .passwordParameter(sProperties.getAuthentication().getPasswordParameter()) // 默认密码的属性名是 password
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
//                .and().csrf().disable() // 输入有效用户信息，还是回到登录页，则要禁用 CSRF 攻击。
                // CSRF（Cross-site request forgery） 跨站请求伪造 关闭 CSRF 攻击
                // 认证一块的代码已优化到AuthorizeConfigurerProvider 接口下的实现中
//                .and()
//                .rememberMe() //记住我
//                .tokenRepository(jdbcTokenRepository()) // 保存登录信息
//                .tokenValiditySeconds(sProperties.getAuthentication().getTokenValiditySeconds()) // 记住我有效时长（秒）
                //session 管理
//                .and()
//                .sessionManagement()
////                .invalidSessionStrategy(invalidSessionStrategy)// session失效后处理逻辑
//                .maximumSessions(1) //允许多少用户同时登陆
////                .expiredSessionStrategy(sessionInformationExpiredStrategy)//当用户达到最大session数后，则调用此处的实现
////                .maxSessionsPreventsLogin(true)// 当一个用户达到最大session数，则不允许后面进行登录 不太建议用开启这个功能，因为如果是被别人盗号登录，那后面都无法登录。
//                .sessionRegistry(sessionRegistry())
//                .and()
                .and().logout()//配置退出登录
                .addLogoutHandler(customLogoutHandler) // +++ 退出处理
                .logoutUrl("/user/logout")//退出系统URL
//                .logoutSuccessUrl("/mobile/page")//退出成功后请求 /mobile/page 回到手机登录页
//                .deleteCookies("JSESSIONID")//删除特定的Cookie值
        ; // 分号`;`不要少了

        // 将手机相关的配置绑定过滤器链上
        http.apply(mobileAuthenticationConfig);
        // 跨站请求伪造 关闭 CSRF 攻击
        http.csrf().disable();
        //权限相关配置管理者, 将所有授权配置管理起来了
        customAuthorizeConfigurationManager.configure(http.authorizeRequests());
    }

//    /**
//     * 对静态资源放行
//     */
//    @Override
//    public void configure(WebSecurity web) {
////        super.configure(web);
////        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
//        web.ignoring().antMatchers(sProperties.getAuthentication().getStaticPaths());
//    }


}
